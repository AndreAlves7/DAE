<script setup>
import { onMounted } from 'vue'
import AdminTable from './AdminTable.vue'
import { useAdminStore } from '../../../stores/admin.js'


const adminStore = useAdminStore()


onMounted(() => {
    adminStore.loadAdmins()
})


</script>

<template>
    <div class="container">
        <div class="row">
        <div class="col-12 mt-3">
            <h2>Admins</h2>
            <AdminTable :admins="adminStore.admins"></AdminTable>
        </div>
        </div>
    </div>
</template>