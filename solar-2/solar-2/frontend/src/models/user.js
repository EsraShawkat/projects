class User {
    constructor(id, name, teamId) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
    }

    static copyConstructor(member){
        if (member == null) return null;
        return Object.assign(new User(0), member);
    }
}

export default User;