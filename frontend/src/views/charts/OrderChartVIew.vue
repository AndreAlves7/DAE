<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import Chart from 'primevue/chart';
import { useRoute } from 'vue-router';

const route = useRoute();
const dataForChart = ref([]);

const loadSensorData = async () => {
    try {
        const response = await axios.get(`/readings/order/${route.params.orderId}`);
        dataForChart.value = response.data;
    } catch (error) {
        console.error(error);
    }
};

onMounted(loadSensorData);

const chartData = ref();
const chartOptions = ref();

const setChartData = () => {
  console.log(dataForChart)
    const sensorNames = Object.keys(dataForChart.value);
    const averageReadings = Object.values(dataForChart.value);

    return {
        labels: sensorNames,
        datasets: [
            {
                label: 'Average Sensor Readings',
                backgroundColor: '#42A5F5',
                data: averageReadings,
            },
        ],
    };
};

const setChartOptions = () => {
    return {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    };
};
</script>

<template>
  <br>
  <h4> Average Sensor Readings </h4>
  <div class="card">
      <Chart type="bar" :data="setChartData()" :options="setChartOptions()" class="h-50rem" />
  </div>
</template>


<style scoped>
.h-50rem {
    height: 50rem;
}

</style>