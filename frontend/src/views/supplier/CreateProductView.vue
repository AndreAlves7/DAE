<template>
  <div>
    <form @submit.prevent="addProduct">
      <div>
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

      <button type="submit">Adicionar Produto</button>
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
    photoBase64: product.value.imageBase64
  });

  if (response.status === 201) {
    submittedProduct.value = response.data;
  }


  submittedProduct.value = { ...product.value };
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
</style>
