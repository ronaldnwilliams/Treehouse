package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));

        // Use fxml file instead to do all this
       /* Group root = new Group();
        Text txt = new Text("Sup?");
        txt.setFont(new Font("Papyrus", 80));
        Label label = new Label("Name:");
        TextField nameFld = new TextField();
        GridPane grid = new GridPane();
        Button btn = new Button();
//        grid.setGridLinesVisible(true);
        grid.add(label, 0,0);
        grid.add(nameFld, 1,0);
        grid.add(btn, 1,1);
        grid.setHgap(20);
        btn.setText("Say Sup!");
        btn.setOnAction(evt -> System.out.printf("Sup %s!%n",
                nameFld.getText()));
        txt.setY(50);
        VBox box = new VBox();
        box.getChildren().addAll(txt, grid);
        root.getChildren().add(box);*/

        primaryStage.setTitle("Sup");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
