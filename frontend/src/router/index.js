import { createRouter, createWebHistory } from 'vue-router'
import BoardListView from '@/views/board/BoardListView.vue'
import BoardRegView from '@/views/board/BoardRegView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/board/list',
    },
    {
      path: '/board/list',
      name: 'board-list',
      component: BoardListView,
    },
    {
      path: '/board/reg',
      name: 'board-reg',
      component: BoardRegView,
    },
    {
      path: '/board/:idx(\\d+)',
      name: 'board-detail',
      component: () => import('@/views/board/BoardDetailView.vue'),
      props: true,
    },
    {
      path: '/:pathMatch(.*)*',
      redirect: '/board/list',
    },
  ],
})

export default router
