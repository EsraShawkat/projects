import Team from "@/models/team";
import items from "core-js/stable/dom-collections";
// import inventory from "@/models/inventory";
import Item from "@/models/inventory";


class Warehouse {
    constructor(id, name, location, projects, teams = []) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.teams = teams;
        this.projects = projects;
        this.inventory = {};
    }


    static randomNumber() {
        return Math.floor(Math.random() * 1000);
    }

    static Project = ["1125", "1126", "1127", "1128", "1129", "1130", "1131", "1132", "1133", "1134", "1135", "1136",
        "1137", "1138", "1139", "1140", "1141", "1142"]

    static Allteams = [];


    static createSampleWarehouse() {
        const randomId = this.randomNumber();
        const randomName = `Warehouse ${this.randomNumber()}`;
        const randomLocation = ['Amsterdam', 'Rotterdam', 'Den Haag', 'Utrecht', 'Delft'];
        const selectedLocation = randomLocation[Math.floor(Math.random() * randomLocation.length)];

        const randomProjectIndex = Math.floor(Math.random() * this.Project.length);
        const randomProject = this.Project[randomProjectIndex];

        const warehouse = new Warehouse(randomId, randomName, selectedLocation, randomProject);
        warehouse.inventory = Item.createSampleItem();


        const numTeams = Math.floor(Math.random() * 6) + 1;

        for (let i = 0; i < numTeams; i++) {
            const randomTeam = Team.createSampleTeam();
            randomTeam.warehouse = randomId;
            warehouse.teams.push(randomTeam);
        }

        Warehouse.Allteams.push(...warehouse.teams);

        items.forEach(item => {
            warehouse.addInventory(item, Math.floor(Math.random() * 30)); // Willekeurige hoeveelheid toewijzen
        });

        return warehouse;
    }

    addInventory(item, quantity) {
        if (this.inventory[item.id]) {
            this.inventory[item.id].quantity += quantity;
        } else {
            this.inventory[item.id] = {
                item,
                quantity,
            };
        }
    }

    removeInventory(item, quantity) {
        if (this.inventory[item.id]) {
            this.inventory[item.id].quantity -= quantity;
            if (this.inventory[item.id].quantity < 0) {
                this.inventory[item.id].quantity = 0;
            }
        }
    }

    assignInventory(selectedWarehouseId, items) {
        if (this.warehouseId === selectedWarehouseId) {
            items.forEach(item => {
                const quantity = Math.floor(Math.random() * 30);
                this.addInventory(item, quantity);
            });
        }
    }

    static copyConstructor(warehouse){
        if (warehouse == null) return null;
        return Object.assign(new Warehouse(0), warehouse);
    }

}

export default Warehouse;