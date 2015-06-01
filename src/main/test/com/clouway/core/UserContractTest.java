package com.clouway.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserContractTest {
  @Test
  public void equalObjects() throws Exception {
    User user1 = new User("name","pass",0.0,new Session("sid","name",0l));
    User user2 = new User("name","pass",0.0,new Session("sid","name",0l));
    assertEquals(user1,user2);
  }

  @Test
  public void differentNames() throws Exception {
    User user1 = new User("newName","pass",0.0,new Session("sid","name",0l));
    User user2 = new User("name","pass",0.0,new Session("sid","name",0l));
    assertNotEquals(user1, user2);
  }
  @Test
  public void differentPasswords() throws Exception {
    User user1 = new User("name","password",0.0,new Session("sid","name",0l));
    User user2 = new User("name","pass",0.0,new Session("sid","name",0l));
    assertNotEquals(user1,user2);
  }
  @Test
  public void differentAmountOfMoney() throws Exception {
    User user1 = new User("name","pass",1110.0,new Session("sid","name",0l));
    User user2 = new User("name","pass",0.0,new Session("sid","name",0l));
    assertNotEquals(user1,user2);
  }

}