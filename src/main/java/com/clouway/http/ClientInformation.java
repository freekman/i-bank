package com.clouway.http;

import com.clouway.core.SidProvider;
import com.clouway.core.User;
import com.clouway.core.UserFinder;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/info")
@Service
public class ClientInformation {

  private final UserFinder userFinder;
  private final SidProvider sidProvider;

  @Inject
  public ClientInformation(UserFinder userFinder, SidProvider sidProvider) {
    this.userFinder = userFinder;
    this.sidProvider = sidProvider;
  }
  @Post
  public Reply<?> sendInfo() {
    String sid = sidProvider.get();
    if (null == sid) {
      return Reply.with("Sid not found!").status(404);
    }
    User user = userFinder.findBySidIfExist(sid);
    if (null != user) {
      return Reply.with(new UserDTO(user.getName(), user.getPassword(), user.getAmount())).as(Json.class).status(200);
    }
    return Reply.with("Sid not found!").status(404);
  }
}
