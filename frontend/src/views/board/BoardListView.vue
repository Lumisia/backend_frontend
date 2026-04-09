<script setup>
import { onMounted, ref } from 'vue'
import { RouterLink } from 'vue-router'

import { fetchBoardList } from '@/api/board'

const boards = ref([])
const isLoading = ref(true)
const errorMessage = ref('')

async function loadBoards() {
  isLoading.value = true
  errorMessage.value = ''

  try {
    boards.value = await fetchBoardList()
  } catch (error) {
    errorMessage.value = error.message || '게시글 목록을 불러오지 못했습니다.'
  } finally {
    isLoading.value = false
  }
}

onMounted(() => {
  loadBoards()
})
</script>

<template>
  <section class="board-page">
    <header class="board-page__hero">
      <div>
        <p class="eyebrow">Board Archive</p>
        <h1 class="page-title">게시글 목록</h1>
        <p class="page-subtitle">
          서버에 저장된 전체 게시글을 불러와 번호와 제목 중심으로 빠르게 확인할 수 있게 구성했습니다.
        </p>
      </div>

      <RouterLink class="primary-button" to="/board/reg">글쓰기</RouterLink>
    </header>

    <section class="panel list-panel">
      <div class="list-panel__header">
        <div>
          <p class="section-label">Posts</p>
          <strong class="list-panel__count">{{ boards.length }}건</strong>
        </div>

        <button class="secondary-button" type="button" @click="loadBoards" :disabled="isLoading">
          {{ isLoading ? '불러오는 중...' : '새로고침' }}
        </button>
      </div>

      <div v-if="errorMessage" class="feedback-card feedback-card--error">
        <p>{{ errorMessage }}</p>
      </div>

      <div v-else-if="isLoading" class="feedback-card">
        <p>게시글 목록을 불러오는 중입니다.</p>
      </div>

      <div v-else-if="boards.length === 0" class="feedback-card">
        <p>등록된 게시글이 없습니다. 첫 글을 작성해 보세요.</p>
      </div>

      <div v-else class="table-wrap">
        <table class="board-table">
          <thead>
            <tr>
              <th scope="col">번호</th>
              <th scope="col">제목</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="board in boards" :key="board.idx">
              <td class="board-table__idx">{{ board.idx }}</td>
              <td>
                <RouterLink class="board-table__link" :to="`/board/${board.idx}`">
                  {{ board.title || '제목 없음' }}
                </RouterLink>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </section>
</template>

<style scoped>
.list-panel {
  padding: 28px;
}

.list-panel__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}

.list-panel__count {
  display: inline-block;
  margin-top: 4px;
  color: var(--color-heading);
  font-size: 1.6rem;
  font-weight: 800;
}

.table-wrap {
  overflow: hidden;
  border: 1px solid var(--color-line);
  border-radius: 22px;
  background: rgba(255, 255, 255, 0.6);
}

.board-table {
  width: 100%;
  border-collapse: collapse;
}

.board-table th,
.board-table td {
  padding: 18px 20px;
  text-align: left;
}

.board-table thead {
  background: rgba(232, 221, 207, 0.42);
}

.board-table th {
  color: var(--color-text-muted);
  font-size: 0.92rem;
  font-weight: 700;
}

.board-table tbody tr + tr td {
  border-top: 1px solid var(--color-line);
}

.board-table tbody tr:hover {
  background: rgba(255, 255, 255, 0.52);
}

.board-table__idx {
  width: 120px;
  color: var(--color-text-muted);
  font-weight: 700;
}

.board-table__link {
  color: var(--color-heading);
  font-size: 1.02rem;
  font-weight: 700;
}

.board-table__link:hover {
  color: var(--color-accent-strong);
}

@media (max-width: 640px) {
  .list-panel {
    padding: 18px;
  }

  .board-table th,
  .board-table td {
    padding: 14px 12px;
  }

  .board-table__idx {
    width: 84px;
  }
}
</style>
