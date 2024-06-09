<template>
  <header-component />
  <nav-bar />
  <router-view />
</template>

<script>
import HeaderComponent from "./HeaderComponent.vue";
import NavBar from "./NavBar.vue";
export default {
  name: "AppComponent33",
  components: {NavBar, HeaderComponent}
}
</script>

<style scoped>

</style>
