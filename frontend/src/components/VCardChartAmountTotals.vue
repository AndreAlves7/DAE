<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useUserStore } from '../stores/user.js';

const userStore = useUserStore();
const user = ref(null);
const dataForStatistics = ref([]);

const loadTransactions = async () => {
  try {
    await userStore.loadUser();
    user.value = userStore.user;


    if (user.value) {
      const response = await axios.get(`/vcard/statistics/totals/${user.value.id}/${user.value.user_type}`);
      dataForStatistics.value = response.data;
    }
  } catch (error) {
    console.error(error);
  }
};


onMounted(() => {
    loadTransactions();
});

</script>

<style scoped>
.centered-content {
  text-align: center;
}

.centered-content h4 {
  margin-bottom: 15px;
}
</style>

<template>
    <div class="centered-content">
      <div class="card p-3 d-flex align-items-center justify-content-center">
        <h5 class="card-title text-primary mb-4">Transaction Statistics</h5>
        <ul class="list-group list-group-flush">
          <li class="list-group-item text-center">
            <span class="fw-bold">Total Sum of transactions:</span> {{ dataForStatistics.totalSum }} €
          </li>
          <li class="list-group-item text-center">
            <span class="fw-bold">Largest transaction:</span> {{ dataForStatistics.maxValue }} €
          </li>
        </ul>
      </div>
    </div>
  </template>
  