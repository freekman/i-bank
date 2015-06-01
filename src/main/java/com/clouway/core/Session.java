package com.clouway.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class Session {
  private String sessionId;
  private String userName;
  private long sessionTimeCreated;

  public Session(String sessionId, String userName, long sessionTimeOut) {
    this.sessionId = sessionId;
    this.userName = userName;
    this.sessionTimeCreated = sessionTimeOut;
  }

  public String getSessionId() {
    return sessionId;
  }

  public String getUserName() {
    return userName;
  }

  public long getSessionTimeCreated() {
    return sessionTimeCreated;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Session session = (Session) o;

    if (sessionTimeCreated != session.sessionTimeCreated) return false;
    if (sessionId != null ? !sessionId.equals(session.sessionId) : session.sessionId != null) return false;
    if (userName != null ? !userName.equals(session.userName) : session.userName != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = sessionId != null ? sessionId.hashCode() : 0;
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (int) (sessionTimeCreated ^ (sessionTimeCreated >>> 32));
    return result;
  }
}
