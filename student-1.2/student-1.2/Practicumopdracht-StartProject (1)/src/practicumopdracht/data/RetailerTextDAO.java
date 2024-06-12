package practicumopdracht.data;


import practicumopdracht.models.Retailer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class RetailerTextDAO extends RetailerDAO {

    private static final String FILENAME = "retailers.txt";


    @Override
    public boolean save() {
        File file = new File(FILENAME);

        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(file);

            printWriter.println(objects.size());

            for (Retailer retailer : objects) {
//                printWriter.println(objects.indexOf(retailer));
                printWriter.println(retailer.getNaam());
                printWriter.println(retailer.getBetaalmethoden());

            }
            return true;
        } catch (FileNotFoundException ex) {
            System.err.println("Er was geen bestand gevonden om op te slaan" + ex.getMessage());
            return false;
        } finally {
            if (printWriter != null)
                printWriter.close();
        }

    }

    @Override
    public boolean load() {
        objects.clear();

        File file = new File(FILENAME);

        try (
                Scanner scanner = new Scanner(file)
        ) {
            int aantalObjecten = scanner.nextInt();

            scanner.nextLine();
            for (int i = 0; i < aantalObjecten; i++) {
                String naam = scanner.nextLine();
                String betaalmethoden = scanner.nextLine();


                Retailer retailer = new Retailer(naam, betaalmethoden);
                addOrUpdate(retailer);
            }
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("bestand kon niet opgeslagen worden" + e.getMessage());
            return false;
        }

    }

}
