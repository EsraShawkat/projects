export class SessionSbService{
    BROWSER_STORAGE_ITEM_NAME
    RESOURCES_URL
    constructor(resourcesUrl, browserStorageItemName) {
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.RESOURCES_URL = resourcesUrl;

        this.getTokenFromBrowserStorage();
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
   async asyncSignIn(email, password){
        const user = await fetch(`${this.RESOURCES_URL}/login`, {
            headers: {'Content-Type': 'application/json'},
            method: "POST",
            body: JSON.stringify({"email": email, "password": password}),
            credential: "include"
        })
       console.log(user)
       if(user.ok){
           const userdata = await user.json();
           this.saveTokenInToBrowserStorage(user.headers.get('Authorization'), JSON.stringify(userdata))
       }

       return user;
    }
    saveTokenInToBrowserStorage(token, user){
        window.sessionStorage.setItem("token", token)
        window.sessionStorage.setItem("user", user)
    }
    getTokenFromBrowserStorage(){
        return window.sessionStorage.getItem("token");
    }
    signOut(){
        window.sessionStorage.removeItem("token");
        window.sessionStorage.removeItem("user");
    }
}
