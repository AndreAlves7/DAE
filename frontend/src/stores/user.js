import axios from 'axios'
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {

    const user = ref(null)
    const userType = computed(() => user.value?.user_type ?? 'Anonymous')

    // async function loadUser() {
    //     try {
    //         const response = await axios.get('/me')
    //         user.value = response.data
    //     } catch (error) {
    //         clearUser()
    //         throw error
    //     }
    // }

    // function clearUser() {
    //     delete axios.defaults.headers.common.Authorization
    //     sessionStorage.clear()
    //     user.value = null
    // }

    async function login(credentials) {
        try {
            const response = await axios.post("/auth/login", credentials)
            axios.defaults.headers.common.Authorization = "Bearer " + response.data
            sessionStorage.setItem('token', response.data)
            // await loadUser()
            return true
        }
        catch(error) {
            // clearUser()
            return false
        }
    }

    // async function logout () {
    //     try {
    //         await axios.post(routes.logout)
    //         socket.emit('loggedOut', user.value)
    //         clearUser()
    //         return true
    //     } catch (error) {
    //         clearUser()
    //         return false
    //     }
    // }


    // async function restoreToken () {
    //     let storedToken = sessionStorage.getItem('token')
    //     if (storedToken) {
    //         axios.defaults.headers.common.Authorization = "Bearer " + storedToken
    //         await loadUser()
    //         socket.emit('loggedIn', user.value)
    //         return true
    //     }
    //     clearUser()
    //     return false
    // }


    return {
        user,
        userType,
        // clearUser,
        login
        // logout
    }
})
