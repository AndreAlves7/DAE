import { ref } from "vue"
import axios from "axios"
import { defineStore } from "pinia"
import { useUserStore } from '../stores/user.js'
import routes from '../utils/routes'

export const useCategoryStore = defineStore('category', () => {

    const userStore = useUserStore()

    const categories = ref([])

    const category = ref({})

    function clearCategories() {
        categories.value = []
    }

    async function loadCategories(phone_number) {
        try {
            const response = await axios.get(`vcard/${phone_number}/category`)
            categories.value = response.data.data
            return categories.value
        } catch (error) {
            clearCategories()
            throw error
        }
    }

    async function loadCategory(category_id) {  
        try {
            const response = await axios.get(`${routes.categories}/${category_id}`)
            category.value = response.data.data

            if(category.value){
                let categIndex = categories.value.findIndex(category => category.id === category_id)
                if(categIndex >= 0){
                    categories.value[categIndex] = category.value
                }
            }
            return category.value
        } catch (error) {
            category.value = {}
            throw error
        }
    }

    async function updateCategory(updateCategory) {
        if(categories.value.length == 0) {
            loadCategories(userStore.user.phone_number)
        }
        const response = await axios.put(`${routes.categories}/${updateCategory.id}`, updateCategory)
        let categIndex = categories.value.findIndex((i) => i.id === response.data.data.id)
        if (categIndex >= 0) {
            categories.value[categIndex] = response.data.data
        }
        return response.data.data
    }

    async function createCategory(newCategory) {
        if(!newCategory || !newCategory.vcard){
            return
        }
        const response = await axios.post(routes.categories, newCategory)
        categories.value.push(response.data.data)
        return response.data.data
    }
    
    async function deleteCategory(deleteCategory) {
        const response = await axios.delete(`${routes.categories}/${deleteCategory.id}`)
        let categIndex = categories.value.findIndex((categ) => categ.id === deleteCategory.id)
        if (categIndex >= 0) {
            categories.value.splice(categIndex, 1)
        }
        return response.data.data
    }

    return {
        categories,
        category,
        clearCategories,
        loadCategories,
        loadCategory,
        updateCategory,
        createCategory,
        deleteCategory
    }

})