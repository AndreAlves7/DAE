<script setup>
import { ref } from 'vue'
import { useCategoryStore } from "../../stores/categories.js"
import { useUserStore } from '../../stores/user.js'
import { useToast } from "vue-toastification"
import { useRouter } from 'vue-router';

const router = useRouter()
const toast = useToast()
const userStore = useUserStore()
const categoriesStore = useCategoryStore()

const newCategory = ref({
    vcard: userStore.user.username,
    name: '',
    type: '',
})

const save = async () => {
    try {
        await categoriesStore.createCategory(newCategory.value)
        toast.success(`Category created successfully!`)
        router.back()
    } catch (error) {
        toast.error(`Category was not created due to unknown server error!`);
        console.log(error)
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
			    <h3>New Category</h3>
			    <hr>
		    </div>
          <div class="row">
            <div class="mb-2">
              <div class="mt-3 p-3 card bg-light">
                <div class="mb-3">
					<label class="form-label">Category name</label>
  					<input v-model="newCategory.name" type="text" class="form-control" aria-label="Category name" >
                </div>
                <div>
                  <label class="form-label">Category type</label>
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