package com.ddlab.tornado.common;

import com.ddlab.generator.IGitIgnoreGen;
import com.ddlab.generator.IReadMeGen;
import com.ddlab.generator.gitignore.GitIgnoreGenerator;
import com.ddlab.generator.readme.ReadMeGenerator;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UIUtil {

  public static GridBagLayout getPanelLayout() {
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] {0, 0, 0};
    gridBagLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
    gridBagLayout.columnWeights = new double[] {0.0, 1.0, Double.MIN_VALUE};
    gridBagLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
    return gridBagLayout;
  }

  private static BalloonBuilder getBalloonBuilder(MessageType msgType, String msg) {
    return JBPopupFactory.getInstance()
        .createHtmlTextBalloonBuilder(msg, msgType, null)
        .setCloseButtonEnabled(true)
        .setShadow(true)
        .setHideOnAction(true);
  }

  private static Balloon getPopupBalloon(
      MessageType msgType, String msg, boolean isAutoDisposable) {
    long timeInSeconds = 5000L; // 5 seconds
    BalloonBuilder balloonBuilder = getBalloonBuilder(msgType, msg);
    if (isAutoDisposable) balloonBuilder.setFadeoutTime(timeInSeconds);

    return balloonBuilder.createBalloon();
  }

  public static void showErrorBalloon(Project project, String errMsg) {
    final StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
    Balloon errorBalloon = getPopupBalloon(MessageType.ERROR, errMsg, false);
    errorBalloon.show(
        RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atRight);
  }

  public static void showInfoBalloon(Project project, String infoMsg, boolean isAutoDisposable) {
    final StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
    Balloon errorBalloon = getPopupBalloon(MessageType.INFO, infoMsg, isAutoDisposable);
    errorBalloon.show(
        RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atRight);
  }

  public static void notifyError(String errorMsg) {
    Notifications.Bus.notify(
        new Notification("ddlab.gitpusher.id", "ERROR", errorMsg, NotificationType.ERROR));
  }

  public static void notifyInfo(String infoMsg) {
    Notifications.Bus.notify(
        new Notification(
            "ddlab.gitpusher.id", "INFORMATION", infoMsg, NotificationType.INFORMATION));
  }

  public static boolean isBlankOrNull(String str) {
    return (str == null || str.trim().length() == 0);
  }

  /**
   * Generate read me file.
   *
   * @param selectedFile the selected file
   * @param description the description
   */
  public static void generateReadMeFile(File selectedFile, String description) {
    IReadMeGen readMeGen = new ReadMeGenerator();
    String projectName = selectedFile.getName();
    description =
        (description == null || description.trim().isEmpty()) ? "To be updated later" : description;
    String readMeContents = readMeGen.generateReadMeMdContents(projectName, description, null);
    Path readMePath = Paths.get(selectedFile.getAbsolutePath() + File.separator + "README.md");
    try {
      if (Files.exists(readMePath)) return;
      Files.write(readMePath, readMeContents.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Generate git ignore file.
   *
   * @param selectedFile the selected file
   */
  public static void generateGitIgnoreFile(File selectedFile) {
    IGitIgnoreGen gitIgnoreGenerator = new GitIgnoreGenerator();
    String gitIgnoreContents = gitIgnoreGenerator.generateGitIgnoreContents();
    Path gitIgnorePath = Paths.get(selectedFile.getAbsolutePath() + File.separator + ".gitignore");
    try {
      if (Files.exists(gitIgnorePath)) return;
      Files.write(gitIgnorePath, gitIgnoreContents.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
