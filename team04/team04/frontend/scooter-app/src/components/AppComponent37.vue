<template>
  <header-component />
  <nav-bar />
  <router-view />
</template>

<script>
import HeaderComponent from "./HeaderComponent.vue";
import NavBar from "./NavBar.vue";
import {ScootersAdaptor} from "../services/ScootersAdaptor";
export default {
  name: "AppComponent37",
  components: {NavBar, HeaderComponent},
  provide(){
    return{
      service: new ScootersAdaptor("http://localhost:8085/scooters")
    }
  }
}
</script>

<style scoped>

</style>
