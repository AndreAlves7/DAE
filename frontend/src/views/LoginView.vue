<template>
    <div>
        <h1>Login</h1>
        <form @submit.prevent="login">
            <div>
                <label for="username">Username</label>
                <input type="text" id="username" v-model="credentials.username">
            </div>
            <div>
                <label for="password">Password</label>
                <input type="password" id="password" v-model="credentials.password">
            </div>
            <button type="submit">Entrar</button>
        </form>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '../stores/user.js'

const userStore = useUserStore()
const router = useRouter()

const credentials = ref({
  username: '',
  password: ''
})

const login = async () => {
    if (await userStore.login(credentials.value)) {
        alert('Login successful!')
        router.push({ name: 'ViewProducts' })
    } else {
        credentials.value.password = ''
        alert('User credentials are invalid!')
    }
}

</script>