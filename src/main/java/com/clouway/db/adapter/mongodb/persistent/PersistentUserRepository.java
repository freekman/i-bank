package com.clouway.db.adapter.mongodb.persistent;

import com.clouway.core.AccountRegister;
import com.clouway.core.ExistingUserException;
import com.clouway.core.Session;
import com.clouway.core.User;
import com.clouway.core.UserFinder;
import com.clouway.core.UserRegister;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.math.BigDecimal;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class PersistentUserRepository implements UserRegister, UserFinder, AccountRegister {

  private MongoDatabase db;

  @Inject
  public PersistentUserRepository(MongoDatabase db) {
    this.db = db;
  }

  @Override
  public void register(User user) {
    if (findByName(user.name).isPresent()) {
      throw new ExistingUserException();
    } else {
      db.getCollection("user").insertOne(new Document()
              .append("name", user.name)
              .append("password", user.password)
              .append("amount", 0.0)
              .append("session",
                      new Document()
                              .append("sid", user.session.sessionId)
                              .append("timeOut", user.session.sessionTimeCreated)));

    }
  }

  @Override
  public void updateAmount(String sid, Double newAmount) {
    try {
      db.getCollection("user").updateOne(new Document("session.sid", sid),
              new Document("$set", new Document("amount", newAmount)));
    } catch (MongoWriteException e) {
      throw new ExistingUserException();
    }
  }

  @Override
  public Optional<User> findByName(String userName) {
    Document result = db.getCollection("user").find(eq("name", userName)).first();
    if (null != result) {
      String name = (String) result.get("name");
      String password = (String) result.get("password");
      Double amount = (Double) result.get("amount");
      Document session = (Document) result.get("session");
      String sessionId = session.getString("sid");
      Long time = session.getLong("timeOut");
      return Optional.of(new User(name, password, new BigDecimal(amount), new Session(sessionId, name, time)));
    }
    return Optional.absent();
  }

  @Override
  public Optional<User> findBySidIfExist(String sid) {

    Document result = db.getCollection("user").find(eq("session.sid", sid)).first();
    if (null != result) {
      String name = (String) result.get("name");
      String password = (String) result.get("password");
      Double amount = (Double) result.get("amount");
      Document session = (Document) result.get("session");
      String sessionId = session.getString("sid");
      Long time = session.getLong("timeOut");
      return Optional.of(new User(name, password, new BigDecimal(amount), new Session(sessionId, name, time)));
    }
    return Optional.absent();
  }

}
