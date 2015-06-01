package com.clouway.db.adapter.mongodb.persistent;

import com.clouway.core.Session;
import com.clouway.core.User;
import com.github.fakemongo.junit.FongoRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PersistentUserRepositoryTest {

  private PersistentUserRepository repository;
  private FakeBankMongoDatabase fakeBankMongoDatabase;
  @Rule
  public FongoRule fongoRule = new FongoRule("bank");

  @Before
  public void setUp() throws Exception {
    fakeBankMongoDatabase = new FakeBankMongoDatabase();
    repository = new PersistentUserRepository(fakeBankMongoDatabase.get());
  }

  @Test
  public void happyPath() throws Exception {
    repository.register(new User("Ivan", "qwerty", 0.0, new Session("", "Ivan", 0l)));
    List<User> users = repository.findAll();
    assertThat(users.size(), is(1));
    assertThat(users.size(), is(1));
    assertThat(users.get(0).getName(), is("Ivan"));
  }

  @Test
  public void multipleRegistrations() throws Exception {
    repository.register(new User("Ivan", "qwerty", 0.0, new Session("", "Ivan", 0l)));
    repository.register(new User("ASD", "qwerty", 0.0, new Session("", "Ivan", 0l)));
    repository.register(new User("DSA", "qwerty", 0.0, new Session("", "Ivan", 0l)));

    List<User> users = repository.findAll();
    assertThat(users.size(), is(3));
    assertThat(users.get(0).getName(), is("Ivan"));
    assertThat(users.get(0).getAmount(), is(0.0));
    assertThat(users.get(2).getName(), is("DSA"));
  }

  @Test
  public void updateAmount() throws Exception {
    repository.register(new User("ASD", "qwerty", 0.0, new Session("abc", "Ivan", 0l)));
    repository.updateAmount("abc", 30.0);
    List<User> result = repository.findAll();
    assertThat(result.get(0).getAmount(), is(30.0));
  }

  @Test
  public void searchForUserByName() throws Exception {
    repository.register(new User("Ivan", "qwerty", 0.0, new Session("abc", "Ivan", 0l)));
    repository.register(new User("ASD", "qwerty", 0.0, new Session("abc", "Ivan", 0l)));
    repository.register(new User("QWERTY", "qwerty", 0.0, new Session("abc", "Ivan", 0l)));
    User result = repository.findByName("Ivan");
    assertThat(result,is(new User("Ivan", "qwerty", 0.0, new Session("abc", "Ivan", 0l))));
  }

  @Test
  public void searchForUserBySid() throws Exception {
    repository.register(new User("Ivan", "qwerty", 0.0, new Session("asd", "Ivan", 0l)));
    repository.register(new User("ASD", "qwerty", 0.0, new Session("sss", "Ivan", 0l)));
    repository.register(new User("QWERTY", "qwerty", 0.0, new Session("ddd", "Ivan", 0l)));
    User result = repository.findBySidIfExist("asd");
    assertThat(result,is(new User("Ivan", "qwerty", 0.0, new Session("asd", "Ivan", 0l))));
  }

}