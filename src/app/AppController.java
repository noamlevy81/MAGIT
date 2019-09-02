package app;

import bottom.CommitDataController;
import center.CommitsListController;
import gitEngine.Commit;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import top.TopController;
public class AppController {

    @FXML private VBox m_Top;
    @FXML private TopController m_TopController;

    @FXML private ScrollPane m_CommitsList;
    @FXML private CommitsListController m_CommitsListController;

    @FXML private AnchorPane m_Bottom;
    @FXML private CommitDataController m_BottomController;

    @FXML
    public void initialize(){
        if(m_CommitsListController != null &&
                m_TopController != null &&
                m_BottomController != null){
            m_TopController.setAppController(this);
            m_CommitsListController.setAppController(this);
            m_BottomController.setAppController(this);
        }
    }

    public void onLoadedRepoToSystem() {
        m_CommitsListController.onLoadNewRepo() ;
    }

    public void onSelectedCommitFromList(Commit commit){
        m_BottomController.ShowCommitDetails(commit);
    }
}
