<template>
    <div class="edit-product">
        <h1>Edit Package</h1>
        <form @submit.prevent="submitForm">

            <Toast />

            <div class="mb-3">
                <label for="product">Transport Package</label>
                <select id="product" class="form-control" v-model="package_">
                    <option v-for="package_ in transportPackages" :key="package_.id" :value="package_.id">{{ package_.code }}</option>
                </select>
            </div>

        

      <button type="submit" class="btn btn-primary mb-3">Create</button>
            
        </form>
    </div>

</template>

<script setup>

import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { useToast } from "primevue/usetoast";
import Toast from 'primevue/toast';


const toast = useToast();
const router = useRouter();

const package_ = ref({
    code: '',
    id: '',
});

const transportPackages = ref([]);
const route = useRoute();

onMounted(async () => {
    const response = await axios.get(`/packages`);
    transportPackages.value = response.data;
    console.log(transportPackages.value)
});

async function submitForm() {
    try{
    const response = await axios.post(`orders/${route.params.orderId}/package/${package_.value}`);

    if (response.status === 200) {
        toast.add({
            severity: "success",
            summary: "Success",
            detail: "Package updated",
            life: 3000
            });
        }
    }catch(e){
        toast.add({
            severity: "error",
            summary: "Error",
            detail: "Internal error or you can not associate another Transport Package",
            life: 5000
        });
    }
}



</script>

<style scoped>
    img{
        width: 500px;
        object-fit: contain;
    }
</style>