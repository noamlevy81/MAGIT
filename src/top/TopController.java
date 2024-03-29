package top;

import app.AppController;
import gitEngine.AppEngine;
import gitEngine.Branch;
import gitEngine.FileWalkResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.nio.file.Path;

public class TopController {

    @FXML
    private AppController m_AppController;

    @FXML
    private MenuItem NewMenuItem;

    @FXML
    private MenuItem loadFromXMLMenuItem;

    @FXML
    private MenuItem changeUserNameMenuItem;

    @FXML
    private MenuItem changeRepoMenuItem;

    @FXML
    private MenuItem showCommitMenuItem;

    @FXML
    private MenuItem openChangesMenuItem;

    @FXML
    private MenuItem newCommitMenuItem;

    @FXML
    private MenuItem showBranchesMenuItem;

    @FXML
    private MenuItem newBranchMenuItem;

    @FXML
    private MenuItem deleteBranchMenuItem;

    @FXML
    private MenuItem checkoutBranchMenuItem;

    @FXML
    private MenuItem resetBranchMenuItem;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    public void setAppController(AppController i_AppController) { this.m_AppController = i_AppController; }

    // =============== Repository menu methods ==========================

    @FXML
    void onChageUserName(ActionEvent event) {
        String newName = GetDataBox.display("Get user name", "please enter new user name: ");
        AppEngine.getInstance().setActiveUserName(newName);
    }

    @FXML
    void onChangeRepo(ActionEvent event) {
        String pathToRepo = GetDataBox.display("Change Repo", "Please enter repository path:");
        if (AppEngine.getInstance().checkIfRepository(pathToRepo)) {
            try {
                AppEngine.getInstance().changeRepositories(pathToRepo);
            } catch (Exception e) {
                MessageBox.display("Error", e.getMessage());
            }
        } else {
            MessageBox.display("Error", pathToRepo + " is not a repository valid path");
        }

        m_AppController.onLoadedRepoToSystem();
    }


    @FXML
    void onLoadFromXML(ActionEvent event) {
        String pathToXML = GetDataBox.display("XML Path", "Please enter XML file path:");
        AppEngine engine = AppEngine.getInstance();
        try {
            switch (engine.getXmlOption(pathToXML)) {
                case NODIRECTORY:
                    engine.createNewRepositoryFromXmlFile(pathToXML, false);
                    m_AppController.onLoadedRepoToSystem();
                    break;
                case REPOSITORY:
                    manageExistingRepo(pathToXML);
                    m_AppController.onLoadedRepoToSystem();
                    break;
                case NOREPOSITORY:
                    MessageBox.display("Error", "There is an exisiting folder in" + System.lineSeparator() + pathToXML);
            }
        } catch (Exception e) {
            MessageBox.display("Error", e.getMessage());
        }
    }

    // helper method to the one above
    private void manageExistingRepo(String i_path) throws Exception {
        String userChoice = GetDataBox.display("Existing repo",
                "there is repository exist in this path would you like to delete and create your own ?" +
                        System.lineSeparator() + "enter 1 to delete the repo, and anything else to see repo history");
        String toShow = "";

        if (userChoice.equals("1")) {
            AppEngine.getInstance().createNewRepositoryFromXmlFile(i_path, true);
        } else if (userChoice.equals("2")) {
            for (String s : AppEngine.getInstance().getAllFilesPointsFromLastCommit()) {
                toShow += s + System.lineSeparator();
            }
        } else {
            toShow = "please enter 1 or 2";
        }
        if (!toShow.equals("")) {
            MessageBox.display("", toShow);
        }
    }

    @FXML
    void onNewRepo(ActionEvent event) {
//        String input = GetDataBox.display("repo", "please enter repository name and path in seperated lines: ");
//        String[] res = parse(input);

        try {
            AppEngine.getInstance().createNewRepository("C:\\Users\\noamlevy\\Desktop\\מכללה\\שנה ב\\סימסטר קיץ\\Java\\javaXML","noam");
        } catch (Exception e) {
            MessageBox.display("Error", e.getMessage());
        }

    }

    String[] parse(String i_input) {
        String[] res = new String[2];
        res[0] = i_input.substring(0, i_input.lastIndexOf(System.lineSeparator()));
        res[1] = i_input.substring(i_input.lastIndexOf(System.lineSeparator()));

        return res;
    }

