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

  public GistSnippetDialog(@Nullable Project project, File selectedFilePath, boolean canBeParent) {
    super(project, canBeParent);
    setTitle(DLG_TITLE_TXT);
    this.project = project;
    this.selectedFile = selectedFilePath;
    uiDesinger = new UICommonDesigner();
    init();
  }

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

  private void createGitTypeCombo(JPanel contentPanel) {
    uiDesinger.createGitTypeComboLbl(contentPanel);
    gitActCombo = uiDesinger.getGitTypeCombo(contentPanel);
  }

  private void createUserNameTxt(JPanel contentPanel) {
    uiDesinger.createUserNameLbl(contentPanel);
    userNameTxt = uiDesinger.getUserNameText(contentPanel);
  }

  private void createPasswordTxt(JPanel contentPanel) {
    uiDesinger.createPasswordLbl(contentPanel);
    passwordField = uiDesinger.getPasswordtext(contentPanel);
  }

  private void createPopulateCombo(JPanel contentPanel) {
    btnTestAndShow = uiDesinger.getTestAndShowBtn(contentPanel, GIST_BTN_TXT);
    snippetCombo = uiDesinger.getPopulateCombo(contentPanel);
    addTestBtnActionListener();
  }

  private void createDescTxt(JPanel contentPanel) {
    uiDesinger.createDescTxtLbl(contentPanel, GIST_LBL_TXT);
    descTxtArea = uiDesinger.getDescTxtArea(contentPanel);
  }

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

  @Override
  protected void doOKAction() {
    close(1);
    new SnippetExecutor(this).createGistSnippet();
  }

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

  private void populateGistSnippets() {
    snippetCombo.removeAllItems();
    new SnippetExecutor(this).fetchSnippets();
  }

  public void showInfoMessage(String infoMsg) {
    SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            UIUtil.showInfoBalloon(project, infoMsg, true);
          }
        });
  }

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

  public ComboBox<String> getGitActCombo() {
    return gitActCombo;
  }

  public JBTextField getUserNameTxt() {
    return userNameTxt;
  }

  public JBPasswordField getPasswordField() {
    return passwordField;
  }

  public JButton getBtnTestAndShow() {
    return btnTestAndShow;
  }

  public ComboBox<String> getSnippetCombo() {
    return snippetCombo;
  }

  public JTextArea getDescTxtArea() {
    return descTxtArea;
  }

  public Project getProject() {
    return project;
  }

  public File getSelectedFile() {
    return selectedFile;
  }
}
