export class Scooter {
    id;
    tag;
    status;
    gpsLocation;
    mileage;
    batteryCharge;

    constructor(id, tag, status, gpsLocation, mileage, batteryCharge) {
        this.id = id;
        this.tag = tag;
        this.status = status;
        this.gpsLocation = gpsLocation;
        this.mileage = mileage;
        this.batteryCharge = batteryCharge;
    }
    static copyConstructor(scooter) {
        if(!scooter)return null;
        return  Object.assign(new Scooter(0), scooter)
    }

    static status = {
        IDLE: "IDLE",
        INUSE: "INUSE",
        MAINTENANCE: "MAINTENANCE"
    }
    static scooterSample(id) {
        const tag = Math.floor(Math.random() *90000 + 1);
        const statusNumber = Math.floor(Math.random() * 3 + 1);
        const status = statusNumber === 3 ? Scooter.status.IDLE : statusNumber === 2 ? Scooter.status.INUSE : Scooter.status.MAINTENANCE;
        const gpsLocationArray = [];
        for (let i = 0; i < 5; i++) {
            gpsLocationArray.push(Math.floor(Math.random() * 99));
        }
        const gpsLocation = `${gpsLocationArray[0]}.${gpsLocationArray[1]}.${gpsLocationArray[2]}.${gpsLocationArray[3]}.${gpsLocationArray[4]}`;


        const mileage = Math.floor(Math.random() * 9000 + 1);
        const batteryCharge = Math.floor(Math.random() * 100 + 5)

        return new Scooter(id, tag, status, gpsLocation, mileage, batteryCharge)
    }


}
