<template>
  <div class="text-white my-auto">
    <template v-if="selectedScooterCopy === null || selectedScooterCopy === undefined">
      <p>Select a scooter from the menu on the left</p>
    </template>
    <template v-else>
      <form class="flex items-center flex-col">
        <div v-for="(value, key) in selectedScooterCopy">
          <template v-if="key === 'id'">
            <h1>ID: {{ value }}</h1>
          </template>
          <template v-else-if="key === 'status'">
            <p>{{ key }}:</p>
            <select v-model="selectedScooterCopy[key]" class="text-black border">
              <option value="IDLE">IDLE</option>
              <option value="INUSE">INUSE</option>
              <option value="MAINTENANCE">MAINTENANCE</option>
            </select>
          </template>
          <template v-else-if="key === 'mileage'">
            <p>Total mileage in km:</p>
            <input v-model="selectedScooterCopy[key]" class="text-black border"/>
          </template>
          <template v-else-if="key === 'batteryCharge'">
            <p>Battery (%):</p>
            <input type="number" v-model="selectedScooterCopy[key]" class="text-black border"/>
          </template>
          <template v-else>
            <p>{{ key }}:</p><input v-model="selectedScooterCopy[key]" class="text-black border"/>
          </template>
        </div>
      </form>
      <div>
        <button class="bg-secondary p-3 rounded-2xl ml-5 text-white font-bold my-4" :class="{'disabled': !dirty}"
                :disabled="!dirty" @click="onSave">Save
        </button>
        <button class="bg-secondary p-3 rounded-2xl ml-5 text-white font-bold my-4" :class="{'disabled': dirty}"
                :disabled="dirty" @click="onDelete">Delete
        </button>
        <button class="bg-secondary p-3 rounded-2xl ml-5 text-white font-bold my-4" :class="{'disabled': !dirty}"
                :disabled="!dirty" @click="onReset">Reset
        </button>
        <button class="bg-secondary p-3 rounded-2xl ml-5 text-white font-bold my-4" @click="onClear">Clear</button>
        <button class="bg-secondary p-3 rounded-2xl ml-5 text-white font-bold my-4" @click="onCancel">Cancel</button>
      </div>
      <div v-show="errorShow">
        <h1 class="text-red-500">{{ errorMessage }}</h1>
      </div>
    </template>
  </div>
</template>

<script>
import {Scooter} from "../../models/scooter.js";

export default {
  name: "ScootersDetail37",
  props: [ 'deleteScooter'],
  inject: ["scooterService"],
  data() {
    return {
      previousTag: null,
      selectedScooterCopy: null,
      errorShow: false,
      errorMessage: "",
      dirty: false,
    };
  },


  async beforeRouteUpdate(to, from, next) {
    if(this.$route.params.Id != null){
      const scooter = await this.scooterService.findById(to.params.Id)
      this.selectedScooterCopy = Scooter.copyConstructor(scooter);
      next()
      return;
    }
    this.selectedScooterCopy = null;
    next()
  },
  watch: {

    'selectedScooterCopy.tag': function (newValue, oldValue) {
      this.dirty = newValue !== oldValue;
    },
    'selectedScooterCopy.batteryCharge': function (newValue, oldValue) {
      this.dirty = newValue !== oldValue;
    },
    'selectedScooterCopy.status': function (newValue, oldValue) {
      this.dirty = newValue !== oldValue;
    },
    'selectedScooterCopy.gpsLocation': function (newValue, oldValue) {
      this.dirty = newValue !== oldValue;
    },
    'selectedScooterCopy.mileage': function (newValue, oldValue) {
      this.dirty = newValue !== oldValue;
    },
    'selectedScooterCopy': async function (newValue, oldValue) {
      await this.$nextTick();
      if (newValue !== oldValue) {
        this.dirty = false;
      }
    },
  },
  async created() {
    if(this.$route.params.Id != null){
      const scooter = await this.scooterService.findById(this.$route.params.Id)
      this.selectedScooterCopy = Scooter.copyConstructor(scooter);
      return;
    }
    this.selectedScooterCopy = null

  },

  methods: {
    async onDelete() {
      if (confirm("Weet je zeker dat je de scooter wilt verwijderen?")) {
        if (this.selectedScooterCopy) {
          await this.scooterService.deleteById(this.selectedScooterCopy.id)
          this.selectedScooterCopy = null;
          this.$emit('refresh-scooters');
          this.$router.push(this.$route.matched[0].path);
        }
      }
    },

   async  onSave() {
      //validation
      for (const key in this.selectedScooterCopy) {
        if (key === 'id') continue
        if (!this.selectedScooterCopy[key]?.toString()?.trim()) {
          this.setErrorMessage("All fields must have a valid value and be selected.")
          return;
        }
      }
      if (this.selectedScooterCopy.tag.length > 5) {
        this.setErrorMessage("The tag cant be longer than 5 digits.")
        return;
      }
      if (!/^\d{2}(\.\d{2}){4}$/.test(this.selectedScooterCopy.gpsLocation)) {
        this.setErrorMessage('The gps location has to follow this format 00.00.00.00.00')
        return;
      }
      if (this.selectedScooterCopy.mileage.length > 5) {
        this.setErrorMessage("The mileage has to be less then a hundred thousand KM.")
        return;
      }
      if (this.selectedScooterCopy.batteryCharge < 0 || this.selectedScooterCopy.batteryCharge > 100) {
        this.setErrorMessage("The battery charge has to be a value between 0 and a a hundred")
        return;
      }
      if (this.errorShow) this.errorShow = false;

      await this.scooterService.Save(this.selectedScooterCopy)
      this.selectedScooterCopy = null;
      this.$router.push(this.$route.matched[0].path);
      this.$emit('refresh-scooters');
      this.dirty = false;
    },
    setErrorMessage(message) {
      this.errorMessage = message;
      this.errorShow = true;
    },
    async onReset() {
      if (this.dirty) {
        if (confirm("You have unsaved changes. Are you sure you want to reset?")) {
          const originalScooter = this.selectedScooter; // Store the original scooter
          for (const key in originalScooter) {
            this.selectedScooterCopy[key] = originalScooter[key];
          }
          await this.$nextTick();
          this.dirty = false;
        }
      }
    },
    onClear() {
      if (this.dirty) {
        if (confirm("You have unsaved changes. Are you sure you want to clear?")) {
          for (const key in this.selectedScooterCopy) {
            if (key !== "id") this.selectedScooterCopy[key] = null;
          }
        }
      } else {
        for (const key in this.selectedScooterCopy) {
          if (key !== "id") this.selectedScooterCopy[key] = null;
        }
      }
    },
    onCancel() {
      if (this.dirty) {
        if (confirm("You have unsaved changes. Are you sure you want to cancel?")) {
          this.selectedScooterCopy = null;
          this.$router.push(this.$route.matched[0].path);
        }
      } else {
        this.selectedScooterCopy = null;
        this.$router.push(this.$route.matched[0].path);
      }
    },




  },

}
</script>

<style scoped>

</style>
