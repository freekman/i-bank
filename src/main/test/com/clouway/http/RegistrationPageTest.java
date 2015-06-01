package com.clouway.http;

import com.clouway.core.ExistingUserException;
import com.clouway.core.Session;
import com.clouway.core.User;
import com.clouway.core.UserRegister;
import com.clouway.validator.Validator;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RegistrationPageTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private RegistrationPage page;
  private UserRegister userRegister;
  private Validator<User> validator;

  @Before
  public void setUp() throws Exception {
    userRegister = context.mock(UserRegister.class);
    validator = context.mock(Validator.class);
    page = new RegistrationPage(userRegister, validator);
  }

  @Test
  public void deposit() throws Exception {
    page.uName = "Ivan";
    page.pwd = "qwert";
    final List<String> erList = new ArrayList<>();
    context.checking(new Expectations() {{
      oneOf(validator).validate(getUser());
      will(returnValue(erList));
      oneOf(userRegister).register(getUser());
    }});
    page.register();
    assertThat(page.getMessages().size(), is(1));
    assertThat(page.getMessages().get(0), is("Registration successful."));
  }

  @Test
  public void userAlreadyExists() throws Exception {
    page.uName = "Ivan";
    page.pwd = "qwert";
    final List<String> erList = new ArrayList<>();
    context.checking(new Expectations() {{
      oneOf(validator).validate(getUser());
      will(returnValue(erList));
      oneOf(userRegister).register(getUser());
      will(throwException(new ExistingUserException()));
    }});
    page.register();
    assertThat(page.getMessages().size(), is(1));
    assertThat(page.getMessages().get(0), is("User already exists."));
  }

  private User getUser() {
    return new User("Ivan", "qwert", 0.0, new Session("", "Ivan", 0));
  }

}