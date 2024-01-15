import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from "../stores/user.js"

import Login from "../components/Login.vue"
import Dashboard from "../components/Dashboard.vue"
import Signup from "../components/auth/Signup.vue"
import Vcards from "../components/admin/Vcards.vue"
import Vcard from "../components/admin/Vcard.vue"
import Admins from "../components/admin/administrators/Admin.vue"
import adminCreate from "../components/admin/administrators/adminCreate.vue"
import AdminCategories from "../components/admin/administrators/AdminCategories.vue"
import DefaultCategoryCreate from "../components/admin/administrators/DefaultCategoryCreate.vue"
import CreditTransaction from "../components/admin/transactions/CreditTransactionCreate.vue"
import Transactions from "../components/TransactionsTable.vue"
import Transaction from "../components/Transaction.vue"
import MakeTransaction from "../components/vcard/MakeTransaction.vue"
import Categories from "../components/categories/Categories.vue"
import Category from "../components/categories/Category.vue"
import CategoryCreate from "../components/categories/CategoryCreate.vue"

let handlingFirstRoute = true

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: Dashboard
        },
        {
            path: '/login',
            name: 'Login',
            component: Login
        },
        {
            path: '/signup',
            name: 'Signup',
            component: Signup
        },
        {
            path: '/dashboard',
            name: 'Dashboard',
            component: Dashboard
        },
        {
            path: '/maketransaction',
            name: 'MakeTransaction',
            component: MakeTransaction
        },
        {
            path: '/admin/vcards',
            name: 'Vcards',
            component: Vcards
        },
        {
            path: '/admin/vcards/:phone_number',
            name: 'Vcard',
            component: Vcard,
            props: route => ({ phone_number: parseInt(route.params.phone_number) })
        },
        {
            path: '/admin/admins',
            name: 'Admins',
            component: Admins
        },
        {
            path: '/admin/add',
            name: 'AddAdmin',
            component: adminCreate
        },
        {
            path: '/vcard/transactions',
            name: 'Transactions',
            component: Transactions
        },  
         {
            path: '/admin/categories',
            name: 'AdminCategories',
            component: AdminCategories
        },
        {
            path: '/admin/transactions/add',
            name: 'CreditTransaction',
            component: CreditTransaction
        },
        {
            path: '/vcard/category',
            name: 'Categories',
            component: Categories
        },
        {
            path: '/vcard/category/:id',
            name: 'Category',
            component: Category,
            props: route => ({ id: parseInt(route.params.id)})
        },
        {
            path: '/vcard/category/add',
            name: 'AddCategory',
            component: CategoryCreate
        },   
        {
            path: '/admin/category/add',
            name: 'DefaultCategoryCreate',
            component: DefaultCategoryCreate
        },
        {
            path: '/vcard/transactions/:id',
            name: 'Transaction',
            component: Transaction,
        }
    ]
})

router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore()
    if (handlingFirstRoute) {
      handlingFirstRoute = false
      await userStore.restoreToken()
    }
    if ((to.name == 'Login') || (to.name == 'Signup')) {
      next()
      return
    }
    if (!userStore.user) {
      next({ name: 'Login' })
      return
    }
    if(userStore.userType == 'A' && (to.name == 'home')) {
        next({name: 'Vcards'})
        return
    }
    next()
})

export default router