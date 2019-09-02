package bottom;

import app.AppController;
import gitEngine.Commit;
import gitEngine.FileUtilities;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import top.MessageBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommitDataController {

    Map<TreeItem<String>, String> m_TreeItemToSHA1;

    @FXML
    private AppController m_AppController;
    @FXML
    private Tab dataTab;

    @FXML
    private TreeView<String> commitTreeView;

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
    private TextArea textAreaFileContent;

    @FXML
    public void setAppController(AppController i_AppController) { m_AppController = i_AppController; }

    @FXML
    public void initialize(){
        m_TreeItemToSHA1 = new HashMap<>();

    }

    private void showCommitDetails(Commit commit) {
        AuthorToAddLabel.setText(commit.getCommitAuthor());
        DateToAddLabel.setText(commit.getCommitDate());
        String parentSHA1 = commit.getParent().get(0);
         parentSHA1 += commit.getParent().size() > 1 ?
                 ", " + commit.getParent().get(1) : "";

         parentSHA1 = parentSHA1.equals("null") ? "none" : parentSHA1;
        parentSha1ToAddLabel.setText(parentSHA1);
        messageToAddLabel.setText(commit.getMessage());
    }

    public void LoadCommitToContainer(Commit commit) {
        showCommitDetails(commit);
        loadCommitTree(commit);
    }

    private void loadCommitTree(Commit commit) {
        textAreaFileContent.clear();
        TreeItem<String> root = new TreeItem<>();
        commitTreeView.setRoot(root);
        commitTreeView.showRootProperty().setValue(false);
        try {
            createTree(root, commit.getRootFolderSha1());
        } catch (Exception e){
            MessageBox.display("Error", e.getMessage());
        }
    }

    private void createTree(TreeItem<String> i_Parent, String i_parentSHA1) throws IOException {
        final int fileNamePlace = 0;
        final int filetypePlace = 2;
        final int fileSHAPlace = 1;

        String content = FileUtilities.getStringFromFolderZip(i_parentSHA1, "");

        for(String line : content.split(System.lineSeparator())){
            String fileName = line.split(",")[fileNamePlace];
            String fileType = line.split(",")[filetypePlace];
            String fileSha1 = line.split(",")[fileSHAPlace];

            TreeItem<String> item = new TreeItem<>(fileName);
            i_Parent.getChildren().add(item);

            if(fileType.equals("folder")){
                createTree(item, line.split(",")[fileSHAPlace]);
            }
            else{
                String fileContent = FileUtilities.getStringFromFolderZip(fileSha1,fileName);
                m_TreeItemToSHA1.put(item,fileContent);

            }
        }
    }

    public void onFileSelected(MouseEvent mouseEvent) {
        TreeItem currentTreeItem = commitTreeView.getFocusModel().getFocusedItem();
        if(m_TreeItemToSHA1.containsKey(currentTreeItem)){
            String fileContent = m_TreeItemToSHA1.get(currentTreeItem);
            textAreaFileContent.setText(fileContent);
        }
        else{
            textAreaFileContent.clear();
        }
    }
}
