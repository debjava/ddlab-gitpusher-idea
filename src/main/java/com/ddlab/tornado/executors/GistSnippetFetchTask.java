/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
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

import java.util.concurrent.TimeUnit;

import static com.ddlab.tornado.common.CommonConstants.GIT_ACCOUNTS;
import static com.ddlab.tornado.common.CommonConstants.NO_GIST_AVL_MSG;

/**
 * The type Gist snippet fetch task.
 *
 * @author Debadatta Mishra
 */
public class GistSnippetFetchTask extends Task.Backgroundable {
  /** UserAccount object */
  private UserAccount userAccount;
  /** Combox box selected git account type */
  private String selectedGitType;
  /** Gist Snippet creation dialog */
  private GistSnippetDialog snippetDialog;

  /**
   * Instantiates a new Gist snippet fetch task.
   *
   * @param title the title
   * @param canBeCancelled the can be cancelled
   * @param snippetDialog the snippet dialog
   */
  public GistSnippetFetchTask(
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

  /**
   * Thread's default run method to process operations in background and provides a basic progress
   * info.
   *
   * @param indicator
   */
  @Override
  public void run(@NotNull ProgressIndicator indicator) {
    indicator.setFraction(0.1);
    IGitPusher gitPusher = GitType.fromString(selectedGitType).getGitPusher(userAccount);
    try {
      String[] snippets = gitPusher.getExistingSnippets();
      TimeUnit.SECONDS.sleep(2);
      indicator.setFraction(0.8);
      if (snippets.length != 0) {
        for (String snippet : snippets) this.snippetDialog.getSnippetCombo().addItem(snippet);
      } else snippetDialog.showInfoMessage(NO_GIST_AVL_MSG);
      indicator.setFraction(1.0);
    } catch (GenericGitPushException | InterruptedException e) {
      snippetDialog.showErrorMessage(e.getMessage());
    }
  }
}
