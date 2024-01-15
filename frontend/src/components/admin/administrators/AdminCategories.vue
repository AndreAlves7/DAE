<script setup>
import axios from 'axios';
import { ref ,computed, onMounted} from "vue";
import { BIconSearch, BIconPencil, BIconTrash } from 'bootstrap-icons-vue'
import AdminCategoryEdit from '../administrators/AdminCategoryEdit.vue';

import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';
import { FilterMatchMode  } from 'primevue/api';
import { useAdminStore } from '../../../stores/admin.js'
import { useToast } from "vue-toastification"
import { useRouter } from 'vue-router';

const toast = useToast()
const adminStore = useAdminStore()
const router = useRouter();

const categories = ref([])
const showEditPopup = ref(false)

const modalDelete = ref(false)
const categoryToDelete = ref(null)
const selectedCategory = ref(null)

const clickEditButton = (data) => {
    selectedCategory.value = data
    showEditPopup.value = true
}

const handleClosePopup = () => {
    showEditPopup.value = false;
    loadCategories();
};

const loadCategories = async () => {
    try {
        await adminStore.loadAdmins()
        const response = await axios.get('/defaultcategory')
        
        categories.value = response.data
        loading.value = false
    } catch (error) {
        console.log(error)
        loading.value = false
    }
}

const filters = ref(
    {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    }
);


const loading = ref(true);

onMounted (() => {
    loadCategories()
})


const deleteSelectedRow = async () =>{
    
    try {
        await axios.delete('/defaultcategory/' + categoryToDelete.value.id);
        toast.success(`Category deleted successfully!`);
    } catch (error) {
        toast.error(error.response)
    }finally{
        modalDelete.value = false
        loadCategories()
    }
}

    const modalDeleteUser = async (data) => {
    categoryToDelete.value = data
    modalDelete.value = true
    };

    const clickCreateButton = () => {
        router.push({ name: 'DefaultCategoryCreate' })
    }

</script>

<template>

<BModal v-model="modalDelete" :title="'Delete category ' + categoryToDelete?.name">
          <p>Are you sure you want to delete the category?</p>

          <template #footer="{ cancel }">
            <button class="btn btn-secondary" @click="cancel()">Cancel</button>
            <button class="btn btn-danger" @click="deleteSelectedRow(userToDelete)">Delete</button>
          </template>
        </BModal>

<AdminCategoryEdit :showEditPopup="showEditPopup" :selectedCategory="selectedCategory" @close-popup="handleClosePopup"/>

<div class="container">
        <div class="row">
        <div class="col-12 mt-3">
            <div class="mb-3">
                <h2>Default Categories</h2>
                <hr>
            </div>
            
            
<DataTable v-model:filters="filters" :metaKeySelection="false" :loading="loading" 
    removableSort  v-model:selection="selectedCategory" :value="categories"
    stateStorage="session" stateKey="table-vcards" paginator :rows="10" filterDisplay="menu"
    selectionMode="single" :globalFilterFields="['type', 'name']"
    sortField="type" :sortOrder="-1" >
    
    
    <template #header>
    <div class="d-flex justify-content-between align-items-center">
        <span class="p-input-icon-left">
        <BIconSearch />
        <InputText v-model="filters['global'].value" placeholder="Keyword Search" />
        </span>
        <button type="button" class="btn btn-primary" @click="clickCreateButton()">
        Create Default Category
        </button>
    </div>
    </template>



    <Column field="type" header="Type" sortable filterMatchMode="contains" style="width: 10%">
      <template #body="{ data }">
        <div class="flex align-items-center gap-2">
            <span>
            {{ 
                data.type === 'C' ? 'Credit' : (data.type === 'D' ? 'Debit' : '')
            }}
            </span>
        </div>
      </template>
    </Column>
    <Column field="name" header="Name" sortable filterMatchMode="contains" style="width: 15%">
      <template #body="{ data }">
        <div class="flex align-items-center gap-2">
          <span>{{ data.name }}</span>
        </div>
      </template>
    </Column>
    <Column header="Actions" style="width: 5%">
      <template #body="{ data }">
        <div>
            <button class="btn btn-xs btn-light" @click="clickEditButton(data)">
                    <BIconPencil class="bi bi-xs" />
                </button>

                <button class="btn btn-xs btn-light" @click="modalDeleteUser(data)" style="margin-left: 10px;">
                    <BIconTrash class="bi bi-xs" />
            </button>
        </div>
      </template>
    </Column>

    <template #empty> No Categories found. </template>
    <template #loading> Loading data. Please wait. </template>
</DataTable>
             </div>
        </div>
    </div>

</template>
