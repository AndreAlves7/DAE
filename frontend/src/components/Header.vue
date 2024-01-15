<template>
  <p>header</p>
  <button @click.prevent="logout">logout</button>
</template>

<script setup>
const emit = defineEmits(['logout'])

const logout = () => {
  emit('logout')
}
</script>