    // =============== Branch menu methods ==========================
    @FXML
    void onCheckoutBranch(ActionEvent event) {
        String branchName = tryGetNameFromUser();
        if (branchName.equals(""))
            return;

        try {
            if (checkIfThereAreOpeningChangesAndGetUserDecision())
                return;
            else {
                AppEngine.getInstance().checkOutBranch(branchName);
                String toShow = "";
                for (String s : AppEngine.getInstance().getRepository().getLastState().getLastCommitInformation().getFilePathToSha1().values()) {
                    toShow += s + System.lineSeparator();
                }

                MessageBox.display("history", toShow);
            }
        } catch (Exception e) {
            MessageBox.display("Error", e.getMessage());
        }
    }

    private String tryGetNameFromUser() {
        String branchName = GetDataBox.display("Branch Name", "Please enter branch name to checkout:");
        if (!AppEngine.getInstance().checkBranchNameIsExist(branchName)) {
            MessageBox.display("Not Found", "brunch name doesnt exist");
            return "";
        }

        return branchName;
    }

    // helper method to the one above
    private boolean checkIfThereAreOpeningChangesAndGetUserDecision() throws Exception {
        if (!AppEngine.getInstance().getRepository().isWcClean()) {
            String s = GetDataBox.display("", "There are unsaved changes, would you like to commit first?(yes/no)");
            if (s.equals("yes")) {
                AppEngine.getInstance().createNewCommit(GetDataBox.display("Commit Message", "Enter commit message:"));
                return true;
            }
        }
        return false;
    }

    @FXML
    void onDeleteBranch(ActionEvent event) {
        String branchName = tryGetNameFromUser();
        if (branchName.equals("")) {
            return;
        }
        try {
            AppEngine.getInstance().deleteExistingBranch(branchName);
        } catch (Exception e) {
            MessageBox.display("Error", e.getMessage());
        }
    }

    @FXML
    void onNewBranch(ActionEvent event) {
        String branchName = GetDataBox.display("New Branch", "enter branch name:");
        try {
            if (AppEngine.getInstance().checkBranchNameIsExist(branchName)) {
                MessageBox.display("", "branch name already exist");
            } else {
                if (branchName != null) {
                    AppEngine.getInstance().createNewBranch(branchName);
                }
            }
        } catch (Exception e) {
            MessageBox.display("Error", e.getMessage());
        }
    }

    @FXML
    void onResetBranch(ActionEvent event) {

    }

    @FXML
    void onShowBranches(ActionEvent event) {
        String toShow = "";
        try {
            for (Branch branch : AppEngine.getInstance().getAllBranches()) {
                toShow += branch.getBracnhName() + System.lineSeparator();
            }
        } catch (Exception e) {
            toShow = e.getMessage();
        }

        MessageBox.display("Branches", toShow);
    }

// =============== Commit menu methods ==========================

    @FXML
    void onCommit(ActionEvent event) {
        String message = GetDataBox.display("Commit Message", "Enter commit message:");
        try {
            AppEngine.getInstance().createNewCommit(message);
        } catch (Exception e) {
            MessageBox.display("Error", e.getMessage());
        }
    }

    @FXML
    void onShowCommit(ActionEvent event) {
        String listOfFiles = "";
        for (String file : AppEngine.getInstance().getAllFilesPointsFromLastCommit()) {
            listOfFiles += file + System.lineSeparator();
        }

        MessageBox.display("Commit History", listOfFiles);
    }

    @FXML
    void onShowOpenChanges(ActionEvent event) {
        try {
            String openChanges = "";
            FileWalkResult fwr = AppEngine.getInstance().getStatus();
            openChanges += "New Files:" + System.lineSeparator();
            for (Path file : fwr.getCommitDelta().getNewFiles()) {
                openChanges += file + System.lineSeparator();
            }
            openChanges += "Modified Files: " + System.lineSeparator();
            for (Path file : fwr.getCommitDelta().getModifiedFiles()) {
                openChanges += file + System.lineSeparator();
            }
            openChanges += "Deleted Files: " + System.lineSeparator();
            for (Path file : fwr.getCommitDelta().getDeletedFiles()) {
                openChanges += file + System.lineSeparator();
            }

            MessageBox.display("Status", openChanges);
        } catch (Exception e) {
            MessageBox.display("Error", e.getMessage());
        }
    }
}