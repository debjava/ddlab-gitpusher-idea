package com.ddlab.tornado.executors;

import com.ddlab.gitpusher.core.GitType;
import com.ddlab.gitpusher.core.IGitPusher;
import com.ddlab.gitpusher.core.UserAccount;
import com.ddlab.gitpusher.exception.GenericGitPushException;
import com.ddlab.tornado.dialog.GistSnippetDialog;
import com.ddlab.tornado.dialog.GitPushDialog;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import static com.ddlab.tornado.common.CommonConstants.GIT_ACCOUNTS;
import static com.ddlab.tornado.common.CommonConstants.NO_REPO_AVL_MSG;

public class RepoFetchTask extends Task.Backgroundable {
  private UserAccount userAccount;
  private String selectedGitType;

  private GitPushDialog gitPushDialog;

  public RepoFetchTask(
      @Nls(capitalization = Nls.Capitalization.Title) @NotNull String title,
      boolean canBeCancelled,
      GitPushDialog gitPushDialog) {
    super(gitPushDialog.getProject(), title, canBeCancelled);
    this.gitPushDialog = gitPushDialog;

    String userName = gitPushDialog.getUserNameTxt().getText();
    String password = new String(gitPushDialog.getPasswordField().getPassword());
    this.userAccount = new UserAccount(userName, password);
    this.selectedGitType = GIT_ACCOUNTS[gitPushDialog.getGitActCombo().getSelectedIndex()];
  }

  @Override
  public void run(@NotNull ProgressIndicator indicator) {
    indicator.setFraction(0.1);
    IGitPusher gitPusher = GitType.fromString(selectedGitType).getGitPusher(userAccount);
    try {
      String[] repos = gitPusher.getExistingRepos();
      TimeUnit.SECONDS.sleep(2);
      indicator.setFraction(0.8);
      if (repos.length != 0) {
        for (String repo : repos) this.gitPushDialog.getRepoCombo().addItem(repo);
      } else gitPushDialog.showInfoMessage(NO_REPO_AVL_MSG);
      indicator.setFraction(1.0);
    } catch (GenericGitPushException | InterruptedException e) {
      gitPushDialog.showErrorMessage(e.getMessage());
    }
  }
}
