import axios from 'axios'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'
import { io } from 'socket.io-client'
import { BootstrapIconsPlugin } from 'bootstrap-icons-vue';
import PrimeVue from 'primevue/config';
import ConfirmationService from 'primevue/confirmationservice';


// bootstrap-vue-next
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css'

import Toast from "vue-toastification"
import "vue-toastification/dist/index.css"

import 'primevue/resources/themes/bootstrap4-light-blue/theme.css'

import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import FieldErrorMessage from './components/global/FieldErrorMessage.vue'

import App from './App.vue'
import router from './router'


const apiDomain = import.meta.env.VITE_API_DOMAIN
// const wsConnection = import.meta.env.VITE_WS_CONNECTION
// app.provide('socket', io(wsConnection))
const wsConnection = import.meta.env.VITE_WS_CONNECTION


axios.defaults.baseURL = apiDomain + '/api'
axios.defaults.headers.common['Content-type'] = 'application/json'

const app = createApp(App)

app
  .provide('socket', io(wsConnection))
  .provide('serverBaseUrl', apiDomain)
  .use(Toast, {
    position: "top-center",
    timeout: 3000,
    closeOnClick: true,
    pauseOnFocusLoss: true,
    pauseOnHover: true,
    draggable: true,
    draggablePercent: 0.6,
    showCloseButtonOnHover: true,
    hideProgressBar: true,
    closeButton: "button",
    icon: true,
    rtl: false
  })
  .use(createPinia())
  .use(router)
  .use(BootstrapIconsPlugin)
  .use(PrimeVue)
  .use(ConfirmationService)
  .component('FieldErrorMessage', FieldErrorMessage)
  .mount('#app')
