package practicumopdracht.data;

import practicumopdracht.models.Retailer;


public class DummyRetailerDAO extends RetailerDAO {
    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean load() {
        objects.add(new Retailer("asos", "apple pay"));
        objects.add(new Retailer("kruidvat", "Ideal"));
        return true;
    }
}
