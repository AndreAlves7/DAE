<script setup>
import {  ref, onMounted , watch} from "vue";
import { BIconSearch, BIconTrash } from 'bootstrap-icons-vue'
import axios from 'axios';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';
import router from '@/router';

// import { FilterMatchMode  } from 'primevue/api';


const sensors = ref([]);
const selectedOrder = ref([]);

watch(selectedOrder, () => {
  console.log(selectedOrder.value)
}) 

onMounted(async () => {
  try {
    const response = await axios.get('/sensors');
    sensors.value = response.data;
    console.log(sensors.value);
  } catch (error) {
    console.error('Error fetching orders:', error);
  }
});

const editClick = (id) => {
  console.log(id);
  router.push({ name: 'UpdateSensor', params: { id: id } });
}


const deleteClick = async (id) => {
  //dialog confirm
  const isConfirm = confirm('Are you sure you want to delete this sensor?');
  if (!isConfirm) return;

  try {
    await axios.delete(`/sensors/${id}`);
  } finally {
    const response = await axios.get('/sensors');
    sensors.value = response.data;
  }

};

</script>

<template>
  <div class="container">
    <div class="col-100">
      <br><br><br>
      <h1 class="flex-grow">Sensors</h1>
      <button class="btn btn-sm btn-primary" @click="router.push({ name: 'CreateSensor' })">
      Create Sensor
    </button>
      <br><br>
      <DataTable v-model:selection="selectedOrder" :value="sensors" 
        stateStorage="session" stateKey="table-orders" paginator :rows="10" filterDisplay="menu"
        selectionMode="single" dataKey="id" :globalFilterFields="['code']">
        <Column field="code" header="Code" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{ data.name }}</span>
            </div>
          </template>
        </Column>
        <Column header="Actions" style="width: 20%">
          <template #body="{ data }">
            <div class="p-2">
              <button class="btn btn-sm btn-light me-2" @click="editClick(data.id)">
                <BIconPencil class="bi bi-xs" />
              </button>
              <button class="btn btn-sm btn-danger me-2" @click="deleteClick(data.id)">
                <BIconTrash class="bi bi-xs" />
              </button>
              <button class="btn btn-sm btn-info" @click="router.push({ name: 'SensorReadings', params: { sensorId: data.id } })">
                Search Readings
                <BIconSearch class="bi bi-xs" />
              </button>
            </div>
          </template>
        </Column>
        <template #empty>No sensors found.</template>
      </DataTable>
    </div>
  </div>
</template>

<style scoped>

</style>