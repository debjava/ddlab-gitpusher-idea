package com.ddlab.tornado.executors;

import com.ddlab.tornado.dialog.GistSnippetDialog;
import com.intellij.openapi.progress.ProgressManager;

import static com.ddlab.tornado.common.CommonConstants.CREATE_GIST_MSG;
import static com.ddlab.tornado.common.CommonConstants.FETCH_GIST_MSG;

public class SnippetExecutor {

  private GistSnippetDialog snippetDialog;

  public SnippetExecutor(GistSnippetDialog snippetDialog) {
    this.snippetDialog = snippetDialog;
  }

  public void fetchSnippets() {
    GistSnippetFetchTask gistSnippetFetchTask =
        new GistSnippetFetchTask(FETCH_GIST_MSG, true, snippetDialog);
    ProgressManager.getInstance().run(gistSnippetFetchTask);
  }

  public void createGistSnippet() {
    GistSnippetCreateTask gistSnippetCreateTask =
        new GistSnippetCreateTask(CREATE_GIST_MSG, true, snippetDialog);
    ProgressManager.getInstance().run(gistSnippetCreateTask);
  }
}
