package com.clouway.db.adapter.mongodb.persistent;

import com.clouway.core.Session;
import com.clouway.core.SessionFinder;
import com.clouway.core.SessionRegister;
import com.google.inject.Inject;
import com.mongodb.client.FindIterable;
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
  public void createSession(String sid, String userName, Long timeOut) {
    db.getCollection("user").updateOne(new Document("name", userName), new Document("$set",
            new Document("session"
                    , new Document().append("sid", sid).append("timeOut", timeOut))));
  }

  @Override
  public void clearSession(String sid) {
    db.getCollection("user").updateOne(new Document("session.sid", sid), new Document("$set",
            new Document("session"
                    , new Document().append("sid", "").append("timeOut", 0l))));
  }

  @Override
  public boolean refreshSession(String sid, Long newTime) {
    Session currentSession = findBySid(sid);
    if (null != currentSession && isValid(currentSession, 30)) {
      db.getCollection("user").updateOne(new Document("session.sid", sid), new Document("$set", new Document("session.timeOut", newTime)));
      return true;
    }
    return false;
  }

  @Override
  public List<Session> findAll() {
    List<Session> list = new ArrayList<>();
    FindIterable<Document> result = db.getCollection("user").find();
    for (Document doc : result) {
      String userNAme = (String) doc.get("name");
      Document session = (Document) doc.get("session");
      if (null != session) {
        String sessionId = (String) session.get("sid");
        Long timeCreated = (Long) session.get("timeOut");
        list.add(new Session(sessionId, userNAme, timeCreated));
      } else {
        list.add(new Session("", userNAme, 0));
      }
    }
    return list;
  }

  @Override
  public Session findBySid(String sid) {
    Document result = db.getCollection("user").find(eq("session.sid", sid)).first();
    if (null != result) {
      String userNAme = (String) result.get("name");
      Document session = (Document) result.get("session");
      String sessionId = (String) session.get("sid");
      Long timeCreated = (Long) session.get("timeOut");
      return new Session(sessionId, userNAme, timeCreated);
    }
    return null;
  }

  private boolean isValid(Session userSession, int lifeTimeInMinutes) {
    long lifeTimeInMilliseconds = lifeTimeInMinutes * 60000;
    if (Calendar.getInstance().getTimeInMillis() - userSession.getSessionTimeCreated() > lifeTimeInMilliseconds) {
      clearSession(userSession.getSessionId());
      return false;
    }
    return true;
  }

}
