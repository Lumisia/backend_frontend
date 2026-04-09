<template>
  <div class="app-shell">
    <RouterView />
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router'
</script>

<style scoped>
.app-shell {
  width: min(1080px, calc(100vw - 32px));
  margin: 0 auto;
  padding: 40px 0 72px;
}

@media (max-width: 640px) {
  .app-shell {
    width: min(100vw - 24px, 1080px);
    padding: 28px 0 48px;
  }
}
</style>
