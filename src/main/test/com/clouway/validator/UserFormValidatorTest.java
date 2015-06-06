package com.clouway.validator;

import com.clouway.core.Session;
import com.clouway.core.User;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserFormValidatorTest {
  private UserFormValidator validator;
  private List<String> expectedErrors;

  @Before
  public void setUp() throws Exception {
    validator = new UserFormValidator();
    expectedErrors = new ArrayList<String>() {{
      add("The name size must be from 2-20 only letters and numbers!");
      add("The password size must be from 2-20,without any white space!");
    }};
  }

  @Test
  public void happyPath() throws Exception {
    User user = new User("aaa", "aaa", new BigDecimal(0), new Session("", "", 0));
    List<String> errors = validator.validate(user);
    assertThat(errors.size(), is(0));
  }

  @Test
  public void missingName() throws Exception {
    User user = new User("", "aaa", new BigDecimal(0), new Session("", "", 0));
    List<String> errors = validator.validate(user);
    assertThat(errors.size(), is(1));
    assertEquals(errors.get(0), expectedErrors.get(0));
  }

  @Test
  public void missingPassword() throws Exception {
    User user = new User("aaa", "", new BigDecimal(0), new Session("", "", 0));
    List<String> errors = validator.validate(user);
    assertThat(errors.size(), is(1));
    assertEquals(errors.get(0), expectedErrors.get(1));
  }

  @Test
  public void nameWithSpaces() throws Exception {
    User user = new User("aaa  xxx", "aaa", new BigDecimal(0), new Session("", "", 0));
    List<String> errors = validator.validate(user);
    assertThat(errors.size(), is(1));
    assertEquals(errors.get(0), expectedErrors.get(0));
  }

  @Test
  public void passwordWithSpaces() throws Exception {
    User user = new User("aaa", "a a a", new BigDecimal(0), new Session("", "", 0));
    List<String> errors = validator.validate(user);
    assertThat(errors.size(), is(1));
    assertEquals(errors.get(0), expectedErrors.get(1));
  }

  @Test
  public void missingCredentials() throws Exception {
    User user = new User("", "", new BigDecimal(0), new Session("", "", 0));
    List<String> errors = validator.validate(user);
    assertThat(errors.size(), is(2));
    assertEquals(errors, expectedErrors);
  }
}