<script setup>
import {  ref, onMounted , watch} from "vue";
import { BIconSearch, BIconTrash } from 'bootstrap-icons-vue'
import axios from 'axios';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';
import { useRouter } from 'vue-router';

const router = useRouter();


const products = ref([]);
const selectedProduct = ref([]);


onMounted(async () => {
  try {
    const response = await axios.get('/products');
    products.value = response.data;
    console.log(products.value);
  } catch (error) {
    console.error('Error fetching products:', error);
  }
});


const editClick = (id) => {
  console.log(id);
  router.push({ name: 'UpdateProduct', params: { id: id } });
}

watch(selectedProduct, () => {
  console.log(selectedProduct.value)
}) 


const deleteClick = async (id) => {
  //dialog confirm
  const isConfirm = confirm('Are you sure you want to delete this product?');
  if (!isConfirm) return;

  try {
    await axios.delete(`/products/${id}`);
  } finally {
    const response = await axios.get('/products');
    products.value = response.data;
  }

};

</script>

<template>

     <div class="container">
        <div class="col-100">
            <br>
            <br>
            <br>
            <h1 class="flex-grow">Products</h1>
            <button class="btn btn-sm btn-primary" @click="router.push({ name: 'createProduct' })">
     Create Product
    </button>
    <br><br>
    <DataTable v-model:selection="selectedProduct" :value="products" 
        stateStorage="session" stateKey="table-products" paginator :rows="10" filterDisplay="menu"
        selectionMode="single" dataKey="id" :globalFilterFields="['name', 'type']">
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