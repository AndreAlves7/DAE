<script setup>
import { ref, onMounted } from 'vue'
import VcardTable from './VCardTable.vue'
import { useVcardsStore } from "../../stores/vcard.js"

const vcardsStore = useVcardsStore()

const vcards = ref([])

const loadVcards = async () => {
    try {
        vcards.value = await vcardsStore.loadVcards()
    } catch (error) {
        console.log(error)
    }
}

onMounted (() => {
    loadVcards()
})

</script>

<template>
    <div class="container">
        <div class="row">
        <div class="col-12 mt-3">
            <div class="mb-3">
                <h2>VCards</h2>
                <hr>
            </div>
            <vcard-table :vcards="vcards"></vcard-table>
        </div>
        </div>
    </div>
</template>