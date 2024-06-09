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
  name: "ScootersDetail34",
  props: ['selectedScooter', 'deleteScooter'],
  data() {
    return {
      previousTag: null,
      selectedScooterCopy: null,
      errorShow: false,
      errorMessage: "",
      dirty: false,
    };
  },


  beforeRouteUpdate(to, from, next) {
    if (this.dirty) {
      const confirmationMessage = "Je hebt onopgeslagen wijzigingen. Weet je zeker dat je wilt navigeren?";
      if (confirm(confirmationMessage)) {
        console.log("veranderd")
        next();
      } else {
        console.log("niet veranderd")
        next(false);
      }
    } else {
      console.log("veranderd zonder")

      next();
    }
  },

  beforeRouteLeave(to, from, next) {
    if (this.dirty) {
      const confirmationMessage = "Je hebt onopgeslagen wijzigingen. Weet je zeker dat je wilt vertrekken?";
      if (confirm(confirmationMessage)) {
        console.log("veranderd")
        next();
      } else {
        console.log("niet veranderd")
        next(false);
      }
    } else {
      console.log("veranderd zonder")
      next();
    }
  },




  watch: {
    'selectedScooter': function (newSelectedScooter) {
      this.selectedScooterCopy = Scooter.copyConstructor(newSelectedScooter);
    },
    'selectedScooterCopy': function (newValue, oldValue) {
      if (newValue !== oldValue) {
        this.dirty = false;
      }
    },
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
    }
  },
  created() {
    this.selectedScooterCopy = Scooter.copyConstructor(this.selectedScooter);
  },

  methods: {
    onDelete() {
      if (confirm("Weet je zeker dat je de scooter wilt verwijderen?")) {
        if (this.deleteScooter && this.selectedScooter) {
          this.deleteScooter(this.selectedScooter);
        }
      }
    },

    onSave() {
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

      Object.assign(this.selectedScooter, this.selectedScooterCopy)
      this.selectedScooterCopy = null;
      this.$router.push(this.$route.matched[0].path);
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
