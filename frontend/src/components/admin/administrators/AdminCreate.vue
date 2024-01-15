
<script setup>
import { ref } from 'vue'
import { useAdminStore } from '../../../stores/admin.js'
import { useToast } from "vue-toastification"
import { useRouter } from 'vue-router'


const adminStore = useAdminStore()
const toast = useToast()
const router = useRouter()


const admin = ref({
    name: '',
    email: '',
    password: '',
})

const createAdmin = async () => {

    //a password tem de ter pelo menos 3 caracteres
    if (admin.value.password.length < 3) {
        toast.error('The password must have at least 3 characters!')
        return
    }


    if (await adminStore.createAdmin(admin.value)) {
        toast.success('Sucessfully created the administrator!');
        router.push({ name: 'Admins' })

    } else {
        toast.error('There was a problem creating the administrator!')
    }
}

const exit = () => {
    router.push({ name: 'Admins' });
}
</script>

<template>
    <div class="container">
        <div class="row">
            <div class="col-12 mt-3">
                <h2>Create Admin</h2>
                <form @submit.prevent="createAdmin">
                    <div class="mb-3">
                        <label for="name" class="form-label">Name</label>
                        <input required v-model="admin.name" type="text" class="form-control" id="name" placeholder="Name">
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email address</label>
                        <input required v-model="admin.email" type="email" class="form-control" id="email" placeholder="Enter email">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input required v-model="admin.password" type="password" class="form-control" id="password" placeholder="Password">
                    </div>

                    <div class="d-flex" style="gap: 25px;">
                        <button type="submit" class="btn btn-primary">Create</button>
                        <button type="button" class="btn btn-danger ml-2" @click="exit">Exit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>