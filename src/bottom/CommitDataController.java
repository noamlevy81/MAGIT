package bottom;

import app.AppController;
import gitEngine.Commit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class CommitDataController {


    @FXML
    private AppController m_AppController;
    @FXML
    private Tab dataTab;

    @FXML
    private Label AuthorToAddLabel;

    @FXML
    private Label DateToAddLabel;

    @FXML
    private Label Sha1ToAddLabel;

    @FXML
    private Label parentSha1ToAddLabel;

    @FXML
    private Label messageToAddLabel;


    @FXML
    public void setAppController(AppController i_AppController) { m_AppController = i_AppController; }

    public void ShowCommitDetails(Commit commit) {
        AuthorToAddLabel.setText(commit.getCommitAuthor());
        DateToAddLabel.setText(commit.getCommitDate());
        String parentSHA1 = commit.getParent().get(0);
         parentSHA1 += commit.getParent().size() > 1 ?
                 ", " + commit.getParent().get(1) : "";

         parentSHA1 = parentSHA1.equals("null") ? "none" : parentSHA1;
        parentSha1ToAddLabel.setText(parentSHA1);
        messageToAddLabel.setText(commit.getMessage());
    }
}
