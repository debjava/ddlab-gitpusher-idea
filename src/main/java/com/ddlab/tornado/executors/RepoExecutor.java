package com.ddlab.tornado.executors;

import com.ddlab.tornado.dialog.GitPushDialog;
import com.intellij.openapi.progress.ProgressManager;

import static com.ddlab.tornado.common.CommonConstants.CREATE_REPO_MSG;
import static com.ddlab.tornado.common.CommonConstants.FETCH_REPO_MSG;

public class RepoExecutor {

  private GitPushDialog gitPushDialog;

  public RepoExecutor(GitPushDialog gitPushDialog) {
    this.gitPushDialog = gitPushDialog;
  }

  public void fetchRepos() {
    RepoFetchTask repoFetchTask = new RepoFetchTask(FETCH_REPO_MSG, true, gitPushDialog);
    ProgressManager.getInstance().run(repoFetchTask);
  }

  public void createRepo() {
    RepoCreateTask repoCreateTask = new RepoCreateTask(CREATE_REPO_MSG, true, gitPushDialog);
    ProgressManager.getInstance().run(repoCreateTask);
  }
}
