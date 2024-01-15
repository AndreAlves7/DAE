<script setup>
import {  ref } from "vue";
import { BIconSearch, BIconTrash } from 'bootstrap-icons-vue'
import {useAdminStore} from '../../../stores/admin.js'
import { useToast } from "vue-toastification"


import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';
import { FilterMatchMode  } from 'primevue/api';


const selectedAdmin = ref(null)
const props = defineProps({
  admins: {
    type: Array,  
    default: () => [],
  },
})

const adminStore = useAdminStore()

const toast = useToast()
const modalDelete = ref(false)
const userToDelete = ref(null)


const deleteUser = async () => {
  if ( await adminStore.deleteAdmin(userToDelete.value.id)) {
    toast.success('Sucessfully deleted the administrator!')
  } else {
    toast.error('There was a problem deleting the administrator!')
  }
  modalDelete.value = false
}

const modalDeleteUser = async (admin) => {

  userToDelete.value = admin
  modalDelete.value = true

};


const filters = ref(
    {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    }
);


</script>

<template>

<BModal v-model="modalDelete" :title="userToDelete?.name">
          <p>Are you sure you want to delete the administrator?</p>

          <template #footer="{ cancel }">
            <button class="btn btn-secondary" @click="cancel()">Cancel</button>
            <button class="btn btn-danger" @click="deleteUser(userToDelete)">Delete</button>
          </template>
  </BModal>

  <DataTable v-model:filters="filters" :metaKeySelection="false" :loading="loading" 
    removableSort  v-model:selection="selectedAdmin" :value="props.admins"
    stateStorage="session" stateKey="table-vcards" paginator :rows="10" filterDisplay="menu"
    selectionMode="single" :globalFilterFields="['type', 'email']"
    sortField="type" :sortOrder="-1" >
    
    
    <template #header>
    <div class="d-flex justify-content-between align-items-center">
        <span class="p-input-icon-left">
        <BIconSearch />
        <InputText v-model="filters['global'].value" placeholder="Keyword Search" />
        </span>
    
      <div class="d-flex justify-content-end">
        <router-link class="btn btn-primary" :to="{ name: 'AddAdmin' }">Create Admin</router-link>
      </div>
      </div>
    </template>



    <Column field="name" header="Type" sortable filterMatchMode="contains" style="width: 10%">
      <template #body="{ data }">
        <div class="flex align-items-center gap-2">
            <span>
            {{ data.name }}
            </span>
        </div>
      </template>
    </Column>
    <Column field="email" header="Email" sortable filterMatchMode="contains" style="width: 15%">
      <template #body="{ data }">
        <div class="flex align-items-center gap-2">
          <span>{{ data.email }}</span>
        </div>
      </template>
    </Column>

    <Column header="Actions" style="width: 5%">
      <template #body="{ data }">
        <div class="d-flex ">
              <button class="btn btn-xs btn-light" @click="modalDeleteUser(data)" >
                <BIconTrash class="bi bi-xs" />
              </button>
            </div>
      </template>
    </Column>

    <template #empty> No Admins found. </template>
    <template #loading> Loading data. Please wait. </template>
</DataTable>

<!-- 
   <table class="table">
      <thead>
        <tr>
          <th>Name</th>
          <th>Email</th>
          <th>Delete</th>
        </tr>
      </thead>
      <tbody>
        <BModal v-model="modalDelete" :title="userToDelete?.name">
          <p>Are you sure you want to delete the administrator?</p>

          <template #footer="{ cancel }">
            <button class="btn btn-secondary" @click="cancel()">Cancel</button>
            <button class="btn btn-danger" @click="deleteUser(userToDelete)">Delete</button>
          </template>
        </BModal>
        
        <tr v-for="admin in props.admins" :key="admin.id" >
          <td>{{ admin.name }}</td>
          <td>{{ admin.email }}</td>
          <td class="text-end" >
            <div class="d-flex justify-content-end">
              <button class="btn btn-xs btn-light" @click="modalDeleteUser(admin)" >
                <BIconTrash class="bi bi-xs" />
              </button>
            </div>
          </td>
        </tr>
      </tbody>
   </table>
  -->

</template>

<style scoped>
.img_photo {
  width: 2.6rem;
  height: 2.6rem;
}
</style>