<template>
  <div>
    <form @submit.prevent="addOrder">
      <div>
        <label for="productName">Order code:</label>
        <input type="text" id="productName" v-model="order.code">
      </div>
      
      <div class="form-group">
          <label for="sensors">Packages</label>
                <MultiSelect v-model="selectedPackages" :options="allPackages" :optionLabel="package_ => package_.code" />
      </div> 
            <br>
            <button type="submit">Add Package</button>

      <div class="container mt-4">
              <div class="row">
                <div class="col-12">
                  <h2>Assign amount to packages</h2>
                  <div v-for="(package_, order) in selectedPackages" :key="order" class="input-group mb-3">
                    <span class="input-group-text">{{ package_.code }}</span>
                    <input type="text" class="form-control" v-model="package_.amount">
                  </div>
                </div>
              </div>
            </div>
    </form>
    
  </div>
</template>

<script setup>
import { ref ,onMounted} from 'vue';
import axios from 'axios';
import router from '@/router';
import MultiSelect from 'primevue/multiselect';

const allPackages = ref([]);
const selectedPackages = ref([]);


const order = ref({
    code: '',
    packages: []
});

onMounted(async () => {
  const response = await axios.get('/packages');
        allPackages.value = response.data.map(pkg => ({
            ...pkg,
            amount: 0
        }));
});

async function addOrder() {


  console.log(order.value.code)
  console.log("ADSDSASADDASSDA")
  console.log(selectedPackages.value)
  //send to api
  // const response = await axios.post('/orders', {
  //   code : order.value.code,
  //   quantityByPackageID : allPackages.value,
  // });

  // if (response.status === 201) {
    // submittedOrder.value = response.data;
  // }

  // submittedOrder.value = { ...product.value };

  // router.push({ name: 'ViewProducts' });
}

</script>

<style>
/* Adicione aqui os estilos desejados */
  img{
    width: 500px;
  }
</style>
