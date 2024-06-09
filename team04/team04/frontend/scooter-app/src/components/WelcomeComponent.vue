<template>
  <div class="flex h-full">
  <div class="w-[250px] border-r-2 flex-col p-10 pt-[150px] text-white leading-10">
    <h2 class="text-[20px]">Select a brand</h2>
    <div>
      <select class="text-black">
        <option v-for="(brand, index) in brands" :value="brand" :key="index">{{brand}}</option>
      </select>
    </div>
    <div>
      <h2 class="text-[20px]">Available scooters:</h2>
      <div v-for="location in locations">
        <p>{{location.name}}: {{location.availableBikes}}</p>
      </div>
    </div>
  </div>
  <div class="flex-1 p-5 flex flex-wrap">
    <div v-for="scooterIMG in fillerIMG" class="w-[200px] h-[200px] m-3">
      <img :src="scooterIMG" alt="filler">
    </div>
  </div>
  <div class="w-[200px] border-l-2 p-5">
    <p class="text-white">
      Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium aperiam architecto at atque cum
      distinctio dolorem doloremque ea, earum eius esse et eum exercitationem expedita explicabo facere fuga illum ipsum
      iure minima molestiae nam obcaecati odit quasi quia quod recusandae rerum sit tempore totam. Beatae distinctio
      eligendi facilis modi quo?
    </p>
  </div>
  </div>
</template>
<script>
import ScooterFillerIMG from "../assets/images/scooter-filler-img.webp"
export default {
  name: 'WelcomeComponent',
  data() {
    return {
      brands: ["brand 1" , "brand 2" , "brand 3", "brand 4", "brand 5"],
      fillerIMG: [ScooterFillerIMG, ScooterFillerIMG, ScooterFillerIMG, ScooterFillerIMG,
        ScooterFillerIMG, ScooterFillerIMG, ScooterFillerIMG, ScooterFillerIMG, ScooterFillerIMG, ScooterFillerIMG],
      locations: [
          {
            name: "Centraal station",
            availableBikes: 16
          },
        {
          name: "Leidseplein",
          availableBikes: 7
        },
        {
          name: "Rembrandtplein",
          availableBikes: 13
        }
      ]
    }
  }
}
</script>
<style scoped>

</style>
