package com.clouway.db.adapter.mongodb.persistent;

import com.clouway.core.Session;
import com.clouway.core.User;
import com.clouway.core.ExistingUserException;
import com.clouway.core.UserFinder;
import com.clouway.core.UserRegister;
import com.google.inject.Inject;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class PersistentUserRepository implements UserRegister, UserFinder {

  private MongoDatabase db;

  @Inject
  public PersistentUserRepository(MongoDatabase db) {
    this.db = db;
  }

  @Override
  public void register(User user) {
    try {
      db.getCollection("user").insertOne(new Document()
              .append("name", user.getName())
              .append("password", user.getPassword())
              .append("amount", 0.0)
              .append("session",
                      new Document()
                              .append("sid", user.getSession().getSessionId())
                              .append("timeOut", user.getSession().getSessionTimeCreated())));
    } catch (MongoWriteException e) {
      throw new ExistingUserException();
    }
  }

  @Override
  public void updateAmount(String sid, Double newAmount) {
    try {
      db.getCollection("user").updateOne(new Document("session.sid", sid),
              new Document("$set", new Document("amount", newAmount)));
      System.out.println(db.toString());
    } catch (MongoWriteException e) {
      throw new ExistingUserException();
    }
  }

  @Override
  public List<User> findAll() {
    List<User> list = new ArrayList<>();
    FindIterable<Document> result = db.getCollection("user").find();
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

  @Override
  public User findByName(String userName) {
    Document result = db.getCollection("user").find(eq("name", userName)).first();
    if (null != result) {
      String name = (String) result.get("name");
      String pwd = (String) result.get("password");
      Double amount = (Double) result.get("amount");
      Document session = (Document) result.get("session");
      String sessionId = session.getString("sid");
      Long time = session.getLong("timeOut");
      return new User(name, pwd, amount, new Session(sessionId, name, time));
    }
    return null;
  }

  @Override
  public User findBySidIfExist(String sid) {

    Document result = db.getCollection("user").find(eq("session.sid", sid)).first();
    if (null != result) {
      String name = (String) result.get("name");
      String pwd = (String) result.get("password");
      Double amount = (Double) result.get("amount");
      Document session = (Document) result.get("session");
      String sessionId = session.getString("sid");
      Long time = session.getLong("timeOut");
      return new User(name, pwd, amount, new Session(sessionId, name, time));
    }
    return null;
  }

}
