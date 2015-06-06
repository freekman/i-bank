package com.clouway.db.adapter.mongodb.persistent;

import com.clouway.core.Session;
import com.clouway.core.SessionFinder;
import com.clouway.core.SessionRegister;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class PersistentSessionRepository implements SessionRegister, SessionFinder {

  private MongoDatabase db;

  @Inject
  public PersistentSessionRepository(MongoDatabase db) {
    this.db = db;
  }

  @Override
  public void create(String sid, String userName, Long timeOut) {
    MongoUserCollection().updateOne(new Document("name", userName), new Document("$set",
            new Document("session"
                    , new Document().append("sid", sid).append("timeOut", timeOut))));
  }


  @Override
  public void clear(String sid) {
    MongoUserCollection().updateOne(new Document("session.sid", sid), new Document("$set",
            new Document("session"
                    , new Document().append("sid", "").append("timeOut", 0L))));
  }

  @Override
  public boolean refresh(String sid, Long newTime) {
    Optional<Session> currentSession = findBySid(sid);
    if (currentSession.isPresent() && isValid(currentSession.get(), 30)) {
      MongoUserCollection().updateOne(new Document("session.sid", sid), new Document("$set", new Document("session.timeOut", newTime)));
      return true;
    }
    return false;
  }

  @Override
  public Optional<List<Session>> findAll() {
    List<Session> sessions = new ArrayList<>();
    FindIterable<Document> result = MongoUserCollection().find();
    for (Document doc : result) {
      String userNAme = (String) doc.get("name");
      Document session = (Document) doc.get("session");
      if (null != session) {
        String sessionId = (String) session.get("sid");
        Long timeCreated = (Long) session.get("timeOut");
        sessions.add(new Session(sessionId, userNAme, timeCreated));
      } else {
        sessions.add(new Session("", userNAme, 0));
      }
    }
    return Optional.of(sessions);
  }

  @Override
  public Optional<Session> findBySid(String sid) {
    Document result = MongoUserCollection().find(eq("session.sid", sid)).first();
    if (null != result) {
      String userNAme = (String) result.get("name");
      Document session = (Document) result.get("session");
      String sessionId = (String) session.get("sid");
      Long timeCreated = (Long) session.get("timeOut");
      return Optional.of(new Session(sessionId, userNAme, timeCreated));
    }
    return Optional.absent();
  }

  private boolean isValid(Session userSession, int lifeTimeInMinutes) {
    long lifeTimeInMilliseconds = lifeTimeInMinutes * 60000;
    if (Calendar.getInstance().getTimeInMillis() - userSession.sessionTimeCreated > lifeTimeInMilliseconds) {
      clear(userSession.sessionId);
      return false;
    }
    return true;
  }

  private MongoCollection<Document> MongoUserCollection() {
    return db.getCollection("user");
  }
}
