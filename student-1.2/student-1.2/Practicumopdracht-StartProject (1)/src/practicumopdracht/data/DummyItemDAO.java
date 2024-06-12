package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Item;

import java.time.LocalDate;

public class DummyItemDAO extends ItemDAO{
    public boolean save() {
        return true;
    }

    @Override
    public boolean load() {
        objects.add(new Item("t-shirt", 50600, 13.23, true,
                LocalDate.of(2000, 2, 2), MainApplication.getRetailerDAO().getById(0)));
        objects.add(new Item("jeans", 4053, 50, false,
                null, MainApplication.getRetailerDAO().getById(1)));
        objects.add(new Item("tas", 45678, 50000, true,
                LocalDate.of(2022,2,3), MainApplication.getRetailerDAO().getById(0)));
        return true;
    }
}
