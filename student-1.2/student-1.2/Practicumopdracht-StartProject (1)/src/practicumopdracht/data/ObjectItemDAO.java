package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Item;

import java.io.*;

public class ObjectItemDAO extends ItemDAO {
    public static final String FILENAME = "ObjectItem.obj";
    @Override
    public boolean save() {
        File file = new File(FILENAME);

        try(
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ){
            objectOutputStream.writeInt(objects.size());

            for (Item item : objects) {
                objectOutputStream.writeObject(item);
                objectOutputStream.writeInt(MainApplication.getRetailerDAO().getIdFor(item.getHoortBij()));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean load() {
        objects.clear();

        File file = new File(FILENAME);

        try(
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ){
            int aantalObjecten = objectInputStream.readInt();

            for(int i = 0; i < aantalObjecten; i++) {
                Item item = (Item) objectInputStream.readObject();
                int hoortbij = objectInputStream.readInt();
                item.setHoortBij(MainApplication.getRetailerDAO().getById(hoortbij));
                addOrUpdate(item);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }
}


