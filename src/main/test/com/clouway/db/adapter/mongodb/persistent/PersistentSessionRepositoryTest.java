package com.clouway.db.adapter.mongodb.persistent;

import com.clouway.core.Session;
import com.github.fakemongo.junit.FongoRule;
import org.bson.Document;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PersistentSessionRepositoryTest {
  @Rule
  public FongoRule fongoRule = new FongoRule();

  private PersistentSessionRepository repository;
  private FakeBankMongoDatabase database;

  @Before
  public void setUp() throws Exception {
    database = new FakeBankMongoDatabase();
    repository = new PersistentSessionRepository(database.get());
  }

  @Test
  public void happyPath() throws Exception {
    insertFakeUser("ivan", "qwe", 0.0, "", 0l);
    repository.create("asd", "ivan", 123l);
    List<Session> result = repository.findAll();
    assertThat(result.size(), is(1));
    assertThat(result.get(0).sessionId, is("asd"));
    assertThat(result.get(0).sessionTimeCreated, is(123l));
  }


  @Test
  public void resetUserSession() throws Exception {
    insertFakeUser("ivan", "qwe", 0.0, "", 0l);
    repository.create("asd", "ivan", 123l);
    repository.clear("asd");
    List<Session> result = repository.findAll();
    assertThat(result.size(), is(1));
    assertThat(result.get(0).sessionId, is(""));
    assertThat(result.get(0).sessionTimeCreated, is(0l));
  }

  @Test
  public void searchForUserByGivenSid() throws Exception {
    insertFakeUser("ivan", "qqq", 0.0, "asd", 12l);
    insertFakeUser("asd", "qqq", 0.0, "aaa", 12l);
    insertFakeUser("asdd", "qqq", 0.0, "sss", 12l);
    Session result = repository.findBySid("asd");
    assertThat(result, is(new Session("asd", "ivan", 12l)));
  }


  @Test
  public void searchForAllUsers() throws Exception {
    insertFakeUser("ivan", "qqq", 0.0, "asd", 12l);
    insertFakeUser("asd", "qqq", 0.0, "aaa", 12l);
    List<Session> result = repository.findAll();
    assertThat(result.size(), is(2));
    assertThat(result.get(0), is(new Session("asd", "ivan", 12l)));
    assertThat(result.get(1), is(new Session("aaa", "asd", 12l)));
  }

  @Test
  public void pretendWeHaveRegisteredUSer() throws Exception {
    insertFakeUser("ivan", "qqq", 0.0, "asd", getDate(2015, 6, 27, 10, 10));
    boolean result = repository.refresh("asd", getDate(2015, 6, 27, 10, 20));
    assertThat(result, is(true));
  }
  private void insertFakeUser(String name, String password, Double balance, String sid, Long timeOut) {
    database.get().getCollection("user").insertOne(new Document()
            .append("name", name)
            .append("password", password)
            .append("amount", balance)
            .append("session", new Document()
                    .append("sid", sid)
                    .append("timeOut", timeOut)));
  }

  private long getDate(int year, int month, int day, int hour, int minute) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    calendar.set(Calendar.HOUR, hour);
    calendar.set(Calendar.MINUTE, minute);
    return calendar.getTimeInMillis();
  }
}