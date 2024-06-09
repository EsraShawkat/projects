import {Scooter} from "../models/scooter";

export class ScootersAdaptor{
    resourcesUrl;

    constructor(resourcesUrl){
        this.resourcesUrl = resourcesUrl;
        console.log("created scooters for " + this.resourcesUrl);
    }
    async fetchJSON(url, options = null){
        let headers = {
            'Content-Type': 'application/json',
        };
        if (options && options.headers) {
            headers = { ...headers, ...options.headers };
            delete options.headers;
        }

        let response = await fetch(url, {
            ...options,
            headers: headers
        });
        if(response.ok){
            return await response.json();
        }else{
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return  null;
        }
    }
    async findAll(){
        console.log("fetching all scooters...");
        const scooters = await this.fetchJSON(this.resourcesUrl)
        return scooters?.map(s => Scooter.copyConstructor(s))
    }
    async findById(Id){
        const scooter = await this.fetchJSON(this.resourcesUrl + `/${Id}`)
        if(scooter == null){
            return null;
        }
        return Scooter.copyConstructor(scooter)
    }

    async Save(scooter){
        let updatedScooter;

        if (scooter.id === 0) {
            // For new scooters (ID=0), use POST method to create a new scooter
            updatedScooter = await this.fetchJSON(this.resourcesUrl, {
                method: "POST",
                body: JSON.stringify(scooter)
            });
        } else {
            // For existing scooters, use PUT method to update the scooter
            updatedScooter = await this.fetchJSON(`${this.resourcesUrl}/${scooter.id}`, {
                method: "PUT",
                body: JSON.stringify(scooter)
            });
        }

        if (updatedScooter == null) {
            return null;
        }

        return Scooter.copyConstructor(updatedScooter);
    }
    async deleteById(Id){
        const deletedScooter = await this.fetchJSON(`${this.resourcesUrl}/${Id}`, {method: "DELETE"})
        if(deletedScooter == null){
            return null;
        }
        return Scooter.copyConstructor(deletedScooter);
    }
}
