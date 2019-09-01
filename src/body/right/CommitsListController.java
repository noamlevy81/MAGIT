package body.right;

import app.AppController;
import gitEngine.AppEngine;
import gitEngine.Commit;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.Map;

public class CommitsListController {

    @FXML
    private ListView<Commit> commitsListView;

    @FXML
    private AppController m_AppController;

    @FXML
    public void onCommitClick(MouseEvent mouseEvent) {

    }

    public void setAppController(AppController i_AppController) { this.m_AppController = i_AppController; }

    public void displayCommits(ObservableList<Commit> i_ToDisplay)
    {
        commitsListView.setItems(i_ToDisplay);
    }

    public void onLoadNewRepo() {
        Map<String, Commit> toShow = AppEngine.getInstance().getRepository().getMagit().getCommits();
        commitsListView.getItems().addAll(toShow.values());
    }
}