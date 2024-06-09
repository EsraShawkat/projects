import { createRouter, createWebHashHistory } from "vue-router";
import welcomeComponent from "../components/WelcomeComponent.vue";
import ScootersOverview32 from "../components/scooters/ScootersOverview32.vue";
import ScooterOverview31 from "../components/scooters/ScooterOverview31.vue";
import ScootersOverview33 from "../components/scooters/ScootersOverview33.vue";
import scooterDetail from "../components/scooters/ScooterDetail.vue";
import ScootersDetail34 from "../components/scooters/ScootersDetail34.vue";
import scooterDetail37 from "../components/scooters/ScooterDetail37.vue";
import scooterOverview37 from "../components/scooters/ScooterOverview37.vue";
import SignIn from "@/components/SignIn.vue";
const routes = [
    { path: '/', component: welcomeComponent },
    { path: '/Available', component: ScootersOverview33, children: [{ path: ':Id', name: 'ScootersOverview33Detail', component: scooterDetail }] },
    { path: '/Detail', component: ScootersOverview32, children: [{ path: ':Id', name: 'ScooterOverview32Detail', component: scooterDetail }] },
    { path: '/Overview', component: ScooterOverview31 },
    { path: '/Overview34', component: ScootersOverview33, children: [{ path: ':Id', name: 'ScooterOverview34Detail', component: ScootersDetail34 }] },
    { path: '/Overview37', component: scooterOverview37, children: [{ path: ':Id', name: 'ScooterOverview37Detail', component: scooterDetail37 }] },
    { path: '/sign-in', component: SignIn },
    { path: '/sign-out', component: SignIn, query: {signOut: true} }

];

export const router = createRouter({
    history: createWebHashHistory(),
    routes
});
