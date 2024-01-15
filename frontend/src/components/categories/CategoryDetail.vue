<script setup>
import { ref } from 'vue'
import ConfirmDialog from 'primevue/confirmdialog';
import Button from 'primevue/button';
import { useConfirm } from "primevue/useconfirm";

const confirm = useConfirm();

const props = defineProps({
  category: {
    type: Object,  
    required: true,
  },
  errors: {
    type: Object,
    required: false,
  },
})

const emit = defineEmits(['update', 'remove', 'cancel'])

const editingCategory = ref(props.category)

const save = () => {
  emit('update', editingCategory.value)
}

const remove = () => {
  confirm.require({
        message: 'Do you want to delete this category?',
        header: 'Delete Confirmation',
        icon: 'pi pi-info-circle',
        rejectClass: 'p-button-text p-button-text',
        acceptClass: 'p-button-danger p-button-text',
        accept: () => {
          emit('remove', editingCategory.value)
        },
        reject: () => {
          return;
        }
  });
}

const cancel = () => {
  emit('cancel', editingCategory.value)
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
			    <h3>Category Details</h3>
			    <hr>
		    </div>
          <div class="row">
            <div class="mb-2">
              <div class="mt-3 p-3 card bg-light">
                <div class="mb-3">
					<label class="form-label">Category name</label>
  					<input v-model="editingCategory.name" type="text" class="form-control" aria-label="Category name" >
                </div>
                <div>
                  <label class="form-label">Category type</label>
                    <select v-model="editingCategory.type" class="form-select" aria-label="Category type">
                      <option value="C">Credit</option>
                      <option value="D">Debit</option>
                    </select>
                </div>
                <div class="text-center mt-4">
                  <Button label="Delete Category" severity="danger" text @click="remove" />
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

    <ConfirmDialog></ConfirmDialog>
    </div>
</template>