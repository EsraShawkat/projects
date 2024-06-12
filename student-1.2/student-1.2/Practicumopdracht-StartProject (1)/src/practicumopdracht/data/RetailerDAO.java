package practicumopdracht.data;

import practicumopdracht.models.Retailer;

import java.util.ArrayList;
import java.util.List;

public abstract class RetailerDAO implements DAO<Retailer>{
    protected List<Retailer> objects;

    public RetailerDAO() {
        objects = new ArrayList<>();
    }

    // Checkt of de id in de objectlijst kan zitten zo ja returned hij het terug
    public Retailer getById(int id){
        return objects.size() <= id ? null : id < 0 ? null : objects.get(id);
    }



    // geeft een conditional als het object niet in de objectlijst zit geeft hij -1
    // anders geeft hij de index van het object mee
    public int getIdFor(Retailer retailer){
        return (!objects.contains(retailer)) ? -1: objects.indexOf(retailer);
    }

    @Override
    public List<Retailer> getAll() {
        return objects;
    }

    @Override
    public void addOrUpdate(Retailer object) {
        if(!objects.contains(object)){
            objects.add(object);
        }
    }

    @Override
    public void remove(Retailer object) {
        objects.remove(object);
    }

    @Override
    public abstract boolean save();

    @Override
    public abstract boolean load();

}
