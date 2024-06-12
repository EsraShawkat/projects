package practicumopdracht.data;

import practicumopdracht.models.Item;
import practicumopdracht.models.Retailer;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemDAO implements DAO<Item> {
    protected List<Item> objects;

    public ItemDAO() {
        objects = new ArrayList<>();
    }

    public List<Item> getAllFor(Retailer object) {
        List<Item> Items = new ArrayList<>();
        for (Item item : objects){
            if(item.getHoortBij().equals(object)){
                Items.add(item);
            }
        }
        return Items;
    }


    @Override
    public List<Item> getAll() {
        return objects;
    }

    @Override
    public void addOrUpdate(Item object) {
        if (!objects.contains(object)) {
            objects.add(object);
        }
    }

    @Override
    public void remove(Item object) {
        objects.remove(object);
    }

    @Override
    public abstract boolean save();

    @Override
    public abstract boolean load();
}
