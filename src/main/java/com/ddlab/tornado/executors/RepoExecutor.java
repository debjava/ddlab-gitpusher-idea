/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
package com.ddlab.tornado.executors;

import com.ddlab.tornado.dialog.GitPushDialog;
import com.intellij.openapi.progress.ProgressManager;

import static com.ddlab.tornado.common.CommonConstants.CREATE_REPO_MSG;
import static com.ddlab.tornado.common.CommonConstants.FETCH_REPO_MSG;

/**
 * The type Repo executor which provides the core functionality for the overall operations.
 *
 * @author Debadatta Mishra
 */
public class RepoExecutor {

  /** Git Push core dialog */
  private GitPushDialog gitPushDialog;

  /**
   * Instantiates a new Repo executor.
   *
   * @param gitPushDialog the git push dialog
   */
  public RepoExecutor(GitPushDialog gitPushDialog) {
    this.gitPushDialog = gitPushDialog;
  }

  /** Fetch repos. */
  public void fetchRepos() {
    RepoFetchTask repoFetchTask = new RepoFetchTask(FETCH_REPO_MSG, true, gitPushDialog);
    ProgressManager.getInstance().run(repoFetchTask);
  }

  /** Create repo. */
  public void createRepo() {
    RepoCreateTask repoCreateTask = new RepoCreateTask(CREATE_REPO_MSG, true, gitPushDialog);
    ProgressManager.getInstance().run(repoCreateTask);
  }
}
