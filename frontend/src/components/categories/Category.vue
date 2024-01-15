<script setup>
import { ref, onMounted } from 'vue'
import { useToast } from "vue-toastification"
import { useRouter } from 'vue-router';
import { useCategoryStore } from "../../stores/categories.js"
import CategoryDetails from '../categories/CategoryDetail.vue'


const categoriesStore = useCategoryStore()

const toast = useToast()
const router = useRouter()

const category = ref(null)
const errors = ref(null)

let originalCategoryStr = ''

const props = defineProps({
  id: {
    type: Number,  
    default: null,
  }
})

const loadCategory = async (category_id) => {
    if(category_id == null) {
        return;
    }
    originalCategoryStr = ''
    try {
        category.value = await categoriesStore.loadCategory(category_id)
        originalCategoryStr = JSON.stringify(category.value)
    } catch (error) {
        console.log(error)
    }
}

const update = async () => {
    errors.value = null
    try {
        category.value = await categoriesStore.updateCategory(category.value)
        originalCategoryStr = JSON.stringify(category.value)
        toast.success(`Category updated successfully!`)
        router.back()
    } catch (error) {
        if (error.response.status == 422) {
            errors.value = error.response.data.errors
            toast.error(`Category was not updated due to validation errors!`);
        } else {
            toast.error(`Category was not updated due to unknown server error!`);
        }
    }
}

const remove = async () => {
    try {
        await categoriesStore.deleteCategory(category.value)
        toast.success(`Category removed successfully!`)
        router.back()
    } catch (error) {
        toast.error(`Category was not removed due to unknown server error!`);
        console.log(error)
    }
}

const cancel = () => {
  originalCategoryStr = JSON.stringify(category.value)
  router.back()
}

onMounted (() => {
    loadCategory(props.id)
})

</script>


<template>
    <CategoryDetails v-if="category" :category="category" :errors="errors" @update="update" @remove="remove" @cancel="cancel" ></CategoryDetails>
</template>