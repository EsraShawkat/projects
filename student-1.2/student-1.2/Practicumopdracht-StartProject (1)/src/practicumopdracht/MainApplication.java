package practicumopdracht;


import practicumopdracht.controllers.Controller;
import practicumopdracht.controllers.controller.RetailerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import practicumopdracht.data.*;
import practicumopdracht.views.ItemView;
import practicumopdracht.views.View;


public class MainApplication extends Application {
    private static Stage stage;

    private static RetailerDAO retailerDAO;

    private static ItemDAO itemDAO;

    public static RetailerDAO getRetailerDAO() {
        return retailerDAO;
    }

    public static ItemDAO getItemDAO() {
        return itemDAO;
    }

    @Override
    public void start(Stage stage) {
        MainApplication.stage = stage;

        if (!Main.launchedFromMain) {
            System.err.println("Je moet deze applicatie opstarten vanuit de Main-class, niet de MainApplication-class!");
            System.exit(1337);

            return;
        }

//      retailerDAO = new RetailerTextDAO();
//      itemDAO = new ItemTextDAO();

        retailerDAO = new BinaryRetailerDAO();
        itemDAO = new ObjectItemDAO();



        stage.setTitle(String.format("Practicumopdracht OOP2 - %s", Main.studentNaam));
        stage.setWidth(640);
        stage.setHeight(480);

        View view = new ItemView();

        Scene scene = new Scene(view.getRoot());
        stage.setScene(scene);

        stage.show();

        switchController(new RetailerController());

    }


    public static void switchController(Controller controller) {
        stage.getScene().setRoot(controller.getView().getRoot());
    }
}
