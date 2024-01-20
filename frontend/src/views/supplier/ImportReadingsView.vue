<template>

<div class="container">
  <div>
    <Toast />
    <br><br><br>
    <h1>Batch Import Sensor Readings</h1>
    <div class="mt-3">
      <div class="mb-3">
        <label for="xmlFile">XML File:</label>
        <input type="file" id="xmlFile" class="form-control" ref="fileInputXml" @change="handleXMLFile">
      </div>
    </div>
    <button class="btn btn-sm btn-primary" @click="clearFileImput">
      Clear input
    </button>
  </div>
  <hr>
  <div>
    <div class="mb-3">
      <label for="csvFile">CSV File:</label>
      <input type="file" id="csvFile" class="form-control" ref="fileInputCsv" @change="handleCSVFile">
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
import { useToast } from "primevue/usetoast";
import Toast from 'primevue/toast';

const toast = useToast();
const fileInputXml = ref(null);
const fileInputCsv = ref(null);

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

const handleCSVFile = async (event) => {
  console.log 

  const file = event.target.files[0];
  if (!file) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'No file selected', life: 3000 });
    return;
  }

  const reader = new FileReader();
  reader.onload = async (e) => {
    try {
      const csvContent = e.target.result;
      const response = await axios.post('/sensors/readings/csv', csvContent, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });

      if (response.status === 200) {
        submittedXML.value = response.data;
        toast.add({ severity: 'success', summary: 'Success', detail: 'CSV imported successfully', life: 3000 });
      } else {
        throw new Error('Error importing CSV');
      }
    } catch (error) {
      console.error('API error:', error);
      toast.add({ severity: 'error', summary: 'Error', detail: 'Error importing CSV', life: 3000 });
    }
  };
  reader.readAsText(file);
};


const clearFileImput = () => {
  if (fileInputXml.value) {
      fileInputXml.value.value = '';
  }
}

</script>

<style>
/* Adicione aqui os estilos desejados */
  img{
    width: 500px;
  }
</style>
