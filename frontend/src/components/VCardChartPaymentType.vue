<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useUserStore } from '../stores/user.js';
import Chart from 'primevue/chart';

const userStore = useUserStore();
const user = ref(null);
const dataForStatistics = ref([]);

const loadTransactions = async () => {
    try {
        await userStore.loadUser();
        user.value = userStore.user;

        const response = await axios.get(`/vcard/statistics/payment_types/${user.value.id}/${user.value.user_type}`);
        dataForStatistics.value = response.data;

    } catch (error) {
        console.error(error);
    }
};

onMounted(() => {
    loadTransactions();
});

const setChartData = () => {
    
    if (!dataForStatistics.value || !Array.isArray(dataForStatistics.value)) {
        return {};
    }
    const documentStyle = getComputedStyle(document.documentElement);

    const paymentInfo = dataForStatistics.value.reduce((acc, item) => {
    if (!acc[item.payment_type]) {
        acc[item.payment_type] = 0;
    }
    acc[item.payment_type] += item.total;
    return acc;
    }, {});

    const paymentTypes = Object.keys(paymentInfo);
    const paymentTotals = Object.values(paymentInfo);

    return {
        labels: paymentTypes,
        datasets: [
            {
                data: paymentTotals,
                backgroundColor: [documentStyle.getPropertyValue('--blue-500'), documentStyle.getPropertyValue('--yellow-500')
                    , documentStyle.getPropertyValue('--green-500'), documentStyle.getPropertyValue('--red-500')],
                hoverBackgroundColor: [documentStyle.getPropertyValue('--blue-400'), documentStyle.getPropertyValue('--yellow-400')
                    , documentStyle.getPropertyValue('--green-400'), documentStyle.getPropertyValue('--red-500')]
            }
        ]
    };
};

const setChartOptions = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');

    return {
        responsive: true,
        maintainAspectRatio: true,
        aspectRatio: 1.5,
        
        plugins: {
            legend: {
                labels: {
                    cutout: '60%',
                    color: textColor
                }
            }
        }
    };
};

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
    <h4> Payment type usage </h4>
    <Chart type="doughnut" :data="setChartData()" :options="setChartOptions()" class="w-full md:w-30rem" />
  </div>
</template>