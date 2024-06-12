package practicumopdracht.controllers.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import practicumopdracht.Main;
import practicumopdracht.MainApplication;
import practicumopdracht.comparators.NaamComparator;
import practicumopdracht.controllers.Controller;
import practicumopdracht.models.Item;
import practicumopdracht.models.Retailer;
import practicumopdracht.views.RetailerView;
import practicumopdracht.views.View;

import java.awt.event.ActionEvent;
import java.util.Optional;

public class RetailerController extends Controller {

    private ObservableList<Retailer> retailerObservableList = FXCollections.observableArrayList(MainApplication
            .getRetailerDAO().getAll());
    private RetailerView view;

    public RetailerController() {
        view = new RetailerView();

        view.getNieuwKnop().setOnAction(ActionEvent -> {
            if (view.getListView().getSelectionModel().getSelectedItem() != null) {
                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("u moet een retailer selecteren.");
                alert.showAndWait();
            }
        });


        view.getListView().setItems(retailerObservableList);

        view.getSchakelKnop().setOnAction(ActionEvent -> {
            if (view.getListView().getSelectionModel().getSelectedItem() != null) {
                MainApplication.switchController(new ItemController(view.getListView().getSelectionModel().getSelectedItem()));
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("u moet een retailer selecteren.");
                alert.showAndWait();
            }
        });

        view.getVerwijderKnop().setOnAction(ActionEvent -> {
            if (view.getListView().getSelectionModel().getSelectedItem() != null) {
                verwijder(view.getListView().getSelectionModel().getSelectedItem());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("u heeft niks geselecteerd");
                alert.showAndWait();
            }
        });

        view.getListView().setOnMouseClicked(mouseEvent -> {
            if (view.getListView().getSelectionModel().getSelectedItem() != null) {
                viewRetailer(view.getListView().getSelectionModel().getSelectedItem());
            }
        });



        view.getOpslaanMenuItem().setOnAction(actionEvent -> handleSave());
        view.getOpslaanKnop().setOnAction(ActionEvent -> validatie());
        view.getLaadMenuItem().setOnAction(actionEvent -> handleLoad());
        view.getAfsluitMenuItem().setOnAction(event -> handleClose());

        view.getAscendingMenuItem().setOnAction(actionEvent -> retailerSort(true));
        view.getDescendingMenuItem().setOnAction(actionEvent -> retailerSort(false));


    }

    public void handleSave() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Bevestiging");
        confirmationAlert.setHeaderText("Weet u zeker dat u wilt opslaan?");
        confirmationAlert.setContentText("De huidige gegevens worden overschreven.");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            boolean masterSaveResult = MainApplication.getRetailerDAO().save();
            boolean detailSaveResult = MainApplication.getItemDAO().save();

            // Show result alert
            Alert resultAlert = new Alert(Alert.AlertType.INFORMATION);
            resultAlert.setTitle("Resultaat");
            resultAlert.setHeaderText(null);

            if (masterSaveResult && detailSaveResult) {
                resultAlert.setContentText("Opslaan is gelukt.");
            } else {
                resultAlert.setContentText("Opslaan is mislukt.");
            }

            resultAlert.showAndWait();
        }
    }

    public void handleLoad() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Bevestiging");
        confirmationAlert.setHeaderText("Weet u zeker dat u wilt laden?");
        confirmationAlert.setContentText("De huidige gegevens worden overschreven.");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked "OK", proceed with saving
            boolean retailerLoadResult = MainApplication.getRetailerDAO().load();
            boolean itemLoadResult = MainApplication.getItemDAO().load();

            // Show result alert
            Alert resultAlert = new Alert(Alert.AlertType.INFORMATION);
            resultAlert.setTitle("Resultaat");
            resultAlert.setHeaderText(null);

            if (retailerLoadResult && itemLoadResult) {
                resultAlert.setContentText("Laden is gelukt.");
                view.getListView().getItems().addAll(MainApplication.getRetailerDAO().getAll());

                retailerSort(true);
            } else {
                resultAlert.setContentText("Laden is mislukt.");
            }

            resultAlert.showAndWait();
        }
    }

    private void handleClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Wilt u de wijzigingen opslaan?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            MainApplication.getRetailerDAO().save();
            MainApplication.getItemDAO().save();
            System.exit(7);
        } else {
            System.exit(7);
        }
    }

    public void bewerkRetailer(Retailer retailer) {

        retailer.setNaam(view.getNaam().getText());
        retailer.setBetaalmethoden(view.getBetaling().getText());
    }

    public void viewRetailer(Retailer retailer) {
        view.getNaam().setText(retailer.getNaam());
        view.getBetaling().setText(retailer.getBetaalmethoden());
    }

    public void validatie() {
        Retailer retailer = null;
        StringBuilder foutmelding = new StringBuilder();
        String naam = view.getNaam().getText();
        String betaling = view.getBetaling().getText();

        if (naam == null || naam.trim().isEmpty()) {
            foutmelding.append("Naam mag niet leeg zijn");
            foutmelding.append("\n");
        }
        if (betaling == null || betaling.trim().isEmpty()) {
            foutmelding.append("Betaalmethoden mag niet leeg zijn");
            foutmelding.append("\n");
        }

        if (foutmelding.length() > 0) {
            Alert fout = new Alert(Alert.AlertType.ERROR);
            fout.setHeaderText("Er is iets fout gegaan:");
            fout.setContentText(foutmelding.toString());
            fout.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (view.getListView().getSelectionModel().getSelectedItem() == null) {
            retailer = new Retailer(view.getNaam().getText(), view.getBetaling().getText());
            addRetailer(retailer);
        } else {
            retailer = view.getListView().getSelectionModel().getSelectedItem();
            bewerkRetailer(retailer);
            view.getListView().refresh();
        }

        alert.setHeaderText("Het is goed gegaan");
        alert.setContentText(retailer.toString());
        alert.showAndWait();
    }

    public void addRetailer(Retailer retailer) {
        view.getListView().getItems().add(retailer);
        MainApplication.getRetailerDAO().addOrUpdate(retailer);
        clearFields();
    }

    public void verwijder(Retailer retailer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText("Weet u zeker dat u wilt verwijderen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            MainApplication.getRetailerDAO().remove(retailer);
            view.getListView().getItems().remove(retailer);
            view.getListView().refresh();

        }


    }

    public void clearFields() {
        if (view.getListView().getSelectionModel().getSelectedItem() != null) {
            view.getListView().getSelectionModel().select(null);
        }

        view.getBetaling().setText(null);
        view.getNaam().setText(null);
    }

    public void retailerSort(boolean isAscending){
        ObservableList<Retailer> retailers = FXCollections.observableArrayList(MainApplication.getRetailerDAO().getAll());
        FXCollections.sort(retailers, new NaamComparator(isAscending));
        FXCollections.sort(view.getListView().getItems(), new NaamComparator(isAscending));
    }

    @Override
    public View getView() {
        return view;
    }


}
