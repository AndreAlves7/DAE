<template>
  <div class="container my-5">
    <div class="bg-custom p-3 text-white rounded-top">
      <h4>Welcome, {{ userStore.user?.name }}</h4>
    </div>
    <div class="user-card p-3 rounded shadow">
      <div class="user-card-body">
        <h2 class="user-name">Name: {{ userStore.user?.name }}</h2>
        <p class="user-email">Email: {{ userStore.user?.email }}</p>
        <p class="user-type">Type: {{ userType(userStore.user?.type) }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '../stores/user.js';

const userStore = useUserStore();

const userType = (type) => {
  const types = {
    1: 'Manufacturer',
    2: 'Operator',
    3: 'Consumer',
  };
  return types[type] || 'Unknown';
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: auto;
}

.bg-custom {
  background-color: #483a5d;
  border-radius: 8px 8px 0 0;
}

.user-card {
  background-color: #fff; 
  color: #333;
  text-align: center;
  border-radius: 0 0 8px 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.user-card-body {
  padding: 2rem;
}

.user-name {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.user-email {
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
}

.user-type {
  font-size: 1.1rem;
  font-style: italic;
}

</style>
