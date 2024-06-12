package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class ItemTextDAO extends ItemDAO {

    private static final String FILENAME = "items.txt";


    @Override
    public boolean save() {
        File file = new File(FILENAME);

        PrintWriter printWriter = null;


        try {
            printWriter = new PrintWriter(file);

            printWriter.println(objects.size());

            for (Item item : objects) {
                printWriter.println(item.getSoort());
                printWriter.println(item.getArtikelnummer());
                printWriter.println(item.getKosten());
                printWriter.println(item.isGekocht());
                printWriter.println(item.getGekochtOp());
                // hij checkt of de gekozen retailer bestaat in de lijst zoja stuurt de index ervan als er een match is
                printWriter.println(MainApplication.getRetailerDAO().getIdFor(item.getHoortBij()));
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
                String soort = scanner.nextLine();
                int artikelNummer = Integer.parseInt(scanner.nextLine());
                double kosten = Double.parseDouble(scanner.nextLine());
                boolean isGekocht = scanner.nextBoolean();
                scanner.nextLine();

                LocalDate gekochtOp = LocalDate.parse(scanner.nextLine());
                int hoortbij = scanner.nextInt();
                scanner.nextLine();

                Item item = new Item(soort, artikelNummer, kosten, isGekocht, gekochtOp, MainApplication
                        .getRetailerDAO().getById(hoortbij));


                addOrUpdate(item);
            }
            return true;
        } catch (IOException ex) {
            System.err.println("Mislukt data te laden: " + ex.getMessage());
            return false;
        }

    }
}
