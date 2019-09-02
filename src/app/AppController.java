package app;

import body.right.CommitsListController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import top.TopController;

public class AppController {

    @FXML private VBox m_Top;
    @FXML private TopController m_TopController;

    @FXML private ScrollPane m_CommitsList;
    @FXML private CommitsListController m_CommitsListConroller;


    @FXML
    public void initialize(){
        if(m_CommitsListConroller != null && m_TopController != null){
            m_TopController.setAppController(this);
            m_CommitsListConroller.setAppController(this);

        }
    }

    public void onLoadRepoToSystem() {
        m_CommitsListConroller.onLoadNewRepo() ;
    }
}
