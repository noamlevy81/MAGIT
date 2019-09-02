package body.right;

import app.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class CommitsListController {

    @FXML
    private ScrollPane m_ScroolPane;

    @FXML
    private AppController m_AppController;

    @FXML
    public void onCommitClick(MouseEvent mouseEvent) {

    }

    public ScrollPane getScroolPane() {return m_ScroolPane;}

    public void setAppController(AppController i_AppController) { this.m_AppController = i_AppController; }

    public void displayCommits(List i_ToDisplay)
    {
    }

    public void onLoadNewRepo() {
    }
}