<script setup>
import logo from '@/assets/globalhands-logo.png'
import authSketch from '@/assets/images/auth-coast-sketch.png'

defineProps({
  title: { type: String, required: true },
  subtitle: { type: String, default: '' },
  eyebrow: { type: String, default: 'Conecta · Colabora · Cambia el mundo' },
})
</script>

<template>
  <main class="auth-page">
    <section class="auth-visual" :style="{ '--auth-image': `url(${authSketch})` }">
      <RouterLink class="auth-brand" to="/">
        <img class="auth-brand-logo" :src="logo" alt="" />
        <span><strong>Global Hands</strong><small>{{ eyebrow }}</small></span>
      </RouterLink>
      <div class="auth-message">
        <span class="eyebrow">Cada gesto cuenta</span>
        <h1>{{ title }}</h1>
        <p>{{ subtitle }}</p>
      </div>
      <div class="auth-values">
        <span>◉ Personas voluntarias</span><span>◇ Organizaciones solidarias</span><span>◎ Un mundo mejor</span>
      </div>
    </section>
    <section class="auth-content">
      <div class="auth-content-inner"><slot /></div>
    </section>
  </main>
</template>
