package top;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GetDataBox {
    private static final int MIN_WIDTH = 250;
    private static final int SPACING_CHILDRENS = 10;
    private static String s_returnInput = "";

    public static String display(String i_Title, String i_Message){
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(i_Title);
        stage.setMinWidth(MIN_WIDTH);
        Label label = new Label(i_Message);
        TextArea textArea = new TextArea();
        Button closeButton = new Button("Close");

        closeButton.setOnAction(e -> {
            s_returnInput = textArea.getText();
            if(!s_returnInput.equals(""))
                stage.close();
        });

        VBox layout = new VBox(SPACING_CHILDRENS);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, textArea, closeButton);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();

        return s_returnInput;
    }
}
