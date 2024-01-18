<template>
    <!-- list all products -->
    <div class="products">
        <h1>Produtos</h1>
        <div class="products-list">
            <div class="product" v-for="product in products" :key="product.id">
                <img :src="product.photoBase64" alt="Imagem do Produto" v-if="product.photoBase64">
                <p>Nome: {{ product.name }}</p>
                <p>Descrição: {{ product.description }}</p>
                <p>Código: {{ product.code }}</p>
            </div>
        </div>
    </div>

</template>


<script setup>

import { ref, onMounted } from 'vue';
import axios from 'axios';

const products = ref([]);

onMounted(async () => {
    const response = await axios.get('/products');
    products.value = response.data;
});


</script>