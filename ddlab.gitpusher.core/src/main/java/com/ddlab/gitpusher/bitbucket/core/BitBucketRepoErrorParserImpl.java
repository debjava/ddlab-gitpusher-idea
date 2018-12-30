/*
 * Copyright 2018 Tornado Project from DDLAB Inc. or its subsidiaries. All Rights Reserved.
 */
package com.ddlab.gitpusher.bitbucket.core;

import com.ddlab.gitpusher.core.IErrorResponseParser;
import com.ddlab.gitpusher.exception.GenericGitPushException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * The Class BitBucketRepoErrorParserImpl.
 *
 * @author Debadatta Mishra
 */
public class BitBucketRepoErrorParserImpl implements IErrorResponseParser<String, String> {
  /**
   * Method user to parse the error
   *
   * @param jsonResponse
   * @return
   * @throws GenericGitPushException
   */
  @Override
  public String parseError(String jsonResponse) throws GenericGitPushException {
    String errMsg = "";
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    JsonNode rootNode = null;
    try {
      rootNode = mapper.readTree(jsonResponse);
      JsonNode errorNode = rootNode.get("error");
      if (errorNode != null) {
        errMsg = errorNode.get("message").asText();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return errMsg;
  }
}
