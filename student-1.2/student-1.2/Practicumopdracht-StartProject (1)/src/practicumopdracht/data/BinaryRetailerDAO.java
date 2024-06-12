package practicumopdracht.data;

import practicumopdracht.models.Retailer;

import java.io.*;

public class BinaryRetailerDAO extends RetailerDAO {
    public static final String FILENAME = "binaryRetailer.dat";
    @Override
    public boolean save() {
        File file = new File(FILENAME);

        try(
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)
        ){
            dataOutputStream.writeInt(objects.size());
            System.out.println(objects);
            for (Retailer retailer : objects) {

                dataOutputStream.writeUTF(retailer.getNaam());
                dataOutputStream.writeUTF(retailer.getBetaalmethoden());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;

    }


    public boolean load() {
        File file = new File(FILENAME);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            int numberOfItems = dataInputStream.readInt();

            for (int i = 0; i < numberOfItems; i++) {
                String naam = dataInputStream.readUTF();
                String betaalmethoden = dataInputStream.readUTF();

                Retailer afspeellijst = new Retailer(naam, betaalmethoden);
                addOrUpdate(afspeellijst);
            }
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("Bestand niet gevonden: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Fout tijdens het lezen van het bestand: " + e.getMessage());
        }
        return false;
    }
}

