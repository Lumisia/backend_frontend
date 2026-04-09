const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL ?? '').trim().replace(/\/+$/, '')

function buildUrl(path) {
  return `${API_BASE_URL}${path}`
}

function unwrapPayload(payload) {
  if (payload && typeof payload === 'object' && 'result' in payload) {
    return payload.result
  }

  return payload
}

function extractMessage(payload) {
  if (!payload || typeof payload !== 'object') {
    return null
  }

  return payload.message ?? payload.error ?? null
}

function normalizeBoard(rawBoard) {
  if (!rawBoard || typeof rawBoard !== 'object') {
    return null
  }

  return {
    idx: rawBoard.idx ?? rawBoard.id ?? null,
    title: rawBoard.title ?? '',
    content: rawBoard.content ?? rawBoard.contents ?? '',
  }
}

async function request(path, options = {}) {
  const headers = new Headers(options.headers ?? {})

  if (options.body && !headers.has('Content-Type')) {
    headers.set('Content-Type', 'application/json')
  }

  const response = await fetch(buildUrl(path), {
    ...options,
    headers,
  })

  const contentType = response.headers.get('content-type') ?? ''
  let payload = null

  if (contentType.includes('application/json')) {
    payload = await response.json()
  } else {
    const text = await response.text()
    payload = text || null
  }

  if (!response.ok) {
    const error = new Error(extractMessage(payload) ?? '요청을 처리하는 중 문제가 발생했습니다.')
    error.status = response.status
    error.payload = payload
    throw error
  }

  return unwrapPayload(payload)
}

export async function fetchBoardList() {
  const payload = await request('/board/list')

  if (!Array.isArray(payload)) {
    return []
  }

  return payload.map(normalizeBoard).filter(Boolean)
}

export async function fetchBoardDetail(idx) {
  const payload = await request(`/board/${idx}`)
  const board = normalizeBoard(payload)

  if (!board || board.idx === null || board.idx === undefined) {
    throw new Error('게시글 정보를 불러오지 못했습니다.')
  }

  return board
}

export async function createBoard({ title, content }) {
  return request('/board/reg', {
    method: 'POST',
    body: JSON.stringify({
      title,
      content,
      contents: content,
    }),
  })
}
