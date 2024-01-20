<template>
    <div>
      <form @submit.prevent="addSensor">
        <h1>Create Sensor</h1>
        <!-- <div>
          <label for="productName">Nome do sensor:</label>
          <input type="text" id="productName" required v-model="sensor.name">
        </div>
        <Toast />
        <button type="submit">add Sensor</button> -->

        <div class="mb-3">
          <label for="productName">Nome do sensor:</label>
          <input type="text" id="productName" required class="form-control" v-model="sensor.name">
        </div>
        <Toast />
        <button type="submit" class="btn btn-primary mb-3">Create</button>

  
      </form>
  
<!--   
      <div v-if="submittedProduct">
        <h2>Produto Adicionado:</h2>
        <p>Nome: {{ submittedProduct.name }}</p>
        <p>Descrição: {{ submittedProduct.description }}</p>
        <p>Código: {{ submittedProduct.code }}</p>
        <img :src="submittedProduct.imageBase64" alt="Imagem do Produto" v-if="submittedProduct.imageBase64">
      </div> -->
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
  
  const sensor = ref({
    name: '',
  });
  const submittedProduct = ref(null);
  
  async function addSensor() {
  
    //send to api
    const response = await axios.post('/sensors', {
      name : sensor.value.name,
    });
  
    if (response.status === 201) {
      submittedProduct.value = response.data;
    }else{
      toast.add({ severity: 'error', summary: 'Error', detail: 'Error adding sensor', life: 3000 });
      return
    }
  
    submittedProduct.value = { ...sensor.value };
  
    toast.add({ severity: 'success', summary: 'Info', detail: 'Sensor added successfully', life: 3000 });
    
    sensor.value = {
      name: '',
    };
  
  
    // router.push({ name: 'ViewProducts' });
  }
  
  </script>
  
  <style>
  /* Adicione aqui os estilos desejados */
    img{
      width: 500px;
    }
  </style>
  