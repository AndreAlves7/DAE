import axios from 'axios'
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {

    const user = ref(null)

    const userTypes = {
        1: "Product manufacturer",
        2: "Logistics operator",
        3: "Final consumer"
    }

    const userType = computed(() => user.value?.type ?? 'Anonymous')

    const isManufacturer = computed(() => userType.value === 1)
    const isOperator = computed(() => userType.value === 2)
    const isConsumer = computed(() => userType.value === 3)


    // MANUFACTURER(1, "Product manufacturer"),
    // OPERATOR(2, "Logistics operator"),
    // CONSUMER(3, "Final consumer");

    async function loadUser() {
        try {
            const response = await axios.get('/auth/me')
            user.value = response.data
        } catch (error) {
            clearUser()
            throw error
        }
    }

    function clearUser() {
        delete axios.defaults.headers.common.Authorization
        sessionStorage.clear()
        user.value = null
    }

    async function login(credentials) {
        try {
            const response = await axios.post("/auth/login", credentials)
            axios.defaults.headers.common.Authorization = "Bearer " + response.data
            sessionStorage.setItem('token', response.data)
            await loadUser()
            return true
        }
        catch(error) {
            clearUser()
            return false
        }
    }

    async function logout () {
        try {
            //await axios.post(routes.logout)
            clearUser()
            return true
        } catch (error) {
            clearUser()
            return false
        }
    }


    async function restoreToken () {
        let storedToken = sessionStorage.getItem('token')
        if (storedToken) {
            axios.defaults.headers.common.Authorization = "Bearer " + storedToken
            await loadUser()
            return true
        }
        clearUser()
        return false
    }


    return {
        user,
        userType,
        loadUser,
        clearUser,
        restoreToken,
        login,
        logout,
        userTypes,
        isManufacturer,
        isOperator,
        isConsumer
    }
})
