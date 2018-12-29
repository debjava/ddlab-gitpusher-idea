/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
package com.ddlab.tornado.dialog;

import com.ddlab.tornado.common.UIUtil;
import com.ddlab.tornado.executors.SnippetExecutor;
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
 * The type Gist snippet dialog.
 *
 * @author Debadatta Mishra
 */
public class GistSnippetDialog extends DialogWrapper {
  private ComboBox<String> gitActCombo;
  private JBTextField userNameTxt;
  private JBPasswordField passwordField;
  private JButton btnTestAndShow;
  private ComboBox<String> snippetCombo;
  private JTextArea descTxtArea;
  private Project project;
  private UICommonDesigner uiDesinger;
  private File selectedFile;

  /**
   * Instantiates a new Gist snippet dialog.
   *
   * @param project the project
   * @param selectedFilePath the selected file path
   * @param canBeParent the can be parent
   */
  public GistSnippetDialog(@Nullable Project project, File selectedFilePath, boolean canBeParent) {
    super(project, canBeParent);
    setTitle(DLG_TITLE_TXT);
    this.project = project;
    this.selectedFile = selectedFilePath;
    uiDesinger = new UICommonDesigner();
    init();
  }

  /**
   * Creates a panel in the center
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
   * Creates combo box for Git Account selection
   *
   * @param contentPanel
   */
  private void createGitTypeCombo(JPanel contentPanel) {
    uiDesinger.createGitTypeComboLbl(contentPanel);
    gitActCombo = uiDesinger.getGitTypeCombo(contentPanel);
  }

  /**
   * Creates a text field for user name
   *
   * @param contentPanel
   */
  private void createUserNameTxt(JPanel contentPanel) {
    uiDesinger.createUserNameLbl(contentPanel);
    userNameTxt = uiDesinger.getUserNameText(contentPanel);
  }

  /**
   * Creates a text field for Password
   *
   * @param contentPanel
   */
  private void createPasswordTxt(JPanel contentPanel) {
    uiDesinger.createPasswordLbl(contentPanel);
    passwordField = uiDesinger.getPasswordtext(contentPanel);
  }

  /**
   * Creates a combo box for populating the snippets
   *
   * @param contentPanel
   */
  private void createPopulateCombo(JPanel contentPanel) {
    btnTestAndShow = uiDesinger.getTestAndShowBtn(contentPanel, GIST_BTN_TXT);
    snippetCombo = uiDesinger.getPopulateCombo(contentPanel);
    addTestBtnActionListener();
  }

  /**
   * Creates a text area for description
   *
   * @param contentPanel
   */
  private void createDescTxt(JPanel contentPanel) {
    uiDesinger.createDescTxtLbl(contentPanel, GIST_LBL_TXT);
    descTxtArea = uiDesinger.getDescTxtArea(contentPanel);
  }

  /**
   * Method to perform validation
   *
   * @return
   */
  @Nullable
  @Override
  protected ValidationInfo doValidate() {
    ValidationInfo validationInfo = null;
    String userName = userNameTxt.getText();
    String password = new String(passwordField.getPassword());
    String descriptionTxt = descTxtArea.getText();
    if (UIUtil.isBlankOrNull(userName))
      validationInfo = new ValidationInfo(UNAME_NOT_EMPTY_TXT, userNameTxt);
    else if (UIUtil.isBlankOrNull(password))
      validationInfo = new ValidationInfo(PWD_NOT_EMPTY_TXT, passwordField);
    else if (UIUtil.isBlankOrNull(descriptionTxt))
      validationInfo = new ValidationInfo(GIST_NOT_EMPTY_TXT, descTxtArea);

    return validationInfo;
  }

  /** Method to perform action on click of Ok button */
  @Override
  protected void doOKAction() {
    close(1);
    new SnippetExecutor(this).createGistSnippet();
  }

  /**
   * Method to validate the input credentials
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

  /** Listener for test button */
  private void addTestBtnActionListener() {
    btnTestAndShow.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (isCredentialValid()) {
              populateGistSnippets();
            }
          }
        });
  }

  /** Method to populate Snippets in the combo box */
  private void populateGistSnippets() {
    snippetCombo.removeAllItems();
    new SnippetExecutor(this).fetchSnippets();
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
   * Gets btn test and show.
   *
   * @return the btn test and show
   */
  public JButton getBtnTestAndShow() {
    return btnTestAndShow;
  }

  /**
   * Gets snippet combo.
   *
   * @return the snippet combo
   */
  public ComboBox<String> getSnippetCombo() {
    return snippetCombo;
  }

  /**
   * Gets desc txt area.
   *
   * @return the desc txt area
   */
  public JTextArea getDescTxtArea() {
    return descTxtArea;
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
   * Gets selected file.
   *
   * @return the selected file
   */
  public File getSelectedFile() {
    return selectedFile;
  }
}
