import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from "../stores/user.js"
import LoginView from '../views/LoginView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/',
      name: 'home',
      component: () => import('../views/DashboardView.vue')
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: () => import('../views/DashboardView.vue')
    },
    {
      path: '/createproduct',
      name: 'createProduct',
      component: () => import('../views/supplier/CreateProductView.vue')
    },    {
      path: '/createorder',
      name: 'createOrder',
      component: () => import('../views/customer/CreateOrderView.vue')
    },
    {
      path: '/viewproducts',
      name: 'ViewProducts',
      component: () => import('../views/supplier/ViewProductsView.vue')
    },    {
      path: '/vieworders',
      name: 'ViewOrders',
      component: () => import('../views/logistics/ViewOrdersView.vue')
    },
    {
      path: '/updateproduct/:id',
      name: 'UpdateProduct',
      component: () => import('../views/supplier/UpdateProductView.vue'),
    },
    {
      path: '/viewpackages',
      name: 'ViewPackages',
      component: () => import('../views/logistics/ViewPackagesView.vue')
    },
    {
      path: '/updatepackage/:id',
      name: 'UpdatePackage',
      component: () => import('../views/logistics/UpdatePackageView.vue'),
    },
    {
      path: '/createpackage',
      name: 'CreatePackage',
      component: () => import('../views/logistics/CreatePackageView.vue'),
    },
    { 
      path: '/createsensor',
      name: 'CreateSensor',
      component: () => import('../views/logistics/CreateSensorView.vue'),
    } ,
    {
      path: '/viewsensors',
      name: 'ViewSensors',
      component: () => import('../views/logistics/ViewSensorsView.vue')
    },    
    {
      path: '/sensorReadings/:sensorId',
      name: 'SensorReadings',
      component: () => import('../views/logistics/SensorReadingsView.vue')
    },  
    {
      path: '/importreadings',
      name: 'ImportReadings',
      component: () => import('../views/supplier/ImportReadingsView.vue')
    },  
     {
      path: '/orderchart/:orderId',
      name: 'OrderChart',
      component: () => import('../views/charts/OrderChartView.vue')
    },
    {
      path: '/updateOrder/:orderId',
      name: 'UpdateOrder',
      component: () => import('../views/customer/UpdateOrderView.vue')
    },
    {
      path: '/updatesensor/:id',
      name: 'UpdateSensor',
      component: () => import('../views/logistics/UpdateSensorView.vue'),
    }
  ]
})


let handlingFirstRoute = true

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()

  console.log('From:', from.name)
  console.log('To:', to.name)
  console.log('User:', userStore.user)

  if (userStore.user == null && to.name !== 'login') {
    next({ name: 'login' })
    return
  }

  if (handlingFirstRoute) {
    handlingFirstRoute = false
    await userStore.restoreToken()
  }

  if(userStore.user && to.name == 'login'){
    next({ name: 'dashboard' })
    return
  }

  if(userStore.user && to.name == 'home'){
    next({ name: 'dashboard' })
    return
  }

  next()
})

export default router
