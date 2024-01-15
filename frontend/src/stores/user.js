import axios from 'axios'
import { ref, computed, inject } from 'vue'
import { defineStore } from 'pinia'
import avatarNoneUrl from '@/assets/avatar-none.png'

import routes from '../utils/routes'

export const useUserStore = defineStore('user', () => {

    const serverBaseUrl = inject('serverBaseUrl')

    const socket = inject('socket')

    const user = ref(null)

    const userName = computed(() => user.value?.name ?? 'Anonymous')

    const userId = computed(() => user.value?.id ?? -1)

    const userType = computed(() => user.value?.user_type ?? 'Anonymous')

    const userPhotoUrl = computed(() =>
        user.value?.photo_url
        ? serverBaseUrl + '/storage/fotos/' + user.value.photo_url
        : avatarNoneUrl)

    async function loadUser() {
        try {
            const response = await axios.get('/me')
            user.value = response.data
        } catch (error) {
            clearUser()
            throw error
        }
    }

    function clearUser() {
        delete axios.defaults.headers.common.Authorization
        // sessionStorage.removeItem('token')
        // sessionStorage.removeItem('table-vcards')
        sessionStorage.clear()
        user.value = null
    }

    async function login(credentials) {
        try {
            const response = await axios.post(routes.login, credentials)
            axios.defaults.headers.common.Authorization = "Bearer " + response.data.access_token
            sessionStorage.setItem('token', response.data.access_token)
            await loadUser()
            socket.emit('loggedIn', user.value)
            return true
        }
        catch(error) {
            clearUser()
            return false
        }
    }

    async function logout () {
        try {
            await axios.post(routes.logout)
            socket.emit('loggedOut', user.value)
            clearUser()
            return true
        } catch (error) {
            clearUser()
            return false
        }
    }

    // async function changePassword(credentials) {
    //     if (userId.value < 0) {
    //         throw 'Anonymous users cannot change the password!'
    //     }
    //     try {
    //         await axios.patch(`users/${user.value.id}/password`, credentials)
    //         return true
    //     } catch (error) {
    //         throw error
    //     }
    // }


    async function restoreToken () {
        let storedToken = sessionStorage.getItem('token')
        if (storedToken) {
            axios.defaults.headers.common.Authorization = "Bearer " + storedToken
            await loadUser()
            socket.emit('loggedIn', user.value)
            return true
        }
        clearUser()
        return false
    }


    return {
        user,
        userId,
        userName,
        userType,
        userPhotoUrl,
        loadUser,
        clearUser,
        login,
        logout,
        restoreToken,
        // changePassword
    }
})
