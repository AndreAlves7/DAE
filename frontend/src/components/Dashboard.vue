
<script setup>
import VCardChartAmountByTime from './VCardChartAmountByTime.vue';
import VCardChartPaymentType from './VCardChartPaymentType.vue';
import VCardChartAmountTotals from './VCardChartAmountTotals.vue';


import { ref,onMounted } from 'vue'
import { useUserStore } from '../stores/user.js'
import { useVcardsStore } from "../stores/vcard.js"

const vcardsStore = useVcardsStore()
const userStore = useUserStore()

onMounted(()=>{
  if (userStore.userType == 'V') {
    vcardsStore.loadVcard(userStore.user?.id)
  }
})

</script>

<template>
    <div class="container mt-4 mb-4">

    <div v-if="userStore.userType == 'A'" class="bg-custom p-3 text-white">
      <h4>Welcome  {{ userStore.user?.name }}</h4>
    </div>
    
    <div v-else class="bg-custom p-3 text-white">   
      <h4>Welcome to your virtual card  {{ userStore.user?.name }}</h4>
      <div class="p-3 text-white">
      <h5 style="font-size: 1.3rem;">Current Balance {{ vcardsStore.vcard.balance }} &euro;</h5>
      </div>
    </div>
  </div>


  <div class="w-75 h-30rem container">
    <div class="row align-items-center">
      <div class="col-12 mt-3">
        <div class="mb-3">
          <h2>Dashboard</h2>
          <hr />
        </div>
      </div>
      <div class="col-lg-4 mt-3" style="margin-left: 100px;">
        <VCardChartAmountTotals />
      </div>
      <div class="col-lg-4 mt-3" style="margin-left: 150px;">
        <VCardChartPaymentType />
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <VCardChartAmountByTime />
      </div>
    </div>
    <br>
    <br>
  </div>
</template>



<style scoped>
.bg-custom {
  background-color: #483a5d;
}
</style>