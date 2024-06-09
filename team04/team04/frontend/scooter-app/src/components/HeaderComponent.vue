<template>
  <div class="w-[100vw] h-[150px] relative">
    <img :src="backgroundImage" class="w-full h-full opacity-50 object-cover object-bottom" alt="background-img">
    <img :src="leftImage" class="absolute top-[50%] translate-y-[-50%] h-[100px] w-[100px] rounded-full ml-[10px]" alt="amsterdam-logo">
    <img :src="rightImage" class="absolute top-[50%] translate-y-[-50%] right-0 h-[100px] w-[100px] rounded-full mr-[10px]" alt="amsterdam-logo">
    <h1 class="text-black text-[30px] z-10 absolute top-0 left-[50%] translate-x-[-50%]">E-scooters Amsterdam</h1>
    <div class="flex flex-col items-center">
      <p class="absolute bottom-10 left-[50%] translate-x-[-50%] bg-sky-100 p-1 rounded-sm">{{`Today's date: ${formattedDate}`}}</p>
      <p class="z-10 absolute bottom-5 left-[50%] translate-x-[-50%] ">Start now and get acces to scooters everywhere!</p>
    </div>


  </div>
</template>
<script>
  import BackgroundImage from "../assets/images/electric-scooter-background-nav.jpg";
  import LeftImage from "../assets/images/amsterdam-logo.png";
  import RightImage from "../assets/images/logo.png"
export default {
  name: 'HeaderComponent',
  data() {
    return {
      backgroundImage: BackgroundImage,
      leftImage : LeftImage,
      rightImage : RightImage,
      formattedDate: "",
    }
  },

  mounted() {
    // Calculate and set the formatted date when the component is mounted
    this.calculateFormattedDate();
  },

  methods: {
      calculateFormattedDate() {
        const now = new Date();
        const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
        this.formattedDate = now.toLocaleDateString('en-US', options);
      },
  }
}

</script>
<style scoped>

</style>