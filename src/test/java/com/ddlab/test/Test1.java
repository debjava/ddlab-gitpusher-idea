package com.ddlab.test;

import com.ddlab.gitpusher.core.GitType;
import com.ddlab.gitpusher.core.IGitPusher;
import com.ddlab.gitpusher.core.UserAccount;
import com.ddlab.gitpusher.exception.GenericGitPushException;

public class Test1 {
  public static void main(String[] args) {
      String userName = "techpiku@gmail.com";
      String password = "vostak11";
      String gitType = "GitHub";
      UserAccount userAccount =
              new UserAccount(userName, password);
      IGitPusher gitPusher = GitType.fromString(gitType).getGitPusher(userAccount);
      String[] snippets = new String[0];
      try {
          snippets = gitPusher.getExistingSnippets();
          for (String s : snippets) {
              System.out.println("Snippets : " + s);
          }
      } catch (GenericGitPushException e) {
          e.printStackTrace();
      }

  }
}
