<script setup>
import { ref, inject } from 'vue'
const serverBaseUrl = inject("serverBaseUrl");
import avatarNoneUrl from '@/assets/avatar-none.png'
import ConfirmDialog from 'primevue/confirmdialog';
import Button from 'primevue/button';
import { useConfirm } from "primevue/useconfirm";

const confirm = useConfirm();

const props = defineProps({
  vcard: {
    type: Object,  
    required: true,
  },
  errors: {
    type: Object,
    required: false,
  },
})

const emit = defineEmits(['update', 'remove', 'cancel'])

const editingVcard = ref(props.vcard)

const save = () => {
  emit('update', editingVcard.value)
}

const remove = () => {
  confirm.require({
        message: 'Do you want to delete this VCard?',
        header: 'Delete Confirmation',
        icon: 'pi pi-info-circle',
        rejectClass: 'p-button-text p-button-text',
        acceptClass: 'p-button-danger p-button-text',
        accept: () => {
          emit('remove', editingVcard.value)
        },
        reject: () => {
          return;
        }
  });
}

const cancel = () => {
  emit('cancel', editingVcard.value)
}

const photoFullUrl = (vcard) => {
  return vcard.photo_url
    ? serverBaseUrl + "/storage/fotos/" + vcard.photo_url
    : avatarNoneUrl;
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
				    <h3>VCard Details</h3>
				    <hr>
			    </div>
          <div class="row">
            <div class="mb-2">
              <div class="text-center">
                <img :src="photoFullUrl(props.vcard)" class="rounded-circle img_photo" />
              </div>
              <div class="mt-3 p-3 card bg-light">
                <div class="mb-3">
							    <label class="form-label">Phone Number</label>
  							  <input :value="props.vcard.phone_number" type="text" class="form-control" aria-label="Phone number" disabled >
                </div>
                <div class="mb-3">
                  <label class="form-label">Name</label>
  							  <input :value="props.vcard.name" type="text" class="form-control" aria-label="Name" disabled >
                </div>
                <div class="mb-3">
                  <label class="form-label">E-mail</label>
  							  <input :value="props.vcard.email" type="text" class="form-control" aria-label="Email" disabled >
                </div>
                <div class="row mb-3">
                  <div class="col-md-6">
                    <label class="form-label">Balance</label>
                    <div class="input-group">
                      <input :value="props.vcard.balance" type="number" class="form-control" aria-label="Balance" disabled >
                      <span class="input-group-text">€</span>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label class="form-label">Maximum Debit</label>
                    <div class="input-group">
                      <input v-model="editingVcard.max_debit" type="text" class="form-control" aria-label="Max. debit" >
                      <span class="input-group-text">€</span>
                    </div>
                  </div>
                </div>
                <div>
                  <label class="form-label">Status</label>
                    <select v-model="editingVcard.blocked" class="form-select" aria-label="VCard status">
                      <option :value="null"></option>
                      <option value="0">Not blocked</option>
                      <option value="1">Blocked</option>
                    </select>
                </div>
                <div class="text-center mt-4">
                  <Button label="Delete vCard" severity="danger" text @click="remove" />
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

<style scoped>
.img_photo {
  width: 8.5rem;
}
</style>