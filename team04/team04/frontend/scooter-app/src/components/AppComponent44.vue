<template>
  <header-s-b />
  <NavBarSB />
  <router-view />
</template>

<script>
import HeaderSB from "@/components/HeaderSB.vue";
import {ScootersAdaptor} from "@/services/ScootersAdaptor";
import {SessionSbService} from "@/services/SessionSbService";
import {shallowReactive} from "vue";
import NavBarSB from "@/components/NavbarSB.vue";
import {FetchInterceptor} from "@/services/FetchInterceptor";
export default {
  name: "AppComponent44",
  components: {NavBarSB, HeaderSB},
  provide(){
    this.theSessionService = shallowReactive( new SessionSbService("http://localhost:8085/authentication"))
    this.theFetchInterceptor = new FetchInterceptor(this.theSessionService, this.$router)

    return{
      scooterService: new ScootersAdaptor("http://localhost:8085/scooters"),
      sessionService: new SessionSbService("http://localhost:8085/authentication"),
    }
  },
  unmounted() {
    console.log("unmounted has been called")
    this.theFetchInterceptor.unregister();
  }
}
</script>

<style scoped>

</style>
