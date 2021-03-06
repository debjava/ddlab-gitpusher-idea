/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
package com.ddlab.gitpusher.bitbucket.core;

import com.ddlab.gitpusher.core.IGitHandler;
import com.ddlab.gitpusher.core.NoGitHandler;
import com.ddlab.gitpusher.exception.GenericGitPushException;
import org.apache.commons.io.FileUtils;

import java.io.File;

import static com.ddlab.gitpusher.util.CommonUtil.HOME_GIT_PATH;

/**
 * The Class NoBitBucketGitHandler.
 *
 * @author Debadatta Mishra
 */
public class NoBitBucketGitHandler implements NoGitHandler {

  /* (non-Javadoc)
   * @see com.ddlab.gitpusher.core.NoGitHandler#handle(java.io.File, com.ddlab.gitpusher.core.IGitHandler)
   */
  @Override
  public void handle(File projectDir, IGitHandler gitHandler) throws Exception {
    String repoName = projectDir.getName();
    boolean repoExistsFlag = gitHandler.repoExists(repoName);
    if (repoExistsFlag) {
      System.out.println(
          "A repository with the same project name already exists in BitBucket, create different name");
      throw new GenericGitPushException(
          "A repository with the same project name already exists in BitBucket."
              + "You can refactor the existing project with a different name.");
    } else {
      createCloneCopy(repoName, projectDir, gitHandler);
      // update the project
      gitHandler.update(projectDir, null);
    }
  }

  /**
   * Creates the clone copy.
   *
   * @param repoName the repo name
   * @param projectDir the project dir
   * @param gitHandler the git handler
   * @throws Exception the exception
   */
  private void createCloneCopy(String repoName, File projectDir, IGitHandler gitHandler)
      throws Exception {
    // Create a Hosted Repo
    File tempCloneDir = null;
    try {
      System.out.println("Repo name : " + repoName);
      gitHandler.createHostedRepo(repoName);
      System.out.println("Repo created in bitbucket successfully...");
      tempCloneDir = new File(HOME_GIT_PATH);
      // clone project
      gitHandler.clone(repoName, tempCloneDir);
      // copy to project directory
      FileUtils.copyDirectory(tempCloneDir, projectDir);
    } catch (Exception e) {
      throw e;
    } finally {
      deleteSilently(tempCloneDir);
    }
  }

  /**
   * Delete a folder silently
   *
   * @param tempCloneDir
   */
  private void deleteSilently(File tempCloneDir) {
    if (tempCloneDir != null && tempCloneDir.exists()) {
      String dotGitDir = tempCloneDir.getAbsolutePath() + File.separator + ".git";
      FileUtils.deleteQuietly(new File(dotGitDir));
    }
  }
}
