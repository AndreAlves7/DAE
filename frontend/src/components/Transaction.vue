<script setup>
import { ref, onMounted } from 'vue';
import routes from '../utils/routes';
import axios from 'axios';
import { useRoute } from 'vue-router';
import Dropdown from 'primevue/dropdown';
import { useUserStore } from '../stores/user.js';
import { useToast } from 'vue-toastification';
import { useRouter } from 'vue-router';



const editingTransaction = ref({
  amount: '',
  date: '',
  description: '',
  type: '',
});

const categories = ref([]);
const selectedCategory = ref('');


const $route = useRoute();
const userStore = useUserStore();
const toast = useToast();
const router = useRouter();

const save = () => {
  console.log('Transaction saved', editingTransaction.value);
  axios.patch(`${routes.transactions}/${$route.params.id}`, {
    description: editingTransaction.value.description,
    category_id: selectedCategory.value.id
  })
    .then(function (response) {
      console.log(response);
      toast.success('Transaction updated successfully');
      router.push({ name: 'Transactions' })
    })
    .catch(function (error) {
      console.log(error);
      toast.error('Error updating transaction');
    });
  // Implement save logic
};

const cancel = () => {
  console.log('Operation cancelled');
  // Implement cancel logic
  router.push({ name: 'Transactions' })
};

// const remove = () => {
//   console.log('Transaction removed');
//   // Implement remove logic
// };

onMounted(() => {
  console.log('Transaction mounted');
  // Implement mounted logic

    // Load transaction from API
    axios
      .get(`${routes.transactions}/${$route.params.id}`)
      .then(function (response) {
        console.log(response.data);
        editingTransaction.value = response.data;
        getCategories()

      })
      .catch(function (error) {
        console.log(error);
      });


});



const getCategories = () => {
    axios.get(`${routes.vcard}/${userStore.userId}/category`)
      .then(function (response) {
        console.log(response);
        const { data }  = response.data
        const categoriesFiltered = data.filter(category => category.type === editingTransaction.value.type)
        categoriesFiltered.unshift({id: null, name: 'No Category'})

        categories.value = categoriesFiltered

        selectedCategory.value = categoriesFiltered.find(category => category.id === editingTransaction.value.category_id)
      })
      .catch(function (error) {
        console.log(error);
      });
  }



</script>


<template>
    <div class="container">
      <div class="row">
        <form
          class="row g-3 needs-validation"
          novalidate
          @submit.prevent="save"
        >
        <div>
            <div>
			    <h3>Transaction Details</h3>
			    <hr>
		    </div>
          <div class="row">
            <div class="mb-2">
              <div class="mt-3 p-3 card bg-light">
                <div class="mb-3">
					<label class="form-label">Transaction Amount</label>
  					<input v-model="editingTransaction.value" type="number" class="form-control" disabled aria-label="Transaction Amount" >
                </div>
                <div class="mb-3">
					<label class="form-label">Type</label>
  					    <input type="text" class="form-control" disabled aria-label="Transaction Amount" :value="editingTransaction.type == 'D' ? 'Debit': 'Credit'">
                </div>
                <div class="mb-3">
					<label class="form-label">Transaction Date</label>
  					<input v-model="editingTransaction.date" type="date" class="form-control" disabled aria-label="Transaction Date" >
                </div>
                <div>
					        <label class="form-label">Description</label>
  					      <input v-model="editingTransaction.description" type="text" class="form-control" aria-label="Description" >
                </div>
                
                <div class="mt-3">
                    <label class="form-label">Category</label><br/>
                    <Dropdown v-model="selectedCategory" :options="categories" optionLabel="name" placeholder="Category" class="w-full"  />
                  </div>
              </div>
            </div>
          </div>

          <div class="row mb-3 mt-5">
            <div class="col-6 d-flex justify-content-end">
              <button
                type="button"
                class="btn btn-primary px-5"
                @click="save"
              >Save</button>
            </div>
            <div class="col-6 d-flex justify-content-start">
              <button
                type="button"
                class="btn btn-light px-5"
                @click="cancel"
              >Cancel</button>
            </div>
          </div>
        </div>
        </form>
      </div>

    <!-- <ConfirmDialog></ConfirmDialog> -->
    </div>
</template>
