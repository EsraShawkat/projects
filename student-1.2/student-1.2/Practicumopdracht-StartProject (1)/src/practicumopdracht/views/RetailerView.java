package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import practicumopdracht.models.Retailer;

import static java.lang.Double.MAX_VALUE;


public class RetailerView extends View{
    private Button schakelKnop;
    private Button verwijderKnop;
    private Button nieuwKnop;
    private Button opslaanKnop;

    private TextField naam;
    private TextField betaling;

    private ListView<Retailer> listView;

    private MenuBar menuBar;
    private Menu fileMenu;
    private MenuItem laadMenuItem;
    private MenuItem opslaanMenuItem;
    private MenuItem afsluitMenuItem;


    private Menu sortMenu;
    private MenuItem ascendingMenuItem;
    private MenuItem descendingMenuItem;




    protected Parent initializeView() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        BorderPane.setMargin(gridPane, new Insets(0,0,10,0));


        ColumnConstraints cc = new ColumnConstraints();
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setHgrow(Priority.ALWAYS);
        cc2.setMaxWidth(MAX_VALUE);

        gridPane.getColumnConstraints().addAll(cc, cc2);

        Label naamLabel = new Label("Naam:");
        naam = new TextField();
        gridPane.add(naamLabel, 0, 0);
        gridPane.add(naam, 1,0);

        Label betalingLabel = new Label("Betaalmethoden:");
        betaling = new TextField();
        gridPane.add(betalingLabel, 0, 1);
        gridPane.add(betaling, 1,1);

        opslaanKnop = new Button("Opslaan");
        opslaanKnop.setMaxWidth(MAX_VALUE);
        HBox.setHgrow(opslaanKnop, Priority.ALWAYS);

        listView = new ListView();

        VBox vBox = new VBox(opslaanKnop, listView);
        BorderPane.setMargin(vBox, new Insets(0,0,10,0));
        vBox.setSpacing(10);

        nieuwKnop = new Button("Nieuw");
        verwijderKnop = new Button("Verwijder");
        schakelKnop= new Button("Schakel");

        menuBar = new MenuBar();
        fileMenu = new Menu("Bestand");
        laadMenuItem = new MenuItem("Laden");
        opslaanMenuItem = new MenuItem("Opslaan");
        afsluitMenuItem= new MenuItem("Afsluiten");

        sortMenu = new Menu("Sorteren");
        ascendingMenuItem = new MenuItem("Naam (A-Z)");
        descendingMenuItem = new MenuItem("Naam (Z-A)");
        sortMenu.getItems().addAll(ascendingMenuItem, descendingMenuItem);
        fileMenu.getItems().addAll(laadMenuItem,opslaanMenuItem,afsluitMenuItem);


        menuBar.getMenus().addAll(fileMenu,sortMenu);


        nieuwKnop.setMaxWidth(MAX_VALUE);
        verwijderKnop.setMaxWidth(MAX_VALUE);
        schakelKnop.setMaxWidth(MAX_VALUE);
        HBox.setHgrow(nieuwKnop, Priority.ALWAYS);
        HBox.setHgrow(verwijderKnop, Priority.ALWAYS);
        HBox.setHgrow(schakelKnop, Priority.ALWAYS);

        HBox knoppenHbox = new HBox(nieuwKnop, verwijderKnop, schakelKnop);
        knoppenHbox.setSpacing(10);

        VBox topContainer = new VBox();
        topContainer.getChildren().addAll(menuBar,gridPane);
        topContainer.setSpacing(10);

        VBox vbox = new VBox(10, topContainer, vBox, knoppenHbox);
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

    public TextField getNaam() {
        return naam;
    }

    public TextField getBetaling() {
        return betaling;
    }

    public ListView<Retailer> getListView() {
        return listView;
    }

    public MenuItem getLaadMenuItem() {
        return laadMenuItem;
    }

    public MenuItem getOpslaanMenuItem() {
        return opslaanMenuItem;
    }

    public MenuItem getAfsluitMenuItem() {
        return afsluitMenuItem;
    }



    public MenuItem getAscendingMenuItem() {
        return ascendingMenuItem;
    }

    public MenuItem getDescendingMenuItem() {
        return descendingMenuItem;
    }
}



