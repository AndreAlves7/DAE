<script setup>
import { ref, onMounted } from 'vue'
import CategoriesTable from './CategoriesTable.vue'
import { useCategoryStore } from "../../stores/categories.js"
import { useUserStore } from '../../stores/user.js'
import { useRouter } from 'vue-router'
import { useToast } from "vue-toastification"


const toast = useToast()
const router = useRouter()
const userStore = useUserStore()
const categoriesStore = useCategoryStore()

const categories = ref([])
const loadingTable = ref(true)

const loadCategories = async () => {
    try {
      categories.value = await categoriesStore.loadCategories(userStore.user.username)
      loadingTable.value = false
    } catch (error) {
        console.log(error)
    }
}

const edit = (category_id) => {
  router.push({ name: 'Category', params: { id: category_id } })
}

const remove = async (category) => {
  try {
      await categoriesStore.deleteCategory(category)
      toast.success(`Category removed successfully!`)
      loadCategories()
  } catch (error) {
      toast.error(`Category was not removed due to unknown server error!`);
      console.log(error)
  }
}

onMounted (() => {
  loadCategories()
})
</script>

<template>
    <div class="container">
      <div class="row">
        <div class="col-12 mt-3">
          <h2>Categories</h2>
          <hr />
        </div>
      </div>
      <div class="row mb-4">
        <div class="col-12 d-flex justify-content-end">
          <router-link :to="{ name: 'AddCategory' }" class="btn btn-success">Create new</router-link>
        </div>
      </div>
      <div class="row">
        <div class="col-12">
          <CategoriesTable :categories="categories" :loadingTable="loadingTable" @edit="edit" @remove="remove"></CategoriesTable>
        </div>
      </div>
    </div>
</template>