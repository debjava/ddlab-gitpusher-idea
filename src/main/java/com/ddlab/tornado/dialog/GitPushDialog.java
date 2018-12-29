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

  public GitPushDialog(@Nullable Project project, File selectedRepo, boolean canBeParent) {
    super(project, canBeParent);
    setTitle(DLG_TITLE_TXT);
    super.setSize(300, 300);
    this.project = project;
    this.selectedRepo = selectedRepo;
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
    repoBtnTestAndShow = uiDesinger.getTestAndShowBtn(contentPanel, REPO_BTN_TXT);
    repoCombo = uiDesinger.getPopulateCombo(contentPanel);
    addTestBtnActionListener();
  }

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

  private void populateRepos() {
    repoCombo.removeAllItems();
    new RepoExecutor(this).fetchRepos();
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

  private void createDescTxt(JPanel contentPanel) {
    uiDesinger.createDescTxtLbl(contentPanel, READ_ME_INFO_TXT);
    readMeTxtArea = uiDesinger.getDescTxtArea(contentPanel);
  }

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

  @Override
  protected void doOKAction() {
    close(1);
    new RepoExecutor(this).createRepo();
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

  public JButton getRepoBtnTestAndShow() {
    return repoBtnTestAndShow;
  }

  public ComboBox getRepoCombo() {
    return repoCombo;
  }

  public JTextArea getReadMeTxtArea() {
    return readMeTxtArea;
  }

  public Project getProject() {
    return project;
  }

  public File getSelectedRepo() {
    return selectedRepo;
  }
}
