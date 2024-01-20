<script setup>
import {  ref, onMounted , watch} from "vue";
import { BIconSearch, BIconTrash } from 'bootstrap-icons-vue'
import axios from 'axios';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';
import router from '@/router';

// import { FilterMatchMode  } from 'primevue/api';


const orders = ref([]);

const products = ref([]);
const selectedOrder = ref([]);

watch(selectedOrder, () => {
  console.log(selectedOrder.value)
}) 

onMounted(async () => {
  try {
    const response = await axios.get('/orders');
    orders.value = response.data;
    console.log(orders.value);
  } catch (error) {
    console.error('Error fetching orders:', error);
  }
});

const deleteClick = async (id) => {
  //dialog confirm
  const isConfirm = confirm('Are you sure you want to delete this order?');
  if (!isConfirm) return;

  try {
    await axios.delete(`/orders/${id}`);
  } finally {
    const response = await axios.get('/orders');
    orders.value = response.data;
  }

};


</script>

<template>
  <div class="container">
    <div class="col-100">
      <br><br><br>
      <h1 class="flex-grow">Orders</h1>
      <button class="btn btn-sm btn-primary" @click="router.push({ name: 'createOrder' })">
      Create Order
    </button>
      <br><br>
      <DataTable v-model:selection="selectedOrder" :value="orders" 
        stateStorage="session" stateKey="table-orders" paginator :rows="10" filterDisplay="menu"
        selectionMode="single" dataKey="id" :globalFilterFields="['code']">
        <Column field="code" header="Code" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{ data.code }}</span>
            </div>
          </template>
        </Column>
        <Column field="returned" header="Returned" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{ data.returned ? 'Yes' : 'No' }}</span>
            </div>
          </template>
        </Column>
        <Column header="Actions" style="width: 20%">
          <template #body="{ data }">
            <div class="p-2">
              <button class="btn btn-sm btn-light me-2" @click="router.push({ name: 'UpdateOrder', params: { orderId: data.id } });">
                Associate Transport Order
                <BIconPencil class="bi bi-xs" />
              </button>
              
              <button class="btn btn-sm btn-info me-2" @click="router.push({ name: 'OrderChart', params: { orderId: data.id } });">
                View Order Charts
                <BIconSearch class="bi bi-xs" />
              </button>
              <button class="btn btn-sm btn-danger" @click="deleteClick(data.id)">
                <BIconTrash class="bi bi-xs" />
              </button>
            </div>
          </template>
        </Column>
        <template #empty>No orders found.</template>
      </DataTable>
    </div>
  </div>
</template>

<style scoped>

</style>