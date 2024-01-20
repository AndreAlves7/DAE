<script setup>
import {  ref, onMounted , watch} from "vue";
import { BIconSearch, BIconTrash } from 'bootstrap-icons-vue'
import axios from 'axios';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';
import { useRouter } from 'vue-router';
import { useRoute } from 'vue-router';

const route = useRoute();
// import { FilterMatchMode  } from 'primevue/api';

const router = useRouter();

const packages = ref([]);
const selectedPackage = ref([]);


onMounted(async () => {
  try {
    const response = await axios.get(`/packages/order/${route.params.orderId}`);
    packages.value = response.data;
    console.log(packages.value);
  } catch (error) {
    console.error('Error fetching orders:', error);
  }
});


const editClick = (id) => {
  console.log(id);
  router.push({ name: 'UpdatePackage', params: { id: id } });
}



const deleteClick = async (id) => {
  //dialog confirm
  const isConfirm = confirm('Are you sure you want to delete this package?');
  if (!isConfirm) return;

  try {
    await axios.delete(`/packages/${id}`);
  } finally {
    const response = await axios.get('/packages');
    packages.value = response.data;
  }

};

</script>

<template>
  <div class="container">
    <div class="col-100">
      <br><br><br>

      <div class="flex align-items-center gap-2">

        
        <h1 class="flex-grow">Packages of Order {{ $route.params.orderId }}</h1>
        <!-- <button class="btn btn-sm btn-primary" @click="router.push({ name: 'CreatePackage' })">
          Create Package
        </button> -->
        <br><br>
      </div>
      <DataTable v-model:selection="selectedPackage" :value="packages" 
        stateStorage="session" stateKey="table-orders" paginator :rows="10" filterDisplay="menu"
        selectionMode="single" dataKey="id" :globalFilterFields="['code']">
        <Column field="code" header="Code" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{ data.code }}</span>
            </div>
          </template>
        </Column>
        <Column field="Type" header="Type" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{data.packageType}}</span>
            </div>
          </template>
        </Column>
        <Column header="Actions" style="width: 20%">
          <template #body="{ data }">
            <div class="p-2">
              <button class="btn btn-sm btn-light" @click="editClick(data.id)">
                <BIconPencil class="bi bi-xs" />
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