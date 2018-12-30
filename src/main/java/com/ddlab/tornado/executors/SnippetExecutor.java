/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
package com.ddlab.tornado.executors;

import com.ddlab.tornado.dialog.GistSnippetDialog;
import com.intellij.openapi.progress.ProgressManager;

import static com.ddlab.tornado.common.CommonConstants.CREATE_GIST_MSG;
import static com.ddlab.tornado.common.CommonConstants.FETCH_GIST_MSG;

/**
 * The type Snippet executor.
 *
 * @author Debadatta Mishra
 */
public class SnippetExecutor {

  /** Snippet creation dialog */
  private GistSnippetDialog snippetDialog;

  /**
   * Instantiates a new Snippet executor.
   *
   * @param snippetDialog the snippet dialog
   */
  public SnippetExecutor(GistSnippetDialog snippetDialog) {
    this.snippetDialog = snippetDialog;
  }

  /** Fetch snippets. */
  public void fetchSnippets() {
    GistSnippetFetchTask gistSnippetFetchTask =
        new GistSnippetFetchTask(FETCH_GIST_MSG, true, snippetDialog);
    ProgressManager.getInstance().run(gistSnippetFetchTask);
  }

  /** Create gist snippet. */
  public void createGistSnippet() {
    GistSnippetCreateTask gistSnippetCreateTask =
        new GistSnippetCreateTask(CREATE_GIST_MSG, true, snippetDialog);
    ProgressManager.getInstance().run(gistSnippetCreateTask);
  }
}
