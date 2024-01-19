<template>
    <div class="edit-product">
        <h1>Edit Package</h1>
        <form @submit.prevent="submitForm">

            <div class="form-group">
                <label for="code">Código</label>
                <input type="text" id="code" v-model="package_.code" />
            </div>
            <!-- dropdown -->
            <div class="form-group">
                <label for="materialType">Tipo de Material</label>
                <select id="materialType" v-model="package_.materialType">
                    <option value="CARDBOARD">CARDBOARD</option>
                    <option value="PLASTIC">PLASTIC</option>
                    <option value="METAL">METAL</option>
                </select>
            </div>

            <!-- dropdown  -->
            <div class="form-group">
                <label for="materialType">Tipo de Material</label>
                <select id="materialType" v-model="package_.packageType">
                    <option value="PRIMARY">PRIMARY</option>
                    <option value="SECONDARY">SECONDARY</option>
                    <option value="TERTIARY">TERTIARY</option>
                    <option value="TRANSPORT">TRANSPORT</option>
                </select>
            </div>

            <div class="form-group">
                <label for="product">Produto</label>
                <select id="product" v-model="package_.product.id">
                    <option v-for="product in products" :key="product.id" :value="product.id">{{ product.name }}</option>
                </select>
            </div>

            <div class="form-group">
                <label for="sensors">Sensores</label>
                <MultiSelect v-model="package_.sensors" :options="allSensors" :optionLabel="sensor => sensor.name" />
            </div>

            

            <button type="submit">Salvar</button>
        </form>
    </div>

</template>

<script setup>

import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import MultiSelect from 'primevue/multiselect';


const allSensors = ref([]);

const package_ = ref({
    code: '',
    id: '',
    materialType: '',
    packageType: '',
    product: {
        id: '',
        name: '',
        description: '',
        code: '',
        photoBase64: ''
    },
    sensors: []
});

const products = ref([]);

const route = useRoute();
const router = useRouter();

onMounted(async () => {
    const response = await axios.get(`/packages/${route.params.id}`);
    package_.value = response.data;
    const response2 = await axios.get(`/products`);
    products.value = response2.data;

    const response3 = await axios.get(`/sensors`);
    allSensors.value = response3.data;
});

async function submitForm() {
    const response = await axios.put(`/packages/${route.params.id}`, {
        code: package_.value.code,
        materialType: package_.value.materialType,
        packageType: package_.value.packageType,
        product: {
            id: package_.value.product.id
        },
        sensors: package_.value.sensors,
        id: package_.value.id
    });

    if (response.status === 200) {
        router.push({ name: 'ViewPackages'});
    }
}


</script>

<style scoped>
    img{
        width: 500px;
        object-fit: contain;
    }
</style>