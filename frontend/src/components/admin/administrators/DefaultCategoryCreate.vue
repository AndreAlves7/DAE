<script setup>
import { ref } from 'vue'
import { useToast } from "vue-toastification"
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter()
const toast = useToast()

const newCategory = ref({
    name: '',
    type: '',
})

const save = async () => {
    try {
        await axios.post('/defaultcategory' , newCategory.value)
        toast.success(`Default Category created successfully!`)
        router.back()
      } catch (error) {
        if (error.response && error.response.data && error.response.data.errors) {
            const errorMessages = Object.values(error.response.data.errors).flat();
            toast.error(`Failed to create Default Category: ${errorMessages.join(', ')}`);
        } else {
            toast.error('Failed to create Default Category due to an unknown server error!');
        }
        console.error(error);
    }
}

const cancel = () => {
    router.back()
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
			    <h3>New Default Category</h3>
			    <hr>
		    </div>
          <div class="row">
            <div class="mb-2">
              <div class="mt-3 p-3 card bg-light">
                <div class="mb-3">
					<label class="form-label">Default Category name</label>
  					<input v-model="newCategory.name" type="text" class="form-control" aria-label="Category name" >
                </div>
                <div>
                  <label class="form-label">Default Category type</label>
                    <select v-model="newCategory.type" class="form-select" aria-label="Category type">
                      <option value="C">Credit</option>
                      <option value="D">Debit</option>
                    </select>
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

    </div>
</template>