<template>
    <div class="edit-product">
        <h1>Editar Produto</h1>
        <form @submit.prevent="submitForm">
            <div class="form-group">
                <label for="name">Nome</label>
                <input type="text" id="name" v-model="product.name" />
            </div>
            <div class="form-group">
                <label for="description">Descrição</label>
                <textarea id="description" v-model="product.description"></textarea>
            </div>
            <div class="form-group">
                <label for="code">Código</label>
                <input type="text" id="code" v-model="product.code" />
            </div>
            <div class="form-group">
                <label for="photo">Foto</label>
                <input type="file" id="photo" @change="handlePhotoChange" />
                <img :src="product.photoBase64" alt="Imagem do Produto" v-if="product.photoBase64">
            </div>
            <button type="submit">Salvar</button>
        </form>
    </div>

</template>

<script setup>

import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

const product = ref({
    name: '',
    description: '',
    code: '',
    photoBase64: ''
});

const route = useRoute();
const router = useRouter();

onMounted(async () => {
    const response = await axios.get(`/products/${route.params.id}`);
    product.value = response.data;
});

async function submitForm() {
    const response = await axios.put(`/products/${route.params.id}`, {
        // name: product.value.name,
        // description: product.value.description,
        code: product.value.code,
        photoBase64: product.value.photoBase64
    });

    if (response.status === 200) {
        router.push('/products');
    }
}

function handlePhotoChange(event) {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = (event) => {
        product.value.photoBase64 = event.target.result;
    };

    reader.readAsDataURL(file);
}


</script>