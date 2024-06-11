import Forecast from "@/models/forecast";

class ForecastAdapter {
    resourceUrl;

    constructor(resourceURL) {
        this.resourceUrl = resourceURL;

    }

    /**
     * Fetches JSON data from a given URL using a specified HTTP method.
     * @param {string} url - The URL to fetch data from.
     * @param {string} method - The HTTP method (GET, POST, PUT, DELETE).
     * @param {object} bodyToSend - The data to send with the request (optional).
     * @returns {Promise<any>} - A promise that resolves to the fetched data.
     * @throws {Error} - If an HTTP error occurs.
     */
    async fetchJson(url, method, bodyToSend) {
        let jsonBody = bodyToSend ? JSON.stringify(bodyToSend) : null;
        let response = await fetch(url, {
            method: method,
            body: jsonBody,
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return method !== 'DELETE' ? await response.json() : null;
    }

    async findAll() {
        const forecasts = await this.fetchJson(this.resourceUrl,'GET');
        return forecasts.map(forecast => Forecast.copyConstructor(forecast));
    }

    async findById(id) {
        const forecast = await this.fetchJson(`${this.resourceUrl}/${id}`, 'GET');
        return Forecast.copyConstructor(forecast);
    }
}

export default ForecastAdapter;
