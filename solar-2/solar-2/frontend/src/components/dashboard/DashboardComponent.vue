<template>
    <div>
        <SearchDashboardComponent
            :dashboardTitles="dashboardTitles"
            @dashboard-selected="handleDashboardSelected"
            @all-warehouses-selected="handleAllWarehousesSelected"
        />

        <!-- Use v-if to conditionally render DashboardContainer or AllWarehousesContainer -->
        <DashboardContainer v-if="!showAllWarehouses" :dashboard="selectedDashboard" />
        <AllWarehousesContainer :warehouses="warehouses" :forecasting="forecasting"  v-if="showAllWarehouses" />

    </div>
</template>

<script>
import DashboardContainer from "@/components/dashboard/DashboardContainer.vue";
import SearchDashboardComponent from "@/components/dashboard/SearchDashboardComponent.vue";
import AllWarehousesContainer from "@/components/dashboard/AllWarehousesContainer.vue";

export default {
    inject: ["warehousesService", "forecastService"],
    name: "DashboardComponent",
    components: { SearchDashboardComponent, DashboardContainer, AllWarehousesContainer },
    async created() {
        this.warehouses = await this.warehousesService.findAll();
        this.filteredWarehouses = this.warehouses;
        this.$emit("warehouses-loaded", this.warehouses);

        this.forecasting = await this.forecastService.findAll();
    },
    data() {
        return {
            warehouses: [],
            filteredDashboard: [],
            forecasting: [],
            showForm: false,
            selectedDashboard: null, // Track the selected dashboard
            showAllWarehouses: false, // Flag to control rendering
        };
    },
    methods: {
        handleDashboardSelected(title) {
            this.selectedDashboard = this.warehouses.find(
                (warehouse) => warehouse.name === title
            );
            this.showAllWarehouses = false; // Hide AllWarehousesContainer
        },
        handleAllWarehousesSelected() {
            this.selectedDashboard = null; // Clear selected dashboard
            this.showAllWarehouses = true; // Show AllWarehousesContainer
        },
    },
    computed: {
        dashboardTitles() {
            return this.warehouses.map((warehouse) => warehouse.name);
        },
    },
};
</script>

<style scoped>
/* Your existing styles */
</style>
