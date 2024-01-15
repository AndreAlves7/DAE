<script setup>
import { ref, onMounted } from 'vue'
import VcardDetail from './VCardDetail.vue'
import { useVcardsStore } from "../../stores/vcard.js"
import { useToast } from "vue-toastification"
import { useRouter } from 'vue-router';

const toast = useToast()
const router = useRouter()
const vcardsStore = useVcardsStore()

const props = defineProps({
  phone_number: {
    type: Number,  
    default: null,
  }
})

const vcard = ref(null)
const errors = ref(null)

let originalVcardStr = ''

const loadVcard = async (phone_number) => {
    originalVcardStr = ''
    try {
        vcard.value = await vcardsStore.loadVcard(phone_number)
        originalVcardStr = JSON.stringify(vcard.value)
    } catch (error) {
        console.log(error)
    }
}

const update = async () => {
    errors.value = null
    try {
        vcard.value = await vcardsStore.updateVcard(vcard.value)
        originalVcardStr = JSON.stringify(vcard.value)
        toast.success(`VCard #${ vcard.value.phone_number } updated successfully!`)
        router.back()
    } catch (error) {
        if (error.response.status == 422) {
            errors.value = error.response.data.errors
            toast.error(`VCard #${ vcard.value.phone_number } was not updated due to validation errors!`);
        } else {
            toast.error(`VCard #${ vcard.value.phone_number } was not updated due to unknown server error!`);
        }
    }
}

const remove = async () => {
    errors.value = null
    if(vcard.value.balance > 0) {
        toast.error('Balance must be 0 to delete VCard!');
        errors.value = { balance: ['Balance must 0'] }
        return;
    }
    try {
        console.log('*** REMOVE VCARD ***')
        await vcardsStore.deleteVcard(vcard.value)
        originalVcardStr = ''
        toast.success(`VCard ${ vcard.value.phone_number } deleted successfully!`);
        router.back()
    } catch (error) {
        if (error.response.status == 422) {
            errors.value = error.response.data.errors
            toast.error(`VCard #${ vcard.value.phone_number } was not deleted due to validation errors!`);
        } else {
            toast.error(`VCard #${ vcard.value.phone_number } was not deleted due to unknown server error!`);
        }
    }
}

const cancel = () => {
  originalVcardStr = JSON.stringify(vcard.value)
  router.back()
}

onMounted (() => {
    loadVcard(props.phone_number)
})

</script>

<template>
    <vcard-detail v-if="vcard" :vcard="vcard" :errors="errors" @update="update" @remove="remove" @cancel="cancel"></vcard-detail>
</template>