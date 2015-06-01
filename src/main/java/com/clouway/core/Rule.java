package com.clouway.core;

/**
 * @author Ivan Genchev (ivan.genchev1989@gmail.com)
 */
public class Rule {

  public final String param;
  public final String regEx;
  public final String errorMessage;

  public Rule(String param, String regEx, String errorMessage) {
    this.param = param;
    this.regEx = regEx;
    this.errorMessage = errorMessage;
  }
}
