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
            @click="onSelect(scooter)"
        >
          {{scooter.tag}}
        </tr>
        </tbody>
      </table>
            <router-view  :selected-scooter="selectedScooter" :delete-scooter="onDeleteScooter" @refresh-scooters="refreshScooters"/>
<!--      <router-view><ScooterDetail :delete-scooter="onDeleteScooter"/></router-view>-->
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
import {Scooter} from "@/models/scooter";
import ScooterDetail from "./ScooterDetail37.vue";
export default {
  name: "ScooterOverview37",
  inject: ["scooterService"],
  components: {ScooterDetail},
  async created(){
    this.scooters = await this.scooterService.findAll();
    this.$on('refreshScootersOverview', this.refreshScooters);
    this.selectedScooter = this.findSelectedFromRouteParam(this.$route)
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
    async onSelect(scooter) {
      if (this.selectedScooter === scooter) {
        this.selectedScooter = null;
        await this.$router.push(this.$route.matched[0].path);
      } else {
        this.selectedScooter = scooter;
        await this.$router.push(this.$route.matched[0].path +  "/" + scooter.id);
      }
    },
    async refreshScooters(){
      this.scooters = await this.scooterService.findAll();
    },
    onDeleteScooter(scooter) {
      // Find the index of the scooter to delete
      const index = this.scooters.findIndex((s) => s.id === scooter.id);

      // Remove the scooter from the list if found
      if (index !== -1) {
        this.scooters.splice(index, 1);
        this.selectedScooter = null; // Clear selection
        this.$router.push(this.$route.matched[0].path)
      }
    },
    async onNewScooter(){
      const newScooter = await this.scooterService.Save(Scooter.scooterSample(0));

      this.scooters.push(newScooter);
      this.selectedScooter = newScooter;
      this.$router.push({ name: 'ScooterOverview37Detail', params: { Id: newScooter.id } });

    },
    // onRowClick(scooter){
    //   if(this.selectedScooter === scooter){
    //     this.selectedScooter = null;
    //     return;
    //   }
    //   this.selectedScooter = scooter;
    // },
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
    findSelectedFromRouteParam(route) {
      const scooterIdFromRoute = parseInt(route.params.Id);

      if(scooterIdFromRoute) {
        const scooter =  this.scooters.find((scooter) => scooter.id === scooterIdFromRoute)
        this.selectedScooter =  scooter;
      }else{
        this.selectedScooter = null;
      }
    }
  },

  watch: {
    'selectedScooter.batteryCharge': function (newValue, oldValue) {
      if (newValue > 100) {
        this.selectedScooter.batteryCharge = oldValue;
      } else if (newValue < 0) {
        this.selectedScooter.batteryCharge = oldValue;
      }
    },

    '$route'() {
      this.findSelectedFromRouteParam(this.$route);

    }
  },
  mounted() {
    this.findSelectedFromRouteParam(this.$route);
  }

}
</script>

<style scoped>
.selected{
  background-color: #382f2f;
}
</style>
