<script setup>
import { inject, ref } from "vue";
import { useRouter } from 'vue-router'
import { BIconSearch, BIconPencil } from 'bootstrap-icons-vue'
import avatarNoneUrl from '@/assets/avatar-none.png'

import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import InputText from 'primevue/inputtext';
import Tag from 'primevue/tag';

import { FilterMatchMode } from 'primevue/api';

const serverBaseUrl = inject("serverBaseUrl");
const router = useRouter()

const props = defineProps({
  vcards: {
    type: Array,  
    default: () => [],
  },
})

const photoFullUrl = (photo_url) => {
  return photo_url
    ? serverBaseUrl + "/storage/fotos/" + photo_url
    : avatarNoneUrl;
}

const editClick = (phone_number) => {
  router.push({ name: 'Vcard', params: { phone_number: phone_number } })
}

const filters = ref(
    {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    }
);

const selectedVcard = ref();

const getSeverity = (status) => {
    switch (status) {
        case 1:
            return 'danger';

        case 0:
            return 'success';
    }
};

</script>

<template>

<DataTable v-model:filters="filters" v-model:selection="selectedVcard" :value="vcards"
    stateStorage="session" stateKey="table-vcards" paginator :rows="10" filterDisplay="menu"
    selectionMode="single" dataKey="phone_number" :globalFilterFields="['phone_number', 'name', 'email']">
    <template #header>
        <span class="p-input-icon-left">
            <BIconSearch />
            <InputText v-model="filters['global'].value" placeholder="Search" />
        </span>
    </template>
    <Column header="Photo" style="width: 5%" >
      <template #body="{ data }">
        <img :src="photoFullUrl(data.photo_url)" class="rounded-circle img_photo" />
      </template>
    </Column>
    <Column field="phone_number" header="Phone" sortable filterMatchMode="contains" style="width: 15%">
      <template #body="{ data }">
        <div class="flex align-items-center gap-2">
          <span>{{ data.phone_number }}</span>
        </div>
      </template>
    </Column>
    <Column field="name" header="Name" sortable filterMatchMode="contains" style="width: 35%">
      <template #body="{ data }">
        <div class="flex align-items-center gap-2">
          <span>{{ data.name }}</span>
        </div>
      </template>
    </Column>
    <Column field="email" header="E-mail" sortable sortField="email" filterField="email" filterMatchMode="contains" style="width: 25%">
        <template #body="{ data }">
            <div class="flex align-items-center gap-2">
                <span>{{ data.email }}</span>
            </div>
        </template>
    </Column>
    <Column field="blocked" header="Status" sortable filterMatchMode="equals" style="width: 40%">
        <template #body="{ data }">
            <Tag :value="data.blocked ? 'blocked' : 'active'" :severity="getSeverity(data.blocked)" />
        </template>
    </Column>
    <Column header="Actions" style="width: 25%">
      <template #body="{ data }">
        <button class="btn btn-xs btn-light" @click="editClick(data.phone_number)" >
          <BIconPencil class="bi bi-xs" />
        </button>
      </template>
    </Column>
    <template #empty> No VCards found. </template>
</DataTable>

</template>

<style scoped>
.img_photo {
  width: 2.6rem;
  height: 2.6rem;
}
</style>