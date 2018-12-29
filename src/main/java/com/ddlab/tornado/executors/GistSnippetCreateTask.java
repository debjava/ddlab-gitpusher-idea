package com.ddlab.tornado.executors;

import com.ddlab.gitpusher.core.GitType;
import com.ddlab.gitpusher.core.IGitPusher;
import com.ddlab.gitpusher.core.UserAccount;
import com.ddlab.gitpusher.exception.GenericGitPushException;
import com.ddlab.tornado.dialog.GistSnippetDialog;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.ddlab.tornado.common.CommonConstants.GIT_ACCOUNTS;
import static com.ddlab.tornado.common.CommonConstants.SUCCESSFUL_GIST_CREATE_MSG;

public class GistSnippetCreateTask extends Task.Backgroundable {
  private UserAccount userAccount;
  private String selectedGitType;
  private GistSnippetDialog snippetDialog;

  public GistSnippetCreateTask(
      @Nls(capitalization = Nls.Capitalization.Title) @NotNull String title,
      boolean canBeCancelled,
      GistSnippetDialog snippetDialog) {
    super(snippetDialog.getProject(), title, canBeCancelled);
    this.snippetDialog = snippetDialog;

    String userName = snippetDialog.getUserNameTxt().getText();
    String password = new String(snippetDialog.getPasswordField().getPassword());
    this.userAccount = new UserAccount(userName, password);
    this.selectedGitType = GIT_ACCOUNTS[snippetDialog.getGitActCombo().getSelectedIndex()];
  }

  @Override
  public void run(@NotNull ProgressIndicator indicator) {
    indicator.setFraction(0.1);
    File selectedFile = snippetDialog.getSelectedFile();
    String description = snippetDialog.getDescTxtArea().getText();
    IGitPusher gitPusher = GitType.fromString(selectedGitType).getGitPusher(userAccount);
    try {
      indicator.setFraction(0.5);
      TimeUnit.SECONDS.sleep(2);
      if (indicator.isCanceled()) return;
      gitPusher.pushSnippetDirectly(selectedFile, description);
      TimeUnit.SECONDS.sleep(1);
      indicator.setFraction(0.8);
      indicator.setFraction(1.0);
      snippetDialog.showInfoMessage(SUCCESSFUL_GIST_CREATE_MSG);
    } catch (GenericGitPushException | InterruptedException e) {
      snippetDialog.showErrorMessage(e.getMessage());
    }
  }
}
