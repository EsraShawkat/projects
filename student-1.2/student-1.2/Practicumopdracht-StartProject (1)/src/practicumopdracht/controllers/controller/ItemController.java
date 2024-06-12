package practicumopdracht.controllers.controller;

import practicumopdracht.comparators.KostenComparator;
import practicumopdracht.comparators.SoortComparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import practicumopdracht.MainApplication;
import practicumopdracht.controllers.Controller;
import practicumopdracht.models.Item;
import practicumopdracht.models.Retailer;
import practicumopdracht.views.ItemView;
import practicumopdracht.views.View;

import java.time.LocalDate;
import java.util.Optional;

public class ItemController extends Controller {
    private ItemView view;


    public ItemController(Retailer retailer) {
        this.view = new ItemView();

        view.getNieuwKnop().setOnAction(ActionEvent -> {
            if (view.getListView().getSelectionModel().getSelectedItem() != null) {
                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("u moet een item selecteren.");
                alert.showAndWait();
            }
        });

        ObservableList<Item> itemObservableList1 = FXCollections.observableArrayList(MainApplication.getItemDAO()
                .getAllFor(retailer));

        view.getSchakelKnop().setOnAction(ActionEvent -> MainApplication.switchController(new RetailerController()));

        view.getListView().setItems(itemObservableList1);


        view.getVerwijderKnop().setOnAction(ActionEvent -> {
            if (view.getListView().getSelectionModel().getSelectedItem() != null) {
                verwijder(view.getListView().getSelectionModel().getSelectedItem());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("u heeft niks geselecteerd");
                alert.showAndWait();
            }
        });

        view.getComboBox().setOnAction(actionEvent -> {
            ObservableList<Item> itemObservableList = FXCollections.observableArrayList(MainApplication.getItemDAO()
                    .getAllFor(view.getComboBox().getSelectionModel().getSelectedItem()));
            view.getListView().setItems(itemObservableList);

            clearFields();

            SoortComparator soortComparator = new SoortComparator(true);
            view.getListView().getItems().sort(soortComparator);
        });

        view.getListView().setOnMouseClicked(mouseEvent -> {
            if (view.getListView().getSelectionModel().getSelectedItem() != null) {
                viewItem(view.getListView().getSelectionModel().getSelectedItem());
            }
        });



        view.getComboBox().getItems().addAll(MainApplication.getRetailerDAO().getAll());
        view.getComboBox().getSelectionModel().select(retailer);

        view.getOpslaanKnop().setOnAction(ActionEvent -> validatie());


        view.getKostenAscRadioButton().setOnAction(actionEvent -> kostenComparator(false));
        view.getKostenDescRadioButton().setOnAction(actionEvent -> kostenComparator(true));

        view.getSoortAscRadioButton().setOnAction(actionEvent -> soortComparator(true));
        view.getSoortDescRadioButton().setOnAction(actionEvent -> soortComparator(false));


        SoortComparator soortComparator = new SoortComparator(true);
        view.getListView().getItems().sort(soortComparator);
    }

    public void bewerkItem(Item item) {
        item.setKosten(Double.parseDouble(view.getKosten().getText()));
        item.setGekocht(view.getGekocht().isSelected());
        item.setArtikelnummer(Integer.parseInt(view.getNummer().getText()));
        item.setSoort(view.getSoort().getText());
        item.setGekochtOp(view.getGekochtOp().getValue());
        item.setHoortBij(view.getComboBox().getSelectionModel().getSelectedItem());
    }

    public void viewItem(Item item) {
        view.getKosten().setText(Double.toString(item.getKosten()));
        view.getGekocht().setSelected(item.isGekocht());
        view.getNummer().setText(Integer.toString(item.getArtikelnummer()));
        view.getSoort().setText(item.getSoort());
        view.getGekochtOp().setValue(item.getGekochtOp());
        view.getComboBox().getSelectionModel().select(item.getHoortBij());
    }


