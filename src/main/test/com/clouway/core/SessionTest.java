package com.clouway.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class SessionTest {
  @Test
  public void happyPath() throws Exception {
    Session session1=new Session("aaa","Ivan",12l);
    Session session2=new Session("aaa","Ivan",12l);
    assertEquals(session1,session2);
  }
  @Test
  public void differentSid() throws Exception {
    Session session1=new Session("aaa","Ivan",12l);
    Session session2=new Session("saa","Ivan",12l);
    assertNotEquals(session1, session2);
  }
  @Test
  public void differentName() throws Exception {
    Session session1=new Session("aaa","Ivan",12l);
    Session session2=new Session("aaa","van",12l);
    assertNotEquals(session1,session2);
  }
  @Test
  public void differentTimeOut() throws Exception {
    Session session1=new Session("aaa","Ivan",12l);
    Session session2=new Session("aaa","Ivan",11l);
    assertNotEquals(session1,session2);
  }

}