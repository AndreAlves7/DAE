import { ref, inject } from "vue"
import axios from "axios"
import { defineStore } from "pinia"
import routes from "../utils/routes.js"
import { useUserStore } from "./user.js"
import { useToast } from "vue-toastification"


export const useVcardsStore = defineStore('vcards', () => {

    const vcards = ref([])
    const socket = inject("socket")

    const userStore = useUserStore()
    const toast = useToast()

    const vcard = ref({})

    function clearVcards() {
        vcards.value = []
    }

    async function loadVcards() {
        try {
            const response = await axios.get(routes.vcard)
            vcards.value = response.data.data
            return vcards.value
        } catch (error) {
            clearVcards()
            throw error
        }
    }

    async function loadVcard(phone_number) {
        const response = await axios.get(routes.vcard + '/' + phone_number)
        vcard.value = response.data.data

        // update the current vcard in vcards ref array
        if(vcard.value){
            let vcardIndex = vcards.value.findIndex(vcard => vcard.phone_number === phone_number)
            if(vcardIndex >= 0){
                vcards.value[vcardIndex] = vcard.value
            }
        }
        return vcard.value
    }

    async function updateVcard(updateVcard) {
        const response = await axios.put('vcard/' + updateVcard.phone_number, updateVcard)
        let vcardIndex = vcards.value.findIndex((i) => i.phone_number === response.data.data.phone_number)
        if (vcardIndex >= 0) {
            vcards.value[vcardIndex] = response.data.data
        }
        return response.data.data
    }

    async function deleteVcard(deleteVcard) {
        const response = await axios.delete('vcard/' + deleteVcard.phone_number)
        let vcardIndex = vcards.value.findIndex((i) => i.phone_number === deleteVcard.phone_number)
        if (vcardIndex >= 0) {
            vcards.value.splice(vcardIndex, 1)
        }
        return response.data.data
    }


    socket.on('NewTransaction', async (transaction) => {
        console.log('newTransaction',transaction)
        console.log(userStore.userId)
        console.log(userStore.userId)
        toast.success(`A new transaction was created (#${transaction.id})`)
        await loadVcard(userStore.userId)
        
    })
    

    console.log('vcards store loaded')


    return {
        vcards,
        vcard,
        clearVcards,
        loadVcards,
        loadVcard,
        updateVcard,
        deleteVcard
    }

})