    public void validatie() {
        Item item = null;

        Alert fout = new Alert(Alert.AlertType.INFORMATION);
        fout.setHeaderText("Er is iets fout gegaan:");
        StringBuilder foutmelding = new StringBuilder();
        String soort = view.getSoort().getText();
        String nummer = view.getNummer().getText();
        String kosten = view.getKosten().getText();

        boolean gekocht = view.getGekocht().isSelected();
        LocalDate gekochtOp = view.getGekochtOp().getValue();


        if (soort == null || soort.trim().isEmpty()) {
            foutmelding.append("Soort mag niet leeg zijn");
            foutmelding.append("\n");
        }

        if (nummer == null || nummer.trim().isEmpty()) {
            foutmelding.append("Artikelnummer mag niet leeg zijn");
            foutmelding.append("\n");
        } else if (!nummer.matches("\\d+")) {
            foutmelding.append("Artikelnummer mag alleen cijfers bevatten");
            foutmelding.append("\n");
        }

        if (kosten == null || kosten.trim().isEmpty()) {
            foutmelding.append("Kosten mag niet leeg zijn");
            foutmelding.append("\n");
        } else if (!kosten.matches("\\d+(\\.\\d+)?")) {
            foutmelding.append("Kosten moet een geldig bedrag bevatten");
            foutmelding.append("\n");
        }

        if (gekochtOp != null && !gekocht) {
            foutmelding.append("Gekocht op mag alleen ingevuld worden als Gekocht geselecteerd is");
            foutmelding.append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (foutmelding.length() != 0) {
            fout.setContentText(foutmelding.toString());
            fout.showAndWait();
            return;
        }

        // als je geen item hebt geselecteerd voegt hij het toe, anders werkt hij het bij
        if (view.getListView().getSelectionModel().getSelectedItem() == null) {
            Retailer geselecteerdeRetailer = MainApplication.getRetailerDAO().getById(
                    view.getComboBox().getSelectionModel().getSelectedIndex());

            item = new Item(view.getSoort().getText(), Integer.parseInt(view.getNummer().getText()),
                    Double.parseDouble(view.getKosten().getText()), view.getGekocht().isSelected(),
                    view.getGekochtOp().getValue(),geselecteerdeRetailer);
            view.getListView().getItems().add(item);
            addItem(item);
        } else {
            item = view.getListView().getSelectionModel().getSelectedItem();
            bewerkItem(item);
            view.getListView().refresh();
        }


        alert.setHeaderText("het is goed gegaan");
        alert.setContentText(item.toString());
        alert.showAndWait();
    }

    public void addItem(Item item) {
//        view.getListView().getItems().add(item);
        MainApplication.getItemDAO().addOrUpdate(item);
        clearFields();
    }

    public void verwijder(Item item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText("Weet u zeker dat u wilt verwijderen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            MainApplication.getItemDAO().remove(item);
            view.getListView().getItems().remove(item);
            view.getListView().refresh();
        }
    }

    public void clearFields() {
        if (view.getListView().getSelectionModel().getSelectedItem() != null) {
            view.getListView().getSelectionModel().select(null);
        }
        view.getKosten().setText(null);
        view.getNummer().setText(null);
        view.getSoort().setText(null);
        view.getGekochtOp().setValue(null);
        view.getGekocht().setSelected(false);
    }


    public void kostenComparator(boolean isAscending){
        ObservableList<Item> items = FXCollections.observableArrayList(MainApplication.getItemDAO().getAll());
        FXCollections.sort(items, new KostenComparator(isAscending));
        FXCollections.sort(view.getListView().getItems(), new KostenComparator(isAscending));
    }

    public void soortComparator(boolean isAscending){
        ObservableList<Item> items = FXCollections.observableArrayList(MainApplication.getItemDAO().getAll());
        FXCollections.sort(items, new SoortComparator(isAscending));
        FXCollections.sort(view.getListView().getItems(), new SoortComparator(isAscending));
    }

    @Override
    public View getView() {
        return view;
    }
}
