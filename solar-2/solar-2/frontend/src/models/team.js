
class Team {
    constructor(id, name, warehouse, members, projects) {
        this.id = id;
        this.name = name;
        this.warehouse = null;
        this.members = members;
        this.projects = projects;
    }

    static randomNumber() {
        return Math.floor(Math.random() * 1000);

    }

    static createSampleTeam() {
        const randomId = this.randomNumber();
        const randomName = `Team ${this.randomNumber()}`;
        // const randomWarehouse = `AMS ${this.randomNumber()}`;
        const randomMembers = ['John', 'Jane', 'Jack'];
        const randomProjects = ['Project 1', 'Project 2', 'Project 3', 'Project 4', 'Project 5'];

        const team = new Team(randomId, randomName, null, randomMembers, randomProjects);
        team.warehouse = randomId;

        return team;
    }

    static copyConstructor(team){
        if (team == null) return null;
        return Object.assign(new Team(0), team);
    }
}


export default Team;