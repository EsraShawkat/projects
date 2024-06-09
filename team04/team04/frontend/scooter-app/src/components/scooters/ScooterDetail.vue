<template>
  <div class="text-white my-auto">
    <template v-if="selectedScooter === null || selectedScooter === undefined">
      <p>Select a scooter from the menu on the left.</p>
    </template>
    <template v-else>
      <form>
      <div v-for="(value, key) in selectedScooter">
        <template v-if="key === 'id'">
          <h1>ID: {{value}}</h1>
        </template>
        <template v-else-if="key === 'status'">
          <p>{{key}}:</p>
          <select v-model="selectedScooter[key]" class="text-black border">
            <option value="IDLE" :selected="selectedScooter[key] === 'IDLE'">IDLE</option>
            <option value="INUSE" :selected="selectedScooter[key] === 'INUSE'">INUSE</option>
            <option value="MAINTENANCE" :selected="selectedScooter[key] === 'MAINTENANCE'">MAINTENANCE</option>
          </select>
        </template>
        <template v-else-if="key === 'mileage'">
          <p>Total mileage in km:</p>
          <input v-model="selectedScooter[key]" class="text-black border"/>
        </template>
        <template v-else-if="key === 'batteryCharge'">
          <p>battery (%):</p>
          <input type="number"
                 v-model="selectedScooter[key]"
                 class="text-black border"
          />
        </template>
        <template v-else>
          <p>{{key}}:</p><input v-model="selectedScooter[key]" class="text-black border"/>
        </template>
      </div>
      </form>
      <button class="bg-secondary p-3 rounded-2xl text-white font-bold my-4" @click="onDelete">Delete</button>
    </template>
  </div>
</template>

<script>
export default {
  name: "ScooterDetail",
  props: ['selectedScooter', 'deleteScooter'],
  data() {
    return {
      previousTag: null,
    }
  },
  watch: {
    'selectedScooter.batteryCharge': function (newValue, oldValue) {
      if (newValue > 100) {
        this.selectedScooter.batteryCharge = oldValue;
      } else if (newValue < 0) {
        this.selectedScooter.batteryCharge = oldValue;
      }
    },'selectedScooter.tag': function (newValue) {
      if(this["selectedScooter"] == null)return;
      if (!newValue || newValue.length > 5) {
        this.selectedScooter.tag = this.previousTag;
      } else {
        this.previousTag = newValue;
      }
    }
  },
  // beforeRouteUpdate(from, to, next){
  //   console.log("to:", to)
  //   console.log("from:", from)
  //   const scooterId = to.params.Id;
  //   console.log(scooterId)
  //   next();
  // },
  // beforeRouteLeave(to, from, next) {
  //   console.log("to:", to)
  //   console.log("from:", from)
  //   const scooterId = to.params.Id;
  //   console.log(scooterId)
  //   next();
  // },



  methods: {
    onDelete(){
      if (this.deleteScooter && this.selectedScooter) {
        this.deleteScooter(this.selectedScooter);
      }
    }
  }
}
</script>

<style scoped>

</style>
