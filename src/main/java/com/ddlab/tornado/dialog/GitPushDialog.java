/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
package com.ddlab.tornado.dialog;

import com.ddlab.tornado.common.UIUtil;
import com.ddlab.tornado.executors.RepoExecutor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static com.ddlab.tornado.common.CommonConstants.*;

/**
 * The type Git push dialog.
 *
 * @author Debadatta Mishra
 */
public class GitPushDialog extends DialogWrapper {

  private ComboBox<String> gitActCombo;
  private JBTextField userNameTxt;
  private JBPasswordField passwordField;
  private JButton repoBtnTestAndShow;
  private ComboBox repoCombo;
  private JTextArea readMeTxtArea;
  private UICommonDesigner uiDesinger;
  private Project project;
  private File selectedRepo;

  /**
   * Instantiates a new Git push dialog.
   *
   * @param project the project
   * @param selectedRepo the selected repo
   * @param canBeParent the can be parent
   */
  public GitPushDialog(@Nullable Project project, File selectedRepo, boolean canBeParent) {
    super(project, canBeParent);
    setTitle(DLG_TITLE_TXT);
    super.setSize(300, 300);
    this.project = project;
    this.selectedRepo = selectedRepo;
    uiDesinger = new UICommonDesigner();
    init();
  }

  /**
   * Creates Panel at the center
   *
   * @return
   */
  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    JPanel contentPanel = new JPanel(UIUtil.getPanelLayout());
    createGitTypeCombo(contentPanel);
    createUserNameTxt(contentPanel);
    createPasswordTxt(contentPanel);
    createPopulateCombo(contentPanel);
    createDescTxt(contentPanel);
    contentPanel.setPreferredSize(new Dimension(500, 200));

    return contentPanel;
  }

  /**
   * Create Git account selection Combo
   *
   * @param contentPanel
   */
  private void createGitTypeCombo(JPanel contentPanel) {
    uiDesinger.createGitTypeComboLbl(contentPanel);
    gitActCombo = uiDesinger.getGitTypeCombo(contentPanel);
  }

  /**
   * Creates a text field user name
   *
   * @param contentPanel
   */
  private void createUserNameTxt(JPanel contentPanel) {
    uiDesinger.createUserNameLbl(contentPanel);
    userNameTxt = uiDesinger.getUserNameText(contentPanel);
  }

  /**
   * Creates a text field for password
   *
   * @param contentPanel
   */
  private void createPasswordTxt(JPanel contentPanel) {
    uiDesinger.createPasswordLbl(contentPanel);
    passwordField = uiDesinger.getPasswordtext(contentPanel);
  }

  /**
   * Create a combo box to populate the existing repos
   *
   * @param contentPanel
   */
  private void createPopulateCombo(JPanel contentPanel) {
    repoBtnTestAndShow = uiDesinger.getTestAndShowBtn(contentPanel, REPO_BTN_TXT);
    repoCombo = uiDesinger.getPopulateCombo(contentPanel);
    addTestBtnActionListener();
  }

  /** Listener for test button */
  private void addTestBtnActionListener() {
    repoBtnTestAndShow.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (isCredentialValid()) {
              populateRepos();
            }
          }
        });
  }

  /** Method to populate the repos */
  private void populateRepos() {
    repoCombo.removeAllItems();
    new RepoExecutor(this).fetchRepos();
  }

  /**
   * Check whether credentials are right
   *
   * @return
   */
  private boolean isCredentialValid() {
    boolean validFlag = false;
    String userName = userNameTxt.getText();
    String password = new String(passwordField.getPassword());
    if (UIUtil.isBlankOrNull(userName)) {
      setErrorText(UNAME_NOT_EMPTY_TXT, userNameTxt);
    } else if (UIUtil.isBlankOrNull(password)) {
      setErrorText(PWD_NOT_EMPTY_TXT, passwordField);
    } else {
      setErrorText(null);
      validFlag = true;
    }
    return validFlag;
  }

  /**
   * Create a text area for description
   *
   * @param contentPanel
   */
  private void createDescTxt(JPanel contentPanel) {
    uiDesinger.createDescTxtLbl(contentPanel, READ_ME_INFO_TXT);
    readMeTxtArea = uiDesinger.getDescTxtArea(contentPanel);
  }

  /**
   * Method to validate the inputs
   *
   * @return
   */
  @Nullable
  @Override
  protected ValidationInfo doValidate() {
    ValidationInfo validationInfo = null;
    String userName = userNameTxt.getText();
    String password = new String(passwordField.getPassword());
    String descriptionTxt = readMeTxtArea.getText();

    if (userName == null || userName.trim().length() == 0)
      validationInfo = new ValidationInfo(UNAME_NOT_EMPTY_TXT, userNameTxt);
    else if (password == null || password.trim().length() == 0)
      validationInfo = new ValidationInfo(PWD_NOT_EMPTY_TXT, passwordField);
    return validationInfo;
  }

  /** Method to perform action on pressing Ok button */
  @Override
  protected void doOKAction() {
    close(1);
    new RepoExecutor(this).createRepo();
  }

  /**
   * Show info message.
   *
   * @param infoMsg the info msg
   */
  public void showInfoMessage(String infoMsg) {
    SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            UIUtil.showInfoBalloon(project, infoMsg, true);
          }
        });
  }

  /**
   * Show error message.
   *
   * @param errorMsg the error msg
   */
  public void showErrorMessage(String errorMsg) {
    System.out.println("Error Message : "+errorMsg);
    SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            setErrorText(errorMsg);
            UIUtil.notifyError(errorMsg);
            UIUtil.showErrorBalloon(project, errorMsg);
          }
        });
  }

  /**
   * Gets git act combo.
   *
   * @return the git act combo
   */
  public ComboBox<String> getGitActCombo() {
    return gitActCombo;
  }

  /**
   * Gets user name txt.
   *
   * @return the user name txt
   */
  public JBTextField getUserNameTxt() {
    return userNameTxt;
  }

  /**
   * Gets password field.
   *
   * @return the password field
   */
  public JBPasswordField getPasswordField() {
    return passwordField;
  }

  /**
   * Gets repo btn test and show.
   *
   * @return the repo btn test and show
   */
  public JButton getRepoBtnTestAndShow() {
    return repoBtnTestAndShow;
  }

  /**
   * Gets repo combo.
   *
   * @return the repo combo
   */
  public ComboBox getRepoCombo() {
    return repoCombo;
  }

  /**
   * Gets read me txt area.
   *
   * @return the read me txt area
   */
  public JTextArea getReadMeTxtArea() {
    return readMeTxtArea;
  }

  /**
   * Gets project.
   *
   * @return the project
   */
  public Project getProject() {
    return project;
  }

  /**
   * Gets selected repo.
   *
   * @return the selected repo
   */
  public File getSelectedRepo() {
    return selectedRepo;
  }
}
