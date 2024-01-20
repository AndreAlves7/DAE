<template>

<div class="container">
  <div>

    <br><br><br>
  <h1>Batch Import Reading for sensor with XML</h1>
  <Toast />

  <div class="mb-3">
    <label for="xmlFile">XML File:</label>
    <input type="file" id="xmlFile" class="form-control" ref="fileInput" @change="handleXMLFile">
  </div>
  </div>
  <button class="btn btn-sm btn-primary" @click="clearFileImput">
     Clear input
    </button>
</div>

</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import router from '@/router';
import { useToast } from "primevue/usetoast";
import Toast from 'primevue/toast';

const toast = useToast();
const fileInput = ref(null);

console.log(toast);

const product = ref({
  name: '',
  description: '',
  code: '',
  imageBase64: ''
});
const submittedXML = ref(null);

const handleXMLFile = async (event) => {
  const file = event.target.files[0];
  if (!file) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'No file selected', life: 3000 });
    return;
  }

  const reader = new FileReader();
  reader.onload = async (e) => {
    try {
      const xmlContent = e.target.result;
      const response = await axios.post('/sensors/readings', xmlContent, {
        headers: {
          'Content-Type': 'application/xml'
        }
      });

      if (response.status === 200) {
        submittedXML.value = response.data;
        toast.add({ severity: 'success', summary: 'Success', detail: 'XML imported successfully', life: 3000 });
      } else {
        throw new Error('Error importing XML');
      }
    } catch (error) {
      console.error('API error:', error);
      toast.add({ severity: 'error', summary: 'Error', detail: 'Error importing XML', life: 3000 });
    }
  };
  reader.readAsText(file);
};

const clearFileImput = () => {
  if (fileInput.value) {
      fileInput.value.value = '';
  }
}

</script>

<style>
/* Adicione aqui os estilos desejados */
  img{
    width: 500px;
  }
</style>
