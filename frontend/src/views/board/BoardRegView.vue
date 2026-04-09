<script setup>
import { computed, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

import { createBoard } from '@/api/board'

const router = useRouter()

const form = reactive({
  title: '',
  content: '',
})

const errorMessage = ref('')
const isSubmitting = ref(false)

const isValid = computed(() => form.title.trim() && form.content.trim())

async function submitBoard() {
  errorMessage.value = ''

  if (!isValid.value) {
    errorMessage.value = '제목과 내용을 모두 입력해 주세요.'
    return
  }

  isSubmitting.value = true

  try {
    await createBoard({
      title: form.title.trim(),
      content: form.content.trim(),
    })

    window.alert('게시글이 저장되었습니다.')
    router.push('/board/list')
  } catch (error) {
    errorMessage.value = error.message || '게시글 저장에 실패했습니다.'
  } finally {
    isSubmitting.value = false
  }
}
</script>

<template>
  <section class="board-page">
    <header class="board-page__hero">
      <div>
        <p class="eyebrow">Board Compose</p>
        <h1 class="page-title">게시글 등록</h1>
        <p class="page-subtitle">제목과 내용을 입력한 뒤 저장하면 목록 페이지로 자동 이동합니다.</p>
      </div>
    </header>

    <form class="panel form-panel" @submit.prevent="submitBoard">
      <div class="form-panel__row">
        <label class="form-panel__label" for="board-title">제목</label>
        <input
          id="board-title"
          v-model="form.title"
          class="input-field"
          type="text"
          maxlength="200"
          placeholder="게시글 제목을 입력하세요"
        />
      </div>

      <div class="form-panel__row">
        <label class="form-panel__label" for="board-content">내용</label>
        <textarea
          id="board-content"
          v-model="form.content"
          class="textarea-field"
          placeholder="게시글 내용을 입력하세요"
        />
      </div>

      <div v-if="errorMessage" class="feedback-card feedback-card--error form-panel__feedback">
        <p>{{ errorMessage }}</p>
      </div>

      <div class="form-panel__actions">
        <button class="secondary-button" type="button" @click="router.push('/board/list')">
          목록으로
        </button>
        <button class="primary-button" type="submit" :disabled="isSubmitting">
          {{ isSubmitting ? '저장 중...' : '저장' }}
        </button>
      </div>
    </form>
  </section>
</template>

<style scoped>
.form-panel {
  padding: 30px;
}

.form-panel__row + .form-panel__row {
  margin-top: 22px;
}

.form-panel__label {
  display: inline-block;
  margin-bottom: 10px;
  color: var(--color-heading);
  font-weight: 800;
}

.form-panel__feedback {
  margin-top: 22px;
}

.form-panel__actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 26px;
}

@media (max-width: 640px) {
  .form-panel {
    padding: 20px;
  }

  .form-panel__actions {
    flex-direction: column-reverse;
  }

  .form-panel__actions > button {
    width: 100%;
  }
}
</style>
