package top;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessageBox {

    private static final int MIN_WIDTH = 250;
    private static final int SPACING_CHILDRENS = 10;

    public static void display(String i_Title, String i_Message){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(i_Title);
        stage.setMinWidth(MIN_WIDTH);
        Label label = new Label(i_Message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(SPACING_CHILDRENS);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, closeButton);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
