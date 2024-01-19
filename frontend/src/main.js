import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import axios from 'axios'

const app = createApp(App)

const apiDomain = 'http://localhost:8080'

axios.defaults.baseURL = apiDomain + '/backend/api'
axios.defaults.headers.common['Content-type'] = 'application/json'


app.use(createPinia())
app.use(router)

app.mount('#app')
