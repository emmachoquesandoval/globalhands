import { createRouter, createWebHistory } from 'vue-router'
import { hasSession } from '@/services/api'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  scrollBehavior(to) {
    if (to.hash) return { el: to.hash, top: 100, behavior: 'smooth' }
    return { top: 0 }
  },
  routes: [
    { path: '/', name: 'home', component: () => import('@/views/HomeView.vue') },
    { path: '/voluntariados', name: 'opportunities', component: () => import('@/views/OpportunitiesView.vue') },
    { path: '/voluntariados/:id', name: 'opportunity-detail', component: () => import('@/views/OpportunityDetailView.vue') },
    { path: '/organizaciones', name: 'organizations', component: () => import('@/views/OrganizationsView.vue') },
    { path: '/organizaciones/:id', name: 'organization-detail', component: () => import('@/views/OrganizationDetailView.vue') },
    { path: '/login', name: 'login', component: () => import('@/views/LoginView.vue'), meta: { authPage: true } },
    { path: '/registro', name: 'register', component: () => import('@/views/RegisterView.vue'), meta: { authPage: true } },
    { path: '/recuperar', name: 'recovery', component: () => import('@/views/RecoveryView.vue'), meta: { authPage: true } },
    { path: '/perfil', name: 'profile', component: () => import('@/views/ProfileView.vue'), meta: { requiresAuth: true } },
    { path: '/solicitudes', name: 'applications', component: () => import('@/views/ApplicationsView.vue'), meta: { requiresAuth: true } },
    { path: '/:pathMatch(.*)*', name: 'not-found', component: () => import('@/views/NotFoundView.vue') },
  ],
})

router.beforeEach((to) => {
  if (to.meta.requiresAuth && !hasSession()) {
    return { name: 'login', query: { redirect: to.fullPath } }
  }
})

export default router
