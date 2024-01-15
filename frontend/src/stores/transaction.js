import { ref } from "vue"
import axios from "axios"
import { defineStore } from "pinia"
import routes from "../utils/routes.js"
import { useUserStore } from "./user.js"
import { inject } from "vue"
import { useToast } from "vue-toastification"

export const useTransactionStore = defineStore('transactions', () => {


    const socket = inject("socket")

    const toast = useToast()

    const transactions = ref([])
    const userStore = useUserStore()


    async function createManualTransaction(newTransaction){
        newTransaction.type = 'C'

        const response = await axios.post(routes.transactions, newTransaction)
        return response.data
    }


    async function loadTransactions() {
        
        try {
            // await userStore.loadUser()
            // console.log(userStore.user)
            const response = await axios.get('/vcard/transaction/' + userStore.user.id)
            
            transactions.value = response.data.transactions
            // console.log(transactions.value)
        } catch (error) {
            console.log(error)
        }

    }

    socket.on('NewTransaction', (transaction) => {
        console.log('NewTransaction')
        transactions.value.push(transaction)
        // toast.success(`A new transaction was created (#${transaction.id} : ${transaction.name})`)
    })

    console.log('useTransactionStore')

    return {
        createManualTransaction,
        loadTransactions,
        transactions
    }

})