<template>
  <div class="w-[100vw] h-[150px] relative">
    <img :src="backgroundImage" class="w-full h-full opacity-50 object-cover object-bottom" alt="background-img">
    <img :src="leftImage" class="absolute top-[50%] translate-y-[-50%] h-[100px] w-[100px] rounded-full ml-[10px]" alt="amsterdam-logo">
    <img :src="rightImage" class="absolute top-[50%] translate-y-[-50%] right-0 h-[100px] w-[100px] rounded-full mr-[10px]" alt="amsterdam-logo">
    <h1 class="text-black text-[30px] z-10 absolute top-0 left-[50%] translate-x-[-50%] text-white">E-scooters Amsterdam</h1>
    <div class="flex flex-col items-center">
      <p class="absolute bottom-10 left-[50%] translate-x-[-50%] bg-sky-100 p-1 rounded-sm">{{`Today's date: ${formattedDate}`}}</p>
      <p class="z-10 absolute bottom-5 left-[50%] translate-x-[-50%] text-white">Start now and get acces to scooters everywhere!</p>
      <p v-if="userName" class="text-white absolute bottom-0">welkom {{userName}}</p>
      <p v-else class="text-white absolute bottom-0">welkom visitor</p>
    </div>


  </div>
</template>
<script>
import BackgroundImage from "../assets/images/electric-scooter-background-nav.jpg";
import LeftImage from "../assets/images/amsterdam-logo.png";
import RightImage from "../assets/images/logo.png"
import eventBus from "@/components/EventBus.js";

export default {
  name: 'HeaderSB',
  inject: ["sessionService"],
  data() {
    return {
      backgroundImage: BackgroundImage,
      leftImage : LeftImage,
      rightImage : RightImage,
      formattedDate: "",
      userName: null,
    }
  },

  mounted() {
    // Calculate and set the formatted date when the component is mounted
    this.calculateFormattedDate();
  },
  created() {
    this.setUserName();
    eventBus.on('loggedIn', this.setUserName)
    eventBus.on('loggedOut', this.setUserName)
  },

  methods: {
    calculateFormattedDate() {
      const now = new Date();
      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
      this.formattedDate = now.toLocaleDateString('en-US', options);
    },
    setUserName(){
      const user = window.sessionStorage.getItem("user");
      if(this.userName){
        this.userName = null;
        return;
      }
      if(user){
        this.userName = JSON.parse(user).name;
      }
    }
  }
}

</script>
<style scoped>

</style>
