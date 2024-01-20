<template>
  <div>
    <form @submit.prevent="addProduct">
      <h1>Create Product</h1>
      <!-- <div>
        <label for="productName">Nome do Produto:</label>
        <input type="text" id="productName" v-model="product.name">
      </div>

      <div>
        <label for="productDescription">Descrição do Produto:</label>
        <textarea id="productDescription" v-model="product.description"></textarea>
      </div>

      <div>
        <label for="productCode">Código do Produto:</label>
        <input type="text" id="productCode" v-model="product.code">
      </div>

      <div>
        <label for="productImage">Imagem do Produto:</label>
        <input type="file" id="productImage" @change="convertToBase64">
      </div>

      <button type="submit">Adicionar Produto</button> -->
      <Toast />


      <div class="mb-3">
        <label for="productName">Nome do Produto:</label>
        <input type="text" id="productName" class="form-control" v-model="product.name">      </div>
      <div class="mb-3">
        <label for="productDescription">Descrição do Produto:</label>
        <textarea id="productDescription" class="form-control" v-model="product.description"></textarea>
      </div>
      <div class="mb-3">
        <label for="productCode">Código do Produto:</label>
        <input type="text" id="productCode" class="form-control" v-model="product.code">
      </div>
      <div class="mb-3">
        <label for="productImage">Imagem do Produto:</label>
        <input type="file" id="productImage" class="form-control" @change="convertToBase64">
      </div>

      <button type="submit" class="btn btn-primary mb-3">Create</button>

    </form>


    <div v-if="submittedProduct">
      <h2>Produto Adicionado:</h2>
      <p>Nome: {{ submittedProduct.name }}</p>
      <p>Descrição: {{ submittedProduct.description }}</p>
      <p>Código: {{ submittedProduct.code }}</p>
      <img :src="submittedProduct.imageBase64" alt="Imagem do Produto" v-if="submittedProduct.imageBase64">
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import router from '@/router';
import { useToast } from "primevue/usetoast";
import Toast from 'primevue/toast';

const toast = useToast();


console.log(toast);

const product = ref({
  name: '',
  description: '',
  code: '',
  imageBase64: ''
});
const submittedProduct = ref(null);

async function addProduct() {

  //send to api
  const response = await axios.post('/products', {
    code : product.value.code,
    photoBase64: product.value.imageBase64,
    name : product.value.name,
    description : product.value.description,
  });

  if (response.status === 201) {
    submittedProduct.value = response.data;
  }else{
    toast.add({ severity: 'error', summary: 'Error', detail: 'Error adding product', life: 3000 });
    return
  }

  submittedProduct.value = { ...product.value };

  toast.add({ severity: 'success', summary: 'Info', detail: 'Product added successfully', life: 3000 });



  // router.push({ name: 'ViewProducts' });
}

function convertToBase64(event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      product.value.imageBase64 = e.target.result;
    };
    reader.readAsDataURL(file);
  }
}
</script>

<style>
/* Adicione aqui os estilos desejados */
  img{
    width: 500px;
  }
</style>
