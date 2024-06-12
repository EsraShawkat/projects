package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import practicumopdracht.models.Item;
import practicumopdracht.models.Retailer;

import static java.lang.Double.MAX_VALUE;

public class ItemView extends View {
    Button schakelKnop;
    Button verwijderKnop;
    Button nieuwKnop;
    Button opslaanKnop;

    TextField soort;
    TextField nummer;
    TextField kosten;

    CheckBox gekocht;


    ComboBox<Retailer> comboBox;
    DatePicker gekochtOp;
    ListView<Item> listView;

    private ToggleGroup sortingToggleGroup;
    private RadioButton kostenAscRadioButton;
    private RadioButton kostenDescRadioButton;
    private RadioButton soortAscRadioButton;
    private RadioButton soortDescRadioButton;



    protected Parent initializeView() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        BorderPane.setMargin(gridPane, new Insets(0, 0, 10, 0));


        ColumnConstraints cc = new ColumnConstraints();
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setHgrow(Priority.ALWAYS);
        cc2.setMaxWidth(MAX_VALUE);

        gridPane.getColumnConstraints().addAll(cc, cc2);

        Label comboLabel = new Label("Retailer:");
        comboBox = new ComboBox();
        comboBox.setMaxWidth(MAX_VALUE);
        gridPane.add(comboLabel, 0, 0);
        gridPane.add(comboBox, 1, 0);

        Label soortLabel = new Label("Soort:");
        soort = new TextField();
        gridPane.add(soortLabel, 0, 1);
        gridPane.add(soort, 1, 1);

        Label nummerLabel = new Label("Artikelnummer:");
        nummer = new TextField();
        gridPane.add(nummerLabel, 0, 2);
        gridPane.add(nummer, 1, 2);

        Label kostenLabel = new Label("Kosten:");
        kosten = new TextField();
        gridPane.add(kostenLabel, 0, 3);
        gridPane.add(kosten, 1, 3);

        Label gekochtLabel = new Label("Gekocht:");
        gekocht = new CheckBox();
        gridPane.add(gekochtLabel, 0, 4);
        gridPane.add(gekocht, 1, 4);

        Label gekochtOpLabel = new Label("Gekocht op:");
        gekochtOp = new DatePicker();
        gekochtOp.setMaxWidth(MAX_VALUE);
        gridPane.add(gekochtOpLabel, 0, 5);
        gridPane.add(gekochtOp, 1, 5);


        opslaanKnop = new Button("Opslaan");
        opslaanKnop.setMaxWidth(MAX_VALUE);
        HBox.setHgrow(opslaanKnop, Priority.ALWAYS);

        listView = new ListView<>();

        nieuwKnop = new Button("Nieuw");
        verwijderKnop = new Button("Verwijder");
        schakelKnop = new Button("Schakel");

        sortingToggleGroup = new ToggleGroup();

        soortAscRadioButton = new RadioButton("Soort (A-Z)");
        soortAscRadioButton.setToggleGroup(sortingToggleGroup);
        soortAscRadioButton.setSelected(true);

        soortDescRadioButton = new RadioButton("Soort (Z-A)");
        soortDescRadioButton.setToggleGroup(sortingToggleGroup);

        kostenAscRadioButton = new RadioButton("Kosten (oplopend)");
        kostenAscRadioButton.setToggleGroup(sortingToggleGroup);

        kostenDescRadioButton = new RadioButton("Kosten (aflopend)");
        kostenDescRadioButton.setToggleGroup(sortingToggleGroup);


        HBox sortingBox = new HBox(10, soortAscRadioButton, soortDescRadioButton, kostenAscRadioButton, kostenDescRadioButton);

        nieuwKnop.setMaxWidth(MAX_VALUE);
        verwijderKnop.setMaxWidth(MAX_VALUE);
        schakelKnop.setMaxWidth(MAX_VALUE);
        HBox.setHgrow(nieuwKnop, Priority.ALWAYS);
        HBox.setHgrow(verwijderKnop, Priority.ALWAYS);
        HBox.setHgrow(schakelKnop, Priority.ALWAYS);

        HBox knoppenHbox = new HBox(nieuwKnop, verwijderKnop, schakelKnop);
        knoppenHbox.setSpacing(10);

        VBox vbox = new VBox(10, gridPane, opslaanKnop, sortingBox, listView, knoppenHbox);
        vbox.setPadding(new Insets(10));

        return vbox;
    }

    public Button getSchakelKnop() {
        return schakelKnop;
    }

    public Button getVerwijderKnop() {
        return verwijderKnop;
    }

    public Button getNieuwKnop() {
        return nieuwKnop;
    }

    public Button getOpslaanKnop() {
        return opslaanKnop;
    }

    public TextField getSoort() {
        return soort;
    }

    public TextField getNummer() {
        return nummer;
    }

    public TextField getKosten() {
        return kosten;
    }

    public CheckBox getGekocht() {
        return gekocht;
    }

    public DatePicker getGekochtOp() {
        return gekochtOp;
    }

    public ListView<Item> getListView() {
        return listView;
    }
    public ComboBox<Retailer> getComboBox() {
        return comboBox;
    }

    public RadioButton getKostenAscRadioButton() {
        return kostenAscRadioButton;
    }

    public RadioButton getKostenDescRadioButton() {
        return kostenDescRadioButton;
    }

    public RadioButton getSoortAscRadioButton() {
        return soortAscRadioButton;
    }

    public RadioButton getSoortDescRadioButton() {
        return soortDescRadioButton;
    }
}


