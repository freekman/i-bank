package com.clouway.db.adapter.mongodb.persistent;

import com.clouway.core.Session;
import com.clouway.core.User;
import com.github.fakemongo.junit.FongoRule;
import com.google.common.base.Optional;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PersistentUserRepositoryTest {
  @Rule
  public FongoRule fongoRule = new FongoRule("bank");

  private PersistentUserRepository repository;
  private FakeBankMongoDatabase fakeBankMongoDatabase;

  @Before
  public void setUp() throws Exception {
    fakeBankMongoDatabase = new FakeBankMongoDatabase();
    repository = new PersistentUserRepository(fakeBankMongoDatabase.get());
  }

  @Test
  public void happyPath() throws Exception {
    repository.register(new User("Ivan", "qwerty", 0.0, new Session("", "Ivan", 0l)));
    Optional<User> user = repository.findByName("Ivan");
    assertThat(user.get().name, is("Ivan"));
    assertThat(user.get().password, is("qwerty"));
    assertThat(user.get().getAmount(), is(0d));
  }

  @Test
  public void multipleRegistrations() throws Exception {
    repository.register(new User("Ivan", "qwerty", 0.0, new Session("", "Ivan", 0l)));
    repository.register(new User("ASD", "qwerty", 0.0, new Session("", "Ivan", 0l)));
    repository.register(new User("DSA", "qwerty", 0.0, new Session("", "Ivan", 0l)));

    List<User> users = findAll();
    assertThat(users.size(), is(3));
    assertThat(users.get(0).name, is("Ivan"));
    assertThat(users.get(0).getAmount(), is(0.0));
    assertThat(users.get(2).name, is("DSA"));
  }

  @Test
  public void updateAmount() throws Exception {
    repository.register(new User("ASD", "qwerty", 0.0, new Session("abc", "Ivan", 0l)));
    repository.updateAmount("abc", 30.0);
    List<User> result = findAll();
    assertThat(result.get(0).getAmount(), is(30.0));
  }

  @Test
  public void searchForUserByName() throws Exception {
    repository.register(new User("Ivan", "qwerty", 0.0, new Session("abc", "Ivan", 0l)));
    repository.register(new User("ASD", "qwerty", 0.0, new Session("abc", "Ivan", 0l)));
    repository.register(new User("QWERTY", "qwerty", 0.0, new Session("abc", "Ivan", 0l)));
    Optional<User> result = repository.findByName("Ivan");
    assertThat(result.get(), is(new User("Ivan", "qwerty", 0.0, new Session("abc", "Ivan", 0l))));
  }

  @Test
  public void searchForUserBySid() throws Exception {
    repository.register(new User("Ivan", "qwerty", 0.0, new Session("asd", "Ivan", 0l)));
    repository.register(new User("ASD", "qwerty", 0.0, new Session("sss", "Ivan", 0l)));
    repository.register(new User("QWERTY", "qwerty", 0.0, new Session("ddd", "Ivan", 0l)));
    Optional<User> result = repository.findBySidIfExist("asd");
    assertThat(result.get(), is(new User("Ivan", "qwerty", 0.0, new Session("asd", "Ivan", 0l))));
  }

  private List<User> findAll() {
    List<User> list = new ArrayList<>();
    FindIterable<Document> result = fakeBankMongoDatabase.get().getCollection("user").find();
    for (Document document : result) {
      String name = (String) document.get("name");
      String pwd = (String) document.get("password");
      Double amount = (Double) document.get("amount");
      Document session = (Document) document.get("session");
      String sessionId = session.getString("sid");
      Long time = session.getLong("timeOut");

      list.add(new User(name, pwd, amount, new Session(sessionId, name, time)));
    }
    return list;
  }
}