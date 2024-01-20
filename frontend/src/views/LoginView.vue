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
                        Login
                      </h2>
                      <p class="text-white-50 mb-5"></p>
                      <div class="form-group form-outline form-white mb-4">
                        <input v-model="credentials.username" id="usernameInput" class="form-control" type="text" placeholder="Username" required />
                      </div>
                      <div class="form-group form-outline form-white mb-5">
                        <input v-model="credentials.password" id="passwordInput" class="form-control" type="password" placeholder="Password" required />
                      </div>
                      <button @click="login" class="btn btn-outline-light px-5" type="button">Login</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
    </section>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '../stores/user.js'

const userStore = useUserStore()
const router = useRouter()

const credentials = ref({
  username: '',
  password: ''
})

const login = async () => {
    if (await userStore.login(credentials.value)) {
        alert('Login successful!')
        router.push({ name: 'dashboard' })
    } else {
        credentials.value.password = ''
        alert('User credentials are invalid!')
    }
}

</script>

<style scoped>
  .form-control {
    width: 100% !important;
  }
</style>