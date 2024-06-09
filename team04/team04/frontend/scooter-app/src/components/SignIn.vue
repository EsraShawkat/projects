<template>
<div class="flex items-center justify-center flex-col h-[70vh]">
  <div class="relative flex flex-col bg-blue-500 p-20 rounded-2xl">
    <label for="email">Email:</label>
    <input type="email" class="w-[300px]" name="email" v-model="nameInput"/>
    <label for="wachtwoord">Wachtwoord:</label>
    <input :type="showPassword ? 'text' : 'password'" class="w-[300px]" name="wachtwoord" v-model="passwordInput"/>
    <img :src="[showPassword ? showEye : hiddenEye]"
         @click="handleClickEye"
         alt="eye"
         class="w-[25px] absolute bottom-[166px] right-[90px] cursor-pointer"
    />

    <button class="bg-none border-4 py-2 rounded-2xl border-white mt-10
    text-white font-bold hover:border-black hover:text-black transition-all" @click="handleButton()">
      Login
    </button>
  </div>
  <p v-if="errormessage" class="text-red-600">{{errormessage}}</p>
</div>
</template>

<script>
import hide from '../assets/images/hide.png'
import show from '../assets/images/view.png'
import emitter from '@/components/EventBus.js'
export default {
  name: "SignIn",
  inject: ["sessionService"],
  data(){
    return{
      showEye: show,
      hiddenEye: hide,
      showPassword: false,
      nameInput: "",
      passwordInput: "",
      errormessage: null,
    }
  },
  created() {
    if(this.$route.query.signOut){
      this.sessionService.signOut();
    }
  },
  methods: {
    //...mapMutations(['setLoggedIn']),
    handleClickEye() {
      this.showPassword ? this.showPassword = false : this.showPassword = true;
    },
    async handleButton(){
      const user = await this.sessionService.asyncSignIn(this.nameInput, this.passwordInput);
      if (user.status === 500) {
        this.setErrorMessage()
        return;
      }
      console.log(user)
      emitter.emit('loggedIn');
      this.$router.push('/');
    },
    setErrorMessage(){
      this.errormessage = "Couldn't Authenticate with these credentials"
    }
  }
}
</script>

<style scoped>

</style>
