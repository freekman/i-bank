package com.clouway.core;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class UserTest {
  @Test
  public void equalObjects() throws Exception {
    User user1 = new User("name","pass",new BigDecimal(0),new Session("sid","name",0l));
    User user2 = new User("name","pass",new BigDecimal(0),new Session("sid","name",0l));
    assertEquals(user1,user2);
  }

  @Test
  public void differentNames() throws Exception {
    User user1 = new User("newName","pass",new BigDecimal(0),new Session("sid","name",0l));
    User user2 = new User("name","pass",new BigDecimal(0),new Session("sid","name",0l));
    assertNotEquals(user1, user2);
  }
  @Test
  public void differentPasswords() throws Exception {
    User user1 = new User("name","password",new BigDecimal(0),new Session("sid","name",0l));
    User user2 = new User("name","pass",new BigDecimal(0),new Session("sid","name",0l));
    assertNotEquals(user1,user2);
  }
  @Test
  public void differentAmountOfMoney() throws Exception {
    User user1 = new User("name","pass",new BigDecimal(110),new Session("sid","name",0l));
    User user2 = new User("name","pass",new BigDecimal(0),new Session("sid","name",0l));
    assertNotEquals(user1,user2);
  }

}