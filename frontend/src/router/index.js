import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // {
    //   path: '/login',
    //   name: 'login',
    //   component: () => import('../views/LoginView.vue')
    // },
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
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
    }
  ]
})

// router.beforeEach(async (to, from, next) => {
//   const userStore = useUserStore()
//   if (handlingFirstRoute) {
//     handlingFirstRoute = false
//     await userStore.restoreToken()
//   }
//   if ((to.name == 'Login') || (to.name == 'Signup')) {
//     next()
//     return
//   }
//   if (!userStore.user) {
//     next({ name: 'Login' })
//     return
//   }
//   if(userStore.userType == 'A' && (to.name == 'home')) {
//       next({name: 'Vcards'})
//       return
//   }
//   next()
// })

export default router
