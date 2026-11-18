<script setup>
import { computed } from 'vue'
import { RouterView, useRoute } from 'vue-router'
import SiteFooter from '@/components/SiteFooter.vue'
import SiteHeader from '@/components/SiteHeader.vue'

const route = useRoute()
const isAuthPage = computed(() => route.meta.authPage === true)
</script>

<template>
  <div class="app-shell">
    <SiteHeader v-if="!isAuthPage" />
    <RouterView />
    <SiteFooter v-if="!isAuthPage" />
  </div>
</template>
