<script setup>
import { RouterLink } from 'vue-router'
import cardSketch from '@/assets/images/news-volunteer-sketch.png'

defineProps({
  opportunity: { type: Object, required: true },
})
</script>

<template>
  <article class="opportunity-card">
    <div class="opportunity-art">
      <img :src="cardSketch" alt="Ilustración provisional de voluntariado" />
      <span class="sample-badge">Ilustración provisional</span>
    </div>
    <div class="opportunity-card-content">
      <div class="tag-row">
        <span class="tag">{{ opportunity.modality }}</span>
        <span v-for="category in opportunity.categories || []" :key="category.id" class="tag tag-soft">
          {{ category.name }}
        </span>
      </div>
      <h3>{{ opportunity.title }}</h3>
      <p class="card-organization">{{ opportunity.organizationName }}</p>
      <p class="card-location">
        <span aria-hidden="true">⌖</span>
        {{ [opportunity.city, opportunity.country].filter(Boolean).join(', ') || 'Ubicación por confirmar' }}
      </p>
      <RouterLink class="button button-primary card-action" :to="`/voluntariados/${opportunity.id}`">
        Ver detalles <span aria-hidden="true">→</span>
      </RouterLink>
    </div>
  </article>
</template>
