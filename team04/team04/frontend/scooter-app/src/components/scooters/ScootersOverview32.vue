<template>
  <div class="flex flex-col items-center">
    <div class="flex w-full justify-around">
  <table class="w-[30%] h-full text-white">
    <thead>
    <tr class="border-b-2 border-black">
      <th class="p-2">Tag</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(scooter, index) in paginatedScooters"
        :key="index"
        class="border-b border-black text-center leading-10"
        :class="{'selected': scooter === selectedScooter}"
        @click="onRowClick(scooter)"
    >
        {{scooter.tag}}
    </tr>
    </tbody>
  </table>
      <ScooterDetail :selected-scooter="selectedScooter" :delete-scooter="onDeleteScooter"/>
    </div>
    <div class="flex justify-center mt-4">
      <button @click="prevPage" :disabled="currentPage === 1"
              :class="{'disabled': currentPage === 1}"
              class="bg-secondary text-white font-bold p-2 rounded-2xl">
        Previous
      </button>
      <div class="mx-4">
        Page {{ currentPage }} of {{ totalPages }}
      </div>
      <button @click="nextPage" :disabled="currentPage === totalPages"
              :class="{'disabled': currentPage === totalPages}"
              class="bg-secondary text-white font-bold p-2 rounded-2xl">
        Next</button>
    </div>
  <button @click="onNewScooter" class="bg-secondary p-3 rounded-2xl text-white font-bold my-4">Create new Scooter</button>
  </div>
</template>


<script>
import {Scooter} from "../../models/scooter";
import ScooterDetail from "./ScooterDetail.vue";
export default {
  name: "ScootersOverview32",
  components: {ScooterDetail},
  created(){
      this.id = 3000;

    for (let i = 0; i < 8; i++) {
      this.scooters.push(
          Scooter.scooterSample(this.nextId())
      )
    }
  },
  data() {
    return {
      scooters: [],
      currentPage: 1,
      rowsPerPage: 10,
      selectedScooter: null,
    }
  },
  computed: {
    totalPages(){
      return Math.ceil(this.scooters.length / this.rowsPerPage)
    },
    paginatedScooters() {
      const startIndex = (this.currentPage - 1) * this.rowsPerPage;
      const endIndex = startIndex + this.rowsPerPage;
      return this.scooters.slice(startIndex, endIndex);
    },
  },
  methods: {
    nextId(){
      return this.id += (Math.floor(Math.random() * 3) + 1);
    },
    onDeleteScooter(scooter) {
      // Find the index of the scooter to delete
      const index = this.scooters.findIndex((s) => s.id === scooter.id);

      // Remove the scooter from the list if found
      if (index !== -1) {
        this.scooters.splice(index, 1);
        this.selectedScooter = null; // Clear selection
      }
    },
    onNewScooter(){
      const newId = this.nextId();
      const newScooter = Scooter.scooterSample(newId);
      this.scooters.push(newScooter);
      this.selectedScooter = newScooter;
    },
    onRowClick(scooter){
        if(this.selectedScooter === scooter){
          this.selectedScooter = null;
          return;
        }
        this.selectedScooter = scooter;
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
  }
}
</script>

<style scoped>
 .selected{
   background-color: #382f2f;
 }
</style>
