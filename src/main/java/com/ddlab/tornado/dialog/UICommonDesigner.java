/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
package com.ddlab.tornado.dialog;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import java.awt.*;

import static com.ddlab.tornado.common.CommonConstants.*;

/**
 * The type Ui common designer.
 *
 * @author Debadatta Mishra
 */
public class UICommonDesigner {

  /**
   * Create git type combo lbl.
   *
   * @param contentPanel the content panel
   */
  public void createGitTypeComboLbl(JPanel contentPanel) {
    JBLabel lblSelectGitAccount = new JBLabel(ACT_TYPE_LBL_TXT);
    GridBagConstraints gbc_lblSelectGitAccount = new GridBagConstraints();
    gbc_lblSelectGitAccount.insets = new Insets(0, 0, 5, 5);
    gbc_lblSelectGitAccount.anchor = GridBagConstraints.EAST;
    gbc_lblSelectGitAccount.gridx = 0;
    gbc_lblSelectGitAccount.gridy = 0;
    contentPanel.add(lblSelectGitAccount, gbc_lblSelectGitAccount);
  }

  /**
   * Gets git type combo.
   *
   * @param contentPanel the content panel
   * @return the git type combo
   */
  public ComboBox<String> getGitTypeCombo(JPanel contentPanel) {
    ComboBox<String> gitActCombo = new ComboBox<String>(GIT_ACCOUNTS);
    GridBagConstraints gbc_comboBox = new GridBagConstraints();
    gbc_comboBox.insets = new Insets(0, 0, 5, 0);
    gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
    gbc_comboBox.gridx = 1;
    gbc_comboBox.gridy = 0;
    contentPanel.add(gitActCombo, gbc_comboBox);
    return gitActCombo;
  }

  /**
   * Create user name lbl.
   *
   * @param contentPanel the content panel
   */
  public void createUserNameLbl(JPanel contentPanel) {
    JBLabel lblUsername = new JBLabel(USER_NAME_TEXT);
    GridBagConstraints gbc_lblUsername = new GridBagConstraints();
    gbc_lblUsername.anchor = GridBagConstraints.EAST;
    gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
    gbc_lblUsername.gridx = 0;
    gbc_lblUsername.gridy = 1;
    contentPanel.add(lblUsername, gbc_lblUsername);
  }

  /**
   * Gets user name text.
   *
   * @param contentPanel the content panel
   * @return the user name text
   */
  public JBTextField getUserNameText(JPanel contentPanel) {
    JBTextField userNameTxt = new JBTextField();
    GridBagConstraints gbc_textField = new GridBagConstraints();
    gbc_textField.insets = new Insets(0, 0, 5, 0);
    gbc_textField.fill = GridBagConstraints.HORIZONTAL;
    gbc_textField.gridx = 1;
    gbc_textField.gridy = 1;
    contentPanel.add(userNameTxt, gbc_textField);
    userNameTxt.setColumns(10);
    return userNameTxt;
  }

  /**
   * Create password lbl.
   *
   * @param contentPanel the content panel
   */
  public void createPasswordLbl(JPanel contentPanel) {
    JBLabel lblPassword = new JBLabel(PWD_LBL_TXT);
    GridBagConstraints gbc_lblPassword = new GridBagConstraints();
    gbc_lblPassword.anchor = GridBagConstraints.EAST;
    gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
    gbc_lblPassword.gridx = 0;
    gbc_lblPassword.gridy = 2;
    contentPanel.add(lblPassword, gbc_lblPassword);
  }

  /**
   * Gets passwordtext.
   *
   * @param contentPanel the content panel
   * @return the passwordtext
   */
  public JBPasswordField getPasswordtext(JPanel contentPanel) {
    JBPasswordField passwordField = new JBPasswordField();
    GridBagConstraints gbc_passwordField = new GridBagConstraints();
    gbc_passwordField.insets = new Insets(0, 0, 5, 0);
    gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
    gbc_passwordField.gridx = 1;
    gbc_passwordField.gridy = 2;
    contentPanel.add(passwordField, gbc_passwordField);
    return passwordField;
  }

  /**
   * Gets test and show btn.
   *
   * @param contentPanel the content panel
   * @param btnText the btn text
   * @return the test and show btn
   */
  public JButton getTestAndShowBtn(JPanel contentPanel, String btnText) {
    JButton btnTestAndShow = new JButton(btnText);
    GridBagConstraints gbc_btnTestAndShow = new GridBagConstraints();
    gbc_btnTestAndShow.anchor = GridBagConstraints.EAST;
    gbc_btnTestAndShow.insets = new Insets(0, 0, 5, 5);
    gbc_btnTestAndShow.gridx = 0;
    gbc_btnTestAndShow.gridy = 3;
    contentPanel.add(btnTestAndShow, gbc_btnTestAndShow);
    return btnTestAndShow;
  }

  /**
   * Gets populate combo.
   *
   * @param contentPanel the content panel
   * @return the populate combo
   */
  public ComboBox<String> getPopulateCombo(JPanel contentPanel) {
    ComboBox<String> snippetCombo = new ComboBox<String>();
    GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
    gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
    gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
    gbc_comboBox_1.gridx = 1;
    gbc_comboBox_1.gridy = 3;
    contentPanel.add(snippetCombo, gbc_comboBox_1);
    return snippetCombo;
  }

  /**
   * Create desc txt lbl.
   *
   * @param contentPanel the content panel
   * @param descText the desc text
   */
  public void createDescTxtLbl(JPanel contentPanel, String descText) {
    JLabel lblProvideABrief = new JLabel(descText);
    GridBagConstraints gbc_lblProvideABrief = new GridBagConstraints();
    gbc_lblProvideABrief.insets = new Insets(0, 0, 5, 0);
    gbc_lblProvideABrief.anchor = GridBagConstraints.WEST;
    gbc_lblProvideABrief.gridwidth = 2;
    gbc_lblProvideABrief.gridx = 0;
    gbc_lblProvideABrief.gridy = 4;
    contentPanel.add(lblProvideABrief, gbc_lblProvideABrief);
  }

  /**
   * Gets desc txt area.
   *
   * @param contentPanel the content panel
   * @return the desc txt area
   */
  public JTextArea getDescTxtArea(JPanel contentPanel) {
    JTextArea descTxtArea = new JTextArea(3, 2);
    descTxtArea.setText("");

    GridBagConstraints gbc_txtrDesctxtarea = new GridBagConstraints();
    gbc_txtrDesctxtarea.anchor = GridBagConstraints.NORTH;
    gbc_txtrDesctxtarea.gridwidth = 2;
    gbc_txtrDesctxtarea.insets = new Insets(0, 0, 0, 5);
    gbc_txtrDesctxtarea.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtrDesctxtarea.gridx = 0;
    gbc_txtrDesctxtarea.gridy = 5;
    contentPanel.add(descTxtArea, gbc_txtrDesctxtarea);
    return descTxtArea;
  }
}
