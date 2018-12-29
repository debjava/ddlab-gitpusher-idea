/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
package com.ddlab.tornado.actions;

import com.ddlab.tornado.dialog.GistSnippetDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;

import static com.ddlab.tornado.common.CommonConstants.CODE_MENU_DESC;
import static com.ddlab.tornado.common.CommonConstants.CODE_MENU_TXT;
import static com.ddlab.tornado.common.PluginIcons.GIT_SNIPPET_ACTION_IMG;

/**
 * The type Gist snippet action.
 *
 * @author Debadatta Mishra
 */
public class GistSnippetAction extends AnAction {

  /** Instantiates a new Gist snippet action. */
  public GistSnippetAction() {
    super(CODE_MENU_TXT, CODE_MENU_DESC, GIT_SNIPPET_ACTION_IMG);
  }

  /**
   * Method used to perform an action upon click.
   *
   * @param event
   */
  @Override
  public void actionPerformed(AnActionEvent event) {
    Project project = event.getData(PlatformDataKeys.PROJECT);
    VirtualFile virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE);
    File filePath = new File(virtualFile.getPath());

    GistSnippetDialog gistSnippetDialog = new GistSnippetDialog(event.getProject(), filePath, true);
    gistSnippetDialog.show();
    if (gistSnippetDialog.getExitCode() != DialogWrapper.OK_EXIT_CODE) {
      gistSnippetDialog.getExitCode();
    }
  }

  /**
   * Method used enable disable menu based upon the condition.
   *
   * @param e
   */
  @Override
  public void update(AnActionEvent e) {
    VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
    File filePath = new File(virtualFile.getPath());

    if (!filePath.isDirectory()) e.getPresentation().setEnabledAndVisible(true);
    else e.getPresentation().setEnabledAndVisible(false);
  }
}
