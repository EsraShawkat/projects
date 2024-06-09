<template>
  <nav class="bg-blue-500 p-3">
    <div class="container mx-auto flex justify-between items-center">
      <ul class="flex space-x-4">
        <li>
          <router-link to="/" class="text-white p-1 hover:bg-blue-50 hover:text-black rounded-sm block p-2">Home</router-link>
        </li>
        <li @mouseover="showDropdown = true" @mouseleave="showDropdown = false">
          <a class="text-white p-1 hover:bg-blue-50 hover:text-black rounded-sm p-2 block">Scooters</a>
          <div v-if="showDropdown" class="absolute bg-white shadow-lg rounded-lg ">
            <router-link to="/Overview" class="block px-4 py-2 hover:bg-sky-100 rounded-lg">Scooters overview</router-link>
            <router-link to="/Detail" class="block px-4 py-2 hover:bg-sky-100 ">Scooters details</router-link>
            <router-link to="/Overview37" class="block px-4 py-2 hover:bg-sky-100 ">Available scooters</router-link>
            <router-link to="/Overview34" class="block px-4 py-2 hover:bg-sky-100 rounded-lg">Abandoned scooters</router-link>
          </div>
        </li>
        <li>
          <router-link to="/" class="text-white p-1 hover:bg-blue-50 hover:text-black  p-2 block rounded-sm">My Trips</router-link>
        </li>
        <li>
          <router-link to="/" class="text-white p-1 hover:bg-blue-50 hover:text-black p-2 block rounded-sm">My Account</router-link>
        </li>
      </ul>

      <ul class="flex space-x-4">
        <li>
          <router-link to="/" class="text-white p-1 hover:bg-blue-50 hover:text-black block rounded-sm">Sign Up</router-link>
        </li>
        <li>
          <router-link to="/" class="text-white p-1 hover:bg-blue-50 hover:text-black block rounded-sm">Login</router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
export default {
  name: "NavBar",
  data() {
    return {
      showDropdown: false,
    };
  },
};
</script>
