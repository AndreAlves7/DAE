<script setup>
import axios from 'axios';
import { watch, ref, onMounted, computed } from 'vue'
import { useUserStore } from '../stores/user.js'
import { useToast } from "vue-toastification"
import { useVcardsStore } from "../stores/vcard.js"
import { useRouter} from 'vue-router';

const vcardsStore = useVcardsStore()
const toast = useToast()
const userStore = useUserStore()
const router = useRouter()

const { showProfilePopup } = defineProps(['showProfilePopup']);
const emit = defineEmits(['close-popup'])
const showConfirmPopup = ref(false);
const showDeletePopup = ref(false);
const errors = ref([])
const showConfirmationCodeInput = ref(null)
const vcard = ref(null)
const isUserAdmin = computed(() => userStore.userType === 'A')

const formData = ref({
    name: '',
    email: '',
    confirmation_code: '',
    password: '',
    photo_url: '',
    confirmation_pin:null,
    confirmation_password: null,
});

const deleteRequest = ref({
    phone_number: '',
    confirmation_pin:null,
    confirmation_password: null,
    user_type: null,
});

onMounted(() => {
  userStore.loadUser();
})

watch(() => {
    showConfirmationCodeInput.value = userStore.userType === 'V'
})

const loadVcard = async (phone_number) => {
    try {
        vcard.value = await vcardsStore.loadVcard(phone_number)
    } catch (error) {
        console.log(error)
    }
}


const clearFormData = () => {
    Object.assign(formData.value, {
        name: '',
        email: '',
        confirmation_code: '',
        password: '',
        photo_url: null,
        confirmation_password: null,
        confirmation_pin: null,
    });
    Object.assign(deleteRequest.value, {
    phone_number: '',
    confirmation_pin:null,
    confirmation_password: null,
    user_type: null,
  });
};

const closePopupWithClear = () => {
    closePopup()
    clearFormData()
}

const closePopup = () => {
    emit('close-popup');
};

const closeConfirmPopup = () => {
    showConfirmPopup.value = false
    clearFormData();
}

const closeDeletePopup = () => {
    showDeletePopup.value = false
    clearFormData();
}

const handleFileChange = (event) => {
  try{
    const file = event.target.files[0];

    if (!file) {
      formData.value.photo_url = null
    } else {
      const reader = new FileReader()
      reader.addEventListener(
          'load',
          () => {
            formData.value.photo_url = reader.result
          },
          false,
      )
      if (file) {
        reader.readAsDataURL(file)
      }
    }
  } catch (error) {
    console.log(error)
  }
};

const isValidInputs = () => {
    let isValid = true
    errors.value = {}
    console.log(formData.value)
    if (formData.value.confirmation_code?.length !== 0 && formData.value.confirmation_code.length < 3) {
        errors.value['pin'] = ['Pin must have at least 3 digits!']
        isValid = false
    }
    if (formData.value.password?.length !== 0 && formData.value.password.length < 3) {
        errors.value['pin'] = ['Password must have at least 3 digits!']
        isValid = false
    }

    return isValid
}

const submitForm = async () => {
    if (((formData.value.password != null && formData.value.password != "") ||
        (formData.value.confirmation_code != null) && formData.value.confirmation_code != "")) {
        if (isValidInputs()) {
          showConfirmPopup.value = true
          closePopup()
        } else {
            const allErrorMessages = Object.values(errors.value)
                .reduce((acc, messages) => acc.concat(messages), []);
            toast.error(`User data is invalid! Errors: ${allErrorMessages.join(', ')}`);
        }
        return;
    } else {
      performUpdate()
    }
}

const confirmPassword = async () => {
    try {
        performUpdate();
    } catch (error) {
        toast.error(error.response.data.msg);
    }
};

const performUpdate = async () => {
    try {
        if (isValidInputs()) {
            await axios.patch('/me', formData.value)
            toast.success('User data correctly Updated!')

            clearFormData()
            closeAllPopups()
            userStore.loadUser();
        } else {
            const allErrorMessages = Object.values(errors.value)
                .reduce((acc, messages) => acc.concat(messages), []);
            toast.error(`User data is invalid! Errors: ${allErrorMessages.join(', ')}`);
        }
    } catch (error) {
        toast.error(error.response.data.message)
    }
}

const showDeleteUserPopup = () => {
    showDeletePopup.value = true;
    closePopup()
}

const deleteUser = async () => {
    userStore.loadUser();
    loadVcard(userStore.user.id)
    vcardsStore.loadVcard(vcardsStore);

    errors.value = null
    if (vcardsStore.vcard.balance > 0) {
        toast.error('Balance must be 0 to delete VCard!');
        errors.value = { balance: ['Balance must 0'] }
        return;
    }
    try {
        deleteRequest.value.phone_number = userStore.user.id
        deleteRequest.value.user_type = userStore.userType
        await axios.delete('/me', { data: deleteRequest.value });
        toast.success(`VCard ${vcardsStore.vcard.phone_number} deleted successfully!`);

        logout()
    } catch (error) {
        toast.error(error.response.data.message)
    }
}

