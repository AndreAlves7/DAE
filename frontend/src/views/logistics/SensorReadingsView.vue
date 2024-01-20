<script setup>
import {  ref, onMounted , watch} from "vue";
import { useRoute } from 'vue-router';
import { BIconSearch, BIconTrash } from 'bootstrap-icons-vue'
import axios from 'axios';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';
import router from '@/router';

// import { FilterMatchMode  } from 'primevue/api';


const readings = ref([]);
const selectedReading = ref();
const route = useRoute();


onMounted(async () => {
  try {
    const response = await axios.get(`/readings/sensor/${route.params.sensorId}` );
    readings.value = response.data;
    console.log(readings.value);
  } catch (error) {
    console.error('Error fetching orders:', error);
  }
});

const formatTimestamp = (timestamp) => {
  if (!timestamp) {
    return 'No timestamp provided'; // Fallback message
  }
  const date = new Date(timestamp);
  return isNaN(date.getTime()) ? 'Invalid Date' : date.toLocaleString();
}

</script>

<template>
  <div class="container">
    <div class="col-100">
      <br><br><br>
      <h1 class="flex-grow">Readings of Selected sensor</h1>
      <br><br>
      <DataTable v-model:selection="selectedReading" :value="readings" 
        stateStorage="session" stateKey="table-orders" paginator :rows="10" filterDisplay="menu"
        selectionMode="single" dataKey="id" :globalFilterFields="['code']">
        <Column field="value" header="Value" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{ data.value }}</span>
            </div>
          </template>
        </Column>
        <Column field="recordingTimestamp" header="Recording Timestamp" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{ formatTimestamp(data.recordingTimeStamp) }}</span>
            </div>
          </template>
        </Column>
        <Column field="packageCode" header="Package Code" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{ data.packageCode }}</span>
            </div>
          </template>
        </Column>
        <Column field="orderCode" header="Order Code" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{ data.orderCode }}</span>
            </div>
          </template>
        </Column>
        <template #empty>No readings found.</template>
      </DataTable>
    </div>
  </div>
</template>

<style scoped>

</style>