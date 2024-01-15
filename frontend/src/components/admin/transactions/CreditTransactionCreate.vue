<script setup>
import { ref, onMounted, inject } from 'vue'
import { useVcardsStore } from "../../../stores/vcard.js"
import { useTransactionStore } from '../../../stores/transaction';
import avatarNoneUrl from '@/assets/avatar-none.png'
import Dropdown from 'primevue/dropdown';
import InputNumber from 'primevue/inputnumber';
import InputText from 'primevue/inputtext';
import { useToast } from "vue-toastification"

const serverBaseUrl = inject("serverBaseUrl")
const socket = inject("socket")

const toast = useToast()
const vcardsStore = useVcardsStore()
const transactionStore = useTransactionStore()

const vcards = ref([])
const selectedVcard = ref()
const valueToSend = ref()
const paymentReference = ref()

const selectedPaymentType = ref()
const paymentTypes = ref([
    { name: 'MBWAY', code: 'MBWAY' },
    { name: 'PayPal', code: 'PAYPAL' },
    { name: 'IBAN', code: 'IBAN' },
    { name: 'MB', code: 'MB' },
    { name: 'Visa', code: 'VISA' }
]);

const loadVcards = async () => {
    try {
        vcards.value = await vcardsStore.loadVcards()
    } catch (error) {
        console.log(error)
    }
}

const getPhotoFullUrl = (vcard) => {
    return vcard.photo_url
        ? serverBaseUrl + "/storage/fotos/" + vcard.photo_url
        : avatarNoneUrl;
}

const save = async () => {
  if(selectedVcard.value == null || valueToSend.value == null || 
  selectedPaymentType.value == null || paymentReference.value == null) {
    toast.error("Please fill all the fields")
    return
  }

  if(valueToSend.value <= 0) {
    toast.error("The value to send must be higher than 0")
    return
  }

  if(!validatePaymentRef()) {
    return
  }

  try {
   const response = await transactionStore.createManualTransaction({
        vcard: selectedVcard.value.phone_number,
        value: valueToSend.value,
        payment_type: selectedPaymentType.value.name,
        payment_reference: paymentReference.value
    })
    toast.success("Manual transaction created successfully")
    console.log(response)
    socket.emit('NewTransaction', response)
  } catch (error) {
    toast.error(error.response.data.message)
  }
}

function validatePaymentRef(){
  console.log(selectedPaymentType.value)
  switch(selectedPaymentType.value.code){
    case 'MBWAY':
      if(!paymentReference.value.match(/^9[0-9]{8}$/)) {
        toast.error("The MBWAY payment reference must have 9 digits")
        return false;
      }
      return true;
    case 'PAYPAL':
      if(!paymentReference.value.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)) {
        toast.error("The PayPal payment reference is not valid")
        return false;
      }
      return true;
    case 'IBAN':
      if(!paymentReference.value.match(/^[A-Z]{2}[0-9]{23}$/)) {
        toast.error("The payment reference must have 2 letters and 23 digits")
        return false;
      }
      return true;
    case 'MB':
      if(!paymentReference.value.match(/^[0-9]{9}$/)) {
        toast.error("The payment reference must have 9 digits")
        return false;
      }
      return true;
    case 'VISA':
      if(!paymentReference.value.match(/^[0-9]{9}$/)) {
        toast.error("The payment reference must have 9 digits")
        return false;
      }
      return true;
    default:
      return false;
  }
}

onMounted (() => {
    loadVcards()
})
</script>

<template>
    <div class="container">
      <div class="row">
        <div class="mt-3">
            <h2>Manual Transaction</h2>
            <hr>
        </div>

        <form
          class="row g-3 needs-validation"
          novalidate
          @submit.prevent="save"
        >
        <div>
          <div class="card bg-light p-3">
            <div class="mb-2">
                <div class="mt-3">
                    <label for="vcardRecipient" class="font-bold block mb-2"> VCard Recipient </label>
                    <Dropdown 
                    v-model="selectedVcard" 
                    :options="vcards" 
                    optionLabel="phone_number" 
                    inputId="vcardRecipient"
                    filter 
                    filterPlaceholder="Search by phone number" 
                    placeholder="Select a vCard"
                    style="margin-left:40px;">
                    </Dropdown>
                </div>

                <div class="mt-3">
                    <label for="moneyValue" class="font-bold block mb-2"> Recipient will get </label>
                    <InputNumber v-model="valueToSend" inputId="moneyValue" mode="currency" currency="EUR" locale="pt-PT" placeholder="Enter value" style="margin-left:33px;"/>
                </div>

                <div class="mt-3">
                  <label>Payment Type</label>
                  <Dropdown v-model="selectedPaymentType" :options="paymentTypes" optionLabel="name" placeholder="Select a payment type" class="w-full md:w-14rem" style="margin-left:52px;" />
                </div>

                <div class="mt-3">
                    <label for="payment_reference">Payment Reference</label>
                    <InputText id="payment_reference" v-model="paymentReference" placeholder="Enter reference" style="margin-left:20px;"/>
                </div>
            </div>
        </div>

          <div class="row mb-3 mt-5">
            <div class="col-6 d-flex justify-content-end">
                <button
                type="button"
                class="btn btn-light px-5"
                @click="cancel">Cancel</button>
            </div>
            <div class="col-6 d-flex justify-content-start">
                <button
                type="button"
                class="btn btn-primary px-5"
                @click="save">Confirm</button>
            </div>
          </div>
        </div>
        </form>
      </div>
    </div>
</template>