const logout = async () => {
  await userStore.logout()
    toast.success('Sucessfully logged out of the application!')
    closeAllPopups()
    router.push({ name: 'Login' })
  
}


const closeAllPopups = () => {
    closeDeletePopup()
    closeConfirmPopup()
    closePopup()
}

</script>



<template>
    <div v-if="showProfilePopup" class="popup">
      <button type="button" class="btn btn-outline-secondary close-button" aria-label="Close" @click="closePopupWithClear">
        <span aria-hidden="true">&times;</span>
      </button>
  
      <h2 class="mb-4">Manage User</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="name">Name:</label>
          <input v-model="formData.name" type="text" class="form-control" id="name" :placeholder="userStore.user.name">
        </div>
        <div class="form-group">
          <label for="email">Email:</label>
          <input v-model="formData.email" type="email" class="form-control" id="email" :placeholder="userStore.user.email">
        </div>
        <div v-if="showConfirmationCodeInput" class="form-group">
          <label for="confirmation_code">Confirmation Code:</label>
          <input v-model="formData.confirmation_code" type="password" class="form-control" id="confirmation_code" placeholder="Enter confirmation code">
        </div>
        <div class="form-group" style="margin-bottom: 20px;">
          <label for="password">Password:</label>
          <input v-model="formData.password" type="password" class="form-control" id="password" placeholder="Enter your password">
        </div>
        
        <div v-if="!isUserAdmin" class="mb-3">
          <label for="formFileSm" class="form-label">Profile Photo</label>
          <input v-on:change="handleFileChange" class="form-control form-control-sm" id="formFileSm" type="file">
        </div>
      
        <div class="main-submit-div">
          <button type="submit" class="btn btn-primary">Submit</button>
          <button v-if="!isUserAdmin" type="button" class="btn btn-danger" @click="showDeleteUserPopup">Delete User</button>
        </div>  
      
      </form>
    </div>

    <div v-if="showConfirmPopup" class="popup">
      <button type="button" class="btn btn-outline-secondary close-button" aria-label="Close" @click="closeConfirmPopup">
        <span aria-hidden="true">&times;</span>
      </button>

      <form @submit.prevent="confirmPassword" class="confirm-form">
        <h2 class="mb-4">Confirm Credentials</h2>

        <div v-if="!isUserAdmin" class="form-group">
          <label for="confirmPin">Confirm Your PIN</label>
          <input v-model="formData.confirmation_pin" type="password" class="form-control" id="confirmPin" placeholder="Enter your PIN">
        </div>

        <div class="form-group">
          <label for="confirmPassword">Confirm Your Password</label>
          <input v-model="formData.confirmation_password" type="password" class="form-control" id="confirmPassword" placeholder="Enter your password">
        </div>

        <div class="form-group mt-3 text-center">
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </form>
    </div>


    <div v-if="showDeletePopup" class="popup">
  <button type="button" class="btn btn-outline-secondary close-button" aria-label="Close" @click="closeDeletePopup">
    <span aria-hidden="true">&times;</span>
  </button>

  <form @submit.prevent="deleteUser" class="confirm-form">
    <h2 class="mb-4">Confirm Credentials</h2>

    <div v-if="!isUserAdmin" class="form-group mt-3">
      <label for="confirmPin">Confirm Your PIN</label>
      <input v-model="deleteRequest.confirmation_pin" type="password" class="form-control" id="confirmPin" placeholder="Enter your PIN">
    </div>

    <div class="form-group">
      <label for="confirmPassword">Confirm Your Password</label>
      <input v-model="deleteRequest.confirmation_password" type="password" class="form-control" id="confirmPassword" placeholder="Enter your password">
    </div>

    <div class="submit-div text-center mt-3">
      <button type="submit" class="btn btn-danger mx-auto">Delete</button>
    </div>
  </form>
</div>

  </template>

<style scoped>
/* Custom styles for the popup */
.popup {
  border-radius: 5%;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 400px;
  width: 100%;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #fff;
  z-index: 1000;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.close-button {
  position: absolute;
  top: 0;
  right: 0;
  margin: 15px;
}   

.close-button.btn {
  background-color: #007bff;
  border-color: #007bff; 
  color: #fff; 
}

.close-button.btn:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}

.submit-div {
  display: flex;
  flex-direction: row !important;
}

.main-submit-div{
  display: flex;
  flex-direction: row !important;
  justify-content: center;
  gap: 100px;
}

</style>