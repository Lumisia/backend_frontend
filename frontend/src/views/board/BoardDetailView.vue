<script setup>
import { computed, onBeforeUnmount, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import { fetchBoardDetail } from '@/api/board'

const route = useRoute()
const router = useRouter()

const board = ref(null)
const isLoading = ref(true)
const errorMessage = ref('')

let redirectTimerId = null

const boardIndex = computed(() => Number(route.params.idx))

function clearRedirectTimer() {
  if (redirectTimerId) {
    window.clearTimeout(redirectTimerId)
    redirectTimerId = null
  }
}

function scheduleListRedirect() {
  clearRedirectTimer()
  redirectTimerId = window.setTimeout(() => {
    router.replace('/board/list')
  }, 1500)
}

async function loadBoard() {
  clearRedirectTimer()
  board.value = null
  errorMessage.value = ''
  isLoading.value = true

  if (!Number.isInteger(boardIndex.value) || boardIndex.value < 1) {
    errorMessage.value = '잘못된 게시글 번호입니다. 목록으로 이동합니다.'
    isLoading.value = false
    scheduleListRedirect()
    return
  }

  try {
    board.value = await fetchBoardDetail(boardIndex.value)
  } catch (error) {
    errorMessage.value = error.message || '게시글을 찾을 수 없어 목록으로 이동합니다.'
    scheduleListRedirect()
  } finally {
    isLoading.value = false
  }
}

watch(
  () => route.params.idx,
  () => {
    loadBoard()
  },
  { immediate: true },
)

onBeforeUnmount(() => {
  clearRedirectTimer()
})
</script>

<template>
  <section class="board-page">
    <header class="board-page__hero">
      <div>
        <p class="eyebrow">Board Detail</p>
        <h1 class="page-title">게시글 상세</h1>
        <p class="page-subtitle">선택한 게시글의 제목과 본문 전체를 확인할 수 있습니다.</p>
      </div>
    </header>

    <section v-if="errorMessage" class="feedback-card feedback-card--error">
      <p>{{ errorMessage }}</p>
    </section>

    <section v-else-if="isLoading" class="feedback-card">
      <p>게시글 내용을 불러오는 중입니다.</p>
    </section>

    <article v-else-if="board" class="panel detail-panel">
      <div class="detail-panel__meta">
        <span class="detail-panel__badge">No. {{ board.idx }}</span>
      </div>

      <h2 class="detail-panel__title">{{ board.title || '제목 없음' }}</h2>

      <div class="detail-panel__content">
        {{ board.content || '본문이 없습니다.' }}
      </div>

      <div class="detail-panel__actions">
        <button class="secondary-button" type="button" @click="router.push('/board/list')">
          목록보기
        </button>
      </div>
    </article>
  </section>
</template>

<style scoped>
.detail-panel {
  padding: 30px;
}

.detail-panel__meta {
  margin-bottom: 18px;
}

.detail-panel__badge {
  display: inline-flex;
  align-items: center;
  min-height: 36px;
  padding: 0 14px;
  border-radius: 999px;
  background: var(--color-accent-soft);
  color: var(--color-accent-strong);
  font-size: 0.92rem;
  font-weight: 800;
}

.detail-panel__title {
  margin: 0;
  color: var(--color-heading);
  font-size: clamp(1.8rem, 4vw, 2.7rem);
  line-height: 1.14;
  font-weight: 800;
}

.detail-panel__content {
  margin-top: 22px;
  padding: 24px;
  border-radius: 22px;
  background: rgba(255, 255, 255, 0.7);
  border: 1px solid var(--color-line);
  color: var(--color-text);
  white-space: pre-wrap;
  word-break: break-word;
}

.detail-panel__actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 24px;
}

@media (max-width: 640px) {
  .detail-panel {
    padding: 20px;
  }

  .detail-panel__content {
    padding: 18px;
  }

  .detail-panel__actions {
    justify-content: stretch;
  }

  .detail-panel__actions > button {
    width: 100%;
  }
}
</style>
