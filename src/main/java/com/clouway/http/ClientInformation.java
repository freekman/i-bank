package com.clouway.http;

import com.clouway.core.CurrentUser;
import com.clouway.core.User;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Post;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/info")
@Service
public class ClientInformation {


  private CurrentUser currentUser;

  @Inject
  public ClientInformation(CurrentUser currentUser) {

    this.currentUser = currentUser;
  }

  @Post
  public Reply<?> sendInfo() {
    User currentUser = this.currentUser.get();
    if (null != currentUser) {
      return Reply.with(new UserDTO(currentUser.getName(), currentUser.getPassword(), currentUser.getAmount())).as(Json.class).status(200);
    }
    return Reply.with("Sid not found!").status(404);
  }
}
