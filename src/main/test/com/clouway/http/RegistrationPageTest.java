package com.clouway.http;

import com.clouway.core.UserRegister;
import com.clouway.validator.Validator;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;

import static org.hamcrest.CoreMatchers.is;

public class RegistrationPageTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private RegistrationPage page;
  private UserRegister userRegister;
  private Validator<UserDTO> validator;

  @Before
  public void setUp() throws Exception {
    userRegister = context.mock(UserRegister.class);
    validator = context.mock(Validator.class);
    page = new RegistrationPage(userRegister, validator);
  }

//  @Test
//  public void deposit() throws Exception {
//    page.uName = "Ivan";
//    page.pwd = "qwert";
//    final List<String> erList = new ArrayList<>();
//    final UserDTO testUserDTO = new UserDTO("Ivan", "qwert", 0.0);
//    context.checking(new Expectations() {{
//      oneOf(validator).validate(testUserDTO);
//      will(returnValue(erList));
//      oneOf(userRegister).register(testUserDTO);
//    }});
//    page.get();
//    assertThat(page.getMessages().size(), is(1));
//    assertThat(page.getMessages().get(0), is("Registration successful."));
//  }
//  @Test
//  public void userAlreadyExists() throws Exception {
//    page.uName = "Ivan";
//    page.pwd = "qwert";
//    final List<String> erList = new ArrayList<>();
//    final UserDTO testUserDTO = new UserDTO("Ivan", "qwert", 0.0);
//    context.checking(new Expectations() {{
//      oneOf(validator).validate(testUserDTO);
//      will(returnValue(erList));
//      oneOf(userRegister).register(testUserDTO);will(throwException(new UserAlreadyExistsException()));
//    }});
//    page.get();
//    assertThat(page.getMessages().size(), is(1));
//    assertThat(page.getMessages().get(0), is("User already exists."));
//  }

}