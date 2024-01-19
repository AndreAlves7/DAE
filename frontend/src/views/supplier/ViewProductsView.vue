<script setup>
import {  ref, onMounted } from "vue";
import { BIconSearch, BIconTrash } from 'bootstrap-icons-vue'
import axios from 'axios';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';


const products = ref([]);


onMounted(async () => {
  try {
    const response = await axios.get('/products');
    products.value = response.data;
    console.log(products.value);
  } catch (error) {
    console.error('Error fetching products:', error);
  }
});



</script>

<template>
     <div class="container">
        <div class="col-100">
            <br>
            <br>
            <br>
    <DataTable v-model:selection="products" :value="products" 
        stateStorage="session" stateKey="table-products" paginator :rows="10" filterDisplay="menu"
        selectionMode="none" dataKey="id" :globalFilterFields="['name', 'type']">
        <!-- <template #header>
            <span class="p-input-icon-left">
                <InputText v-model="filters['global'].value" placeholder="Search" />
            </span>
        </template> -->
        <Column field="code" header="Code" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{ data.code }}</span>
            </div>
          </template>
        </Column>
        <Column field="name" header="Name" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{ data.name }}</span>
            </div>
          </template>
        </Column>
        <Column field="description" header="Description" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2">
              <span>{{ data.description }}</span>
            </div>
          </template>
        </Column>
        <Column field="image" header="Image" sortable filterMatchMode="contains" style="width: 40%">
          <template #body="{ data }">
            <div class="flex align-items-center gap-2 ">
              <img class = "img_photo" :src="data.photoBase64"></img>
            </div>
          </template>
        </Column>
        <Column header="Actions" style="width: 20%">
          <template #body="{ data }">
            <div class="p-2">
              <button class="btn btn-sm btn-light" @click="editClick(data.id)" >
                <BIconPencil class="bi bi-xs" />
              </button>
              <button class="btn btn-sm btn-danger" @click="deleteClick(data)" >
                <BIconTrash class="bi bi-xs" />
              </button>
            </div>
          </template>
        </Column>
        <template #empty> No products found. </template>
    </DataTable>
        
      </div>
    </div>
</template>

<style scoped>
.img_photo {
  width: 100px;
  height: 100px;
}
</style>