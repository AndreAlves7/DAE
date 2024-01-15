<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { useToast } from "vue-toastification"
import { useRouter } from 'vue-router'
import { BIconRocketTakeoff } from 'bootstrap-icons-vue'

const toast = useToast()
const router = useRouter()

const newUser = ref({
  phone_number: '',
  password: '',
  name: '',
  email: '',
  confirmation_code: '',
  photo_url: '',
})

const errors = ref([])

const isValidInputs = () => {
    let isValid = true
    errors.value = {}
    if(newUser.value.phone_number.length !== 9) {
        errors.value['phoneNumber'] = ['Phone number must have 9 digits!']
        isValid = false
    }
    if(newUser.value.confirmation_code.length < 3) {
        errors.value['pin'] = ['Pin must have at least 3 digits!']
        isValid = false
    }
    if(newUser.value.name.length === 0) {
        errors.value['name'] = ['Name must not be empty!']
        isValid = false
    }
    if(newUser.value.email.length === 0) {
        errors.value['email'] = ['E-mail must not be empty!']
        isValid = false
    }
    if(newUser.value.password.length === 0) {
        errors.value['password'] = ['Password must not be empty!']
        isValid = false
    }
    
    return isValid
}

const signup = async () => {
    try {
        if(isValidInputs()){
            await axios.post('signup', newUser.value)
            toast.success('Congrats, you now have a vCard!')
            router.push({ name: 'Login' })
        } else {
            toast.error('User credentials are invalid!')
            newUser.value.password = ''
            newUser.value.confirmation_code = ''
        }
    } catch(error) {
        toast.error(error.response.data.message)
        newUser.value.password = ''
        newUser.value.confirmation_code = ''
    }
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
            newUser.value.photo_url = reader.result
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


</script>

<template>
    <section class="vh-100 gradient-custom">
        <div class="container py-5 h-100">
          <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
              <div class="card bg-dark text-white" style="border-radius: 1rem;">
                <div class="card-body p-5 text-center">
                  <div class="mb-md-4 mt-md-4 pb-5">
                    <h2 class="fw-bold mb-2 text-uppercase">
                      <BIconRocketTakeoff class="me-2 fs-1"/>
                      Rabolut
                    </h2>
                    <p class="text-white-50 mb-5">Start using vCard right now!</p>
                    <form novalidate @submit.prevent="signup">
                        <div class="form-group form-outline form-white mb-4">
                            <input v-model="newUser.phone_number" 
                                id="phoneNumberInput" 
                                class="form-control" 
                                :class="{ 'is-invalid': errors ? errors['phoneNumber'] : false }"
                                type="text" 
                                placeholder="Phone number" 
                                required />
                            <field-error-message :errors="errors" fieldName="phoneNumber"></field-error-message>
                        </div>
                        <div class="form-group form-outline form-white mb-4">
                            <input v-model="newUser.name" 
                                id="nameInput" 
                                class="form-control" 
                                :class="{ 'is-invalid': errors ? errors['name'] : false }"
                                type="text" 
                                placeholder="Name"
                                required />
                            <field-error-message :errors="errors" fieldName="name"></field-error-message>
                        </div>
                        <div class="form-group form-outline form-white mb-4">
                            <input v-model="newUser.email" 
                                id="emailInput" 
                                class="form-control" 
                                :class="{ 'is-invalid': errors ? errors['email'] : false }"
                                type="text" 
                                placeholder="E-mail"
                                required />
                            <field-error-message :errors="errors" fieldName="email"></field-error-message>
                        </div>
                        <div class="form-group form-outline form-white mb-4">
                            <input v-model="newUser.password" 
                                id="passwordInput" 
                                class="form-control" 
                                :class="{ 'is-invalid': errors ? errors['password'] : false }"
                                type="password" 
                                placeholder="Password"
                                required />
                            <field-error-message :errors="errors" fieldName="password"></field-error-message>
                        </div>
                        <div class="form-group form-outline form-white mb-4">
                            <input v-model="newUser.confirmation_code" 
                                id="pinInput" 
                                class="form-control" 
                                :class="{ 'is-invalid': errors ? errors['pin'] : false }"
                                type="password" 
                                placeholder="Pin"
                                required />
                            <field-error-message :errors="errors" fieldName="pin"></field-error-message>
                        </div>
                        <div class="mb-2">
                            <label for="formFileSm" class="form-label">Profile Photo</label>
                            <input v-on:change="handleFileChange" class="form-control form-control-sm" id="formFileSm" type="file">
                        </div>
                        <button @click="signup" class="btn btn-outline-light px-5 mt-5" type="button">Create account</button>
                    </form>
                  </div>
                  <p class="mb-0">Have an account? 
                    <router-link class="text-white-50 fw-bold" to="/login">Back to Log-in</router-link>
                    </p>
                </div>
              </div>
            </div>
          </div>
        </div>
    </section>
  </template>
  
  <style></style>