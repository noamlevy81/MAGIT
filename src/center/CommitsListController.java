package center;

import app.AppController;
import gitEngine.AppEngine;
import gitEngine.Commit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.Map;

public class CommitsListController {


    @FXML
    private AppController m_AppController;
    @FXML
    private ScrollPane m_ScroolPane;


    @FXML
    private TableView<Commit> m_CommitsTableView;

    @FXML
    private TableColumn<Commit,String> m_TableColumnDate;

    @FXML
    private TableColumn<Commit,String> m_TableColumnAuthor;

    @FXML
    private TableColumn<Commit,String> m_TableColumnMessage;

    //@FXML
    //private TableColumn<String,Commit> m_TableColumnSha1;


    @FXML
    public void initialize(){

        m_TableColumnMessage.setCellValueFactory(new PropertyValueFactory<>("Message"));

        m_TableColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("CommitAuthor"));

        m_TableColumnDate.setCellValueFactory(new PropertyValueFactory<>("CommitDate"));

//        m_TableColumnMessage.setCellValueFactory(new PropertyValueFactory<>("m_Message"));
    }


    public void setAppController(AppController i_AppController) { this.m_AppController = i_AppController; }

    public void onLoadNewRepo() {
        m_CommitsTableView.setItems(getCommits());
    }

    private ObservableList<Commit> getCommits(){
        Map<String, Commit> commits = AppEngine.getInstance().getRepository().getMagit().getCommits();
        ObservableList<Commit> commitsForUI = FXCollections.observableArrayList();
        commitsForUI.addAll(commits.values());
        return commitsForUI;
    }


    public void onCommitClick(MouseEvent mouseEvent) {
        m_AppController.onSelectedCommitFromList(m_CommitsTableView.getSelectionModel().getSelectedItem());
    }
}

