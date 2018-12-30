/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
package com.ddlab.tornado.executors;

import com.ddlab.gitpusher.core.GitType;
import com.ddlab.gitpusher.core.IGitPusher;
import com.ddlab.gitpusher.core.UserAccount;
import com.ddlab.gitpusher.exception.GenericGitPushException;
import com.ddlab.tornado.common.UIUtil;
import com.ddlab.tornado.dialog.GitPushDialog;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.ddlab.tornado.common.CommonConstants.*;

/**
 * The type Repo create task.
 *
 * @author Debadatta Mishra
 */
public class RepoCreateTask extends Task.Backgroundable {
  /** UserAccount object */
  private UserAccount userAccount;
  /** Combox box selected git account type */
  private String selectedGitType;
  /** Gist Snippet creation dialog */
  private GitPushDialog gitPushDialog;

  /**
   * Instantiates a new Repo create task.
   *
   * @param title the title
   * @param canBeCancelled the can be cancelled
   * @param gitPushDialog the git push dialog
   */
  public RepoCreateTask(
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

  /**
   * Thread's default run method to process operations in background and provides a basic progress
   * info.
   *
   * @param indicator
   */
  @Override
  public void run(@NotNull ProgressIndicator indicator) {
    indicator.setFraction(0.1);
    File selectedFile = gitPushDialog.getSelectedRepo();
    String readMetxt = gitPushDialog.getReadMeTxtArea().getText();
    IGitPusher gitPusher = GitType.fromString(selectedGitType).getGitPusher(userAccount);
    try {
      indicator.setText2(GEN_GITIGNORE_MSG);
      UIUtil.generateGitIgnoreFile(selectedFile);
      indicator.setFraction(0.5);
      TimeUnit.SECONDS.sleep(1);
      indicator.setText2(GEN_README_MSG);
      UIUtil.generateReadMeFile(selectedFile, readMetxt);
      TimeUnit.SECONDS.sleep(1);
      indicator.setFraction(0.8);
      indicator.setText2(PUSH_MSG);
      if (indicator.isCanceled()) return;
      gitPusher.pushCodeDirectly(selectedFile);
      indicator.setFraction(0.8);
      TimeUnit.SECONDS.sleep(1);
      indicator.setText2(COMPLETE_OPRNS_MSG);
      indicator.setFraction(1.0);
      gitPushDialog.showInfoMessage(SUCCESSFUL_REPO_CREATE_MSG);
    } catch (GenericGitPushException | InterruptedException e) {
      gitPushDialog.showErrorMessage(e.getMessage());
    }
  }
}
