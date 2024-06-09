<template>
  <div class="flex flex-col items-center text-white">
    <table class="w-[90%] h-full">
      <thead>
      <tr class="border-b-2 border-black ">
        <th v-for="(value, key) in scooters[0]" :key="key" class="p-2">{{ key }}</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="scooter in paginatedScooters" class="border-b border-black">
        <td v-for="(value, key) in scooter" class="p-2 text-center">
          <template v-if="key === 'gpsLocation'">
            {{ scooter.status === 'INUSE' ? 'unavailable' : value }}
          </template>
          <template v-else>
            {{ value }}
          </template>
        </td>
      </tr>
      </tbody>
    </table>
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
  name: "ScootersOverview31",
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

</style>
