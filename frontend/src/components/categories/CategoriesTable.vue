<script setup>
import { ref } from "vue";
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';
import ConfirmDialog from 'primevue/confirmdialog';
import { FilterMatchMode } from 'primevue/api';
import { BIconSearch, BIconPencil, BIconTrash} from 'bootstrap-icons-vue'
import { useConfirm } from "primevue/useconfirm";

const confirm = useConfirm();

const props = defineProps({
  categories: {
    type: Array,  
    default: () => [],
  },
  loadingTable: {
    type: Boolean,
    default: true
  }
})

const emit = defineEmits(['edit', 'remove'])

const selectedCategory = ref();

const filters = ref(
  {
    global: { value: null, matchMode: FilterMatchMode.CONTAINS }
  }
);

const editClick = (categoryId) => {
  emit('edit', categoryId)
}

const deleteClick = (category) => {
  confirm.require({
        message: 'Do you want to delete this category?',
        header: 'Delete Confirmation',
        icon: 'pi pi-info-circle',
        rejectClass: 'p-button-text p-button-text',
        acceptClass: 'p-button-danger p-button-text',
        accept: () => {
          emit('remove', category)
        },
        reject: () => {
          return;
        }
  });
}

const getCategoryType = (type) => {
    switch (type) {
        case 'C':
            return 'Credit';

        case 'D':
            return 'Debit';
    }
};

</script>

<template>
<DataTable v-model:filters="filters" v-model:selection="selectedCategory" :value="categories" :loading="loadingTable" 
    stateStorage="session" stateKey="table-categories" paginator :rows="10" filterDisplay="menu"
    selectionMode="single" dataKey="id" :globalFilterFields="['name', 'type']">
    <template #header>
        <span class="p-input-icon-left">
            <BIconSearch />
            <InputText v-model="filters['global'].value" placeholder="Search" />
        </span>
    </template>
    <Column field="name" header="Name" sortable filterMatchMode="contains" style="width: 40%">
      <template #body="{ data }">
        <div class="flex align-items-center gap-2">
          <span>{{ data.name }}</span>
        </div>
      </template>
    </Column>
    <Column field="type" header="Type" sortable filterMatchMode="contains" style="width: 40%">
      <template #body="{ data }">
        <div class="flex align-items-center gap-2">
          <span>{{ getCategoryType(data.type) }}</span>
        </div>
      </template>
    </Column>
    <Column header="Actions" style="width: 20%">
      <template #body="{ data }">
        <div class="p-2">
          <button class="btn btn-sm btn-light" @click="editClick(data.id)" >
            <BIconPencil class="bi bi-xs" />
          </button>
          <button class="btn btn-sm btn-danger" @click="deleteClick(data)" >
            <BIconTrash class="bi bi-xs" />
          </button>
        </div>
      </template>
    </Column>
    <template #empty> No categories found. </template>
</DataTable>

<ConfirmDialog></ConfirmDialog>
</template>