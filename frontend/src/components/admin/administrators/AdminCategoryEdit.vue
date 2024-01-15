<script setup>
import axios from 'axios';
import { ref, onMounted} from 'vue'
import { useToast } from "vue-toastification"
import routes from "../../../utils/routes.js"

const toast = useToast()

const props = defineProps({
    showEditPopup: {
    type: Boolean,  
    default: false,
  },

  selectedCategory: {
    type: Object,
    default: []
  }
})

const emit = defineEmits(['close-popup'])
const errors = ref([])

const formData = ref({
    type: '',
    name: '',
});

onMounted(() => {
})


const clearFormData = () => {
    Object.assign(formData.value, {
        type: '',
        name: '',
    });
};

const closePopupWithClear = () => {
    closePopup()
    clearFormData()
}

const closePopup = () => {
    emit('close-popup');
};

const isValidInputs = () => {
    let isValid = true
    errors.value = {}
    if (formData.value.name.length !== 0 && formData.value.name.length > 30) {
        errors.value['name'] = ['Name is too large!']
        isValid = false
    }

    return isValid
}

const performUpdate = async () => {
    try {
        if (isValidInputs()) {
            await axios.patch(`${routes.defaultCategory}/${props.selectedCategory.id}`, formData.value)
            toast.success('User data correctly Updated!')

            closePopupWithClear()
        } else {
            const allErrorMessages = Object.values(errors.value)
                .reduce((acc, messages) => acc.concat(messages), []);
            toast.error(`User data is invalid! Errors: ${allErrorMessages.join(', ')}`);
        }
    } catch (error) {
        toast.error(error.response.data.message)
    }
}

</script>

<template>
    <div v-if="props.showEditPopup" class="popup">
      <button type="button" class="btn btn-outline-secondary close-button" aria-label="Close" @click="closePopupWithClear">
        <span aria-hidden="true">&times;</span>
      </button>
  
      <h2 class="mb-4">Manage Category</h2>

      <form @submit.prevent="performUpdate">
        <div class="form-group">
            <label for="Type">Type:</label>
            <select v-model="formData.type" class="form-control" id="type" >
            <option value="" disabled selected>Select your Option</option>
            <option value="D">Debit</option>
            <option value="C">Credit</option>
            </select>
        </div>
        <div class="form-group">
            <label for="Name">Name:</label>
            <input v-model="formData.name" type="text" class="form-control" id="name" :placeholder="props.selectedCategory.name">
        </div>

        <div class="form-group mt-3 text-center">
          <button type="submit" class="btn btn-success">Submit</button>
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
  background-color: #28a745;
  border-color: #28a745; 
  color: #fff; 
}

.close-button.btn:hover {
  background-color: #1a712e;
  border-color: #1a712e;
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