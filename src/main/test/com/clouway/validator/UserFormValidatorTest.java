package com.clouway.validator;

import com.clouway.core.Session;
import com.clouway.core.User;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserFormValidatorTest {

  private UserFormValidator validator;
  @Before
  public void setUp() throws Exception {
    validator = new UserFormValidator();
  }

  @Test
  public void happyPath() throws Exception {
    User user = new User("aaa", "aaa", 0.0, new Session("", "", 0));
    List<String> errors=validator.validate(user);
    assertThat(errors.size(),is(0));
  }

  @Test
  public void missingName() throws Exception {
    User user = new User("", "aaa", 0.0, new Session("", "", 0));
    List<String> errors=validator.validate(user);
    assertThat(errors.size(),is(1));
    assertThat(errors.get(0),is("The name size must be from 2-20 only letters and numbers!"));
  }

  @Test
  public void missingPassword() throws Exception {
    User user = new User("aaa", "", 0.0, new Session("", "", 0));
    List<String> errors=validator.validate(user);
    assertThat(errors.size(),is(1));
    assertThat(errors.get(0),is("The password size must be from 2-20,without any white space!"));
  }

  @Test
  public void nameWithSpaces() throws Exception {
    User user = new User("aaa  xxx", "aaa", 0.0, new Session("", "", 0));
    List<String> errors=validator.validate(user);
    assertThat(errors.size(),is(1));
    assertThat(errors.get(0),is("The name size must be from 2-20 only letters and numbers!"));
  }

  @Test
  public void passwordWithSpaces() throws Exception {
    User user = new User("aaa", "a a a", 0.0, new Session("", "", 0));
    List<String> errors=validator.validate(user);
    assertThat(errors.size(),is(1));
    assertThat(errors.get(0),is("The password size must be from 2-20,without any white space!"));
  }

  @Test
  public void missingNameAndPassword() throws Exception {
    User user = new User("", "", 0.0, new Session("", "", 0));
    List<String> errors=validator.validate(user);
    assertThat(errors.size(),is(2));
    assertThat(errors.get(0),is("The name size must be from 2-20 only letters and numbers!"));
    assertThat(errors.get(1),is("The password size must be from 2-20,without any white space!"));
  }

}