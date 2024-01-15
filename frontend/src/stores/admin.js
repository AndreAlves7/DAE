import axios from 'axios'
import { ref } from 'vue'
import { defineStore } from 'pinia'
// import avatarNoneUrl from '@/assets/avatar-none.png'

import routes from '../utils/routes'

export const useAdminStore = defineStore('administrators', () => {
    const admins = ref(null)

    const loadAdmins = async () => {
        try {
            const response = await axios.get(routes.admins)
            admins.value = response.data.data
        } catch (error) {
           console.log(error)
        }
    }

    const deleteAdmin = async (id) => {
        try {
            await axios.delete(routes.admins + '/' + id)
            loadAdmins()
            return true
        } catch (error) {
            console.log(error)
            return false
        }
    }

    const createAdmin = async (admin) => {
        try {
            await axios.post(routes.admins, admin)
            loadAdmins()
            return true
        } catch (error) {
            console.log(error)
            return false
        }
    }

    return {
        admins,
        loadAdmins,
        deleteAdmin,
        createAdmin
    }

})