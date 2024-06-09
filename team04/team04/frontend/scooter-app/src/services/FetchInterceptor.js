import fetchIntercept from 'fetch-intercept'

export class FetchInterceptor{
    static theInstance;
    session;
    router;
    unregister;
    constructor(session, router) {
        this.router = router;
        this.session = session;
        FetchInterceptor.theInstance = this;
        this.unregister = fetchIntercept.register(this);
        console.log("FetchInterceptor has been registered with current token; current token = " ,
            FetchInterceptor.theInstance.session.getTokenFromBrowserStorage())
    }
    request(url, options){
        let token = FetchInterceptor.theInstance.session.getTokenFromBrowserStorage();
        console.log(token)
        if(token == null){
            return [url, options];
        }else if (options == null){
            return [url, {headers: {Authorization: token}}]
        }else{
            let newOptions = options ? { ...options } : {};

            if (!newOptions.headers) {
                newOptions.headers = {
                    Authorization: token,
                };
            } else {
                newOptions.headers = {
                    ...newOptions.headers,
                    Authorization: token,
                };
            }
            return [url, newOptions]
        }
    }
    response(response){
        if(response.status === 401){
            FetchInterceptor.theInstance.router.push('/sign-out')
        }
        return response;
    }
}
