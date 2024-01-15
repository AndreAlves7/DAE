<script setup>
import { ref } from 'vue'
import { useToast } from "vue-toastification"
import { useRouter, RouterLink } from 'vue-router'
import { useUserStore } from '../stores/user.js'
import { BIconRocketTakeoff } from 'bootstrap-icons-vue'

const toast = useToast()
const router = useRouter()
const userStore = useUserStore()

const credentials = ref({
  username: '',
  password: ''
})

const login = async () => {
  if (await userStore.login(credentials.value)) {
    toast.success('User ' + userStore.user.name + ' has entered the application.')
    router.push({ name: 'Dashboard' })
  } else {
    credentials.value.password = ''
    toast.error('User credentials are invalid!')
  }
}
</script>

<template>
  <section class="vh-100 gradient-custom">
      <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
          <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <div class="card bg-dark text-white" style="border-radius: 1rem;">
              <div class="card-body p-5 text-center">
                <form novalidate @submit.prevent="login">
                  <div class="mb-md-5 mt-md-4 pb-5">
                    <h2 class="fw-bold mb-2 text-uppercase">
                      <BIconRocketTakeoff class="me-2 fs-1"/>
                      Rabolut
                    </h2>
                    <p class="text-white-50 mb-5">Your favorite virtual card!</p>
                    <div class="form-group form-outline form-white mb-4">
                      <input v-model="credentials.username" id="emailInput" class="form-control" type="email" placeholder="Phone number" required />
                    </div>
                    <div class="form-group form-outline form-white mb-5">
                      <input v-model="credentials.password" id="passwordInput" class="form-control" type="password" placeholder="Password" required />
                    </div>
                    <button @click="login" class="btn btn-outline-light px-5" type="button">Login</button>
                  </div>
                  <div>
                    <p class="mb-0">Don't have an account? 
                      <router-link class="text-white-50 fw-bold" to="/signup">Sign Up</router-link>
                    </p>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
  </section>
</template>

<style></style>
