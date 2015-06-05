package com.clouway.http;

import com.clouway.core.CurrentUser;
import com.clouway.core.User;
import com.google.common.base.Optional;
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
    Optional<User> user = this.currentUser.get();
    if (user.isPresent()) {
      return Reply.with(new UserDTO(user.get().getName(), user.get().getPassword(), user.get().getAmount())).as(Json.class).status(200);
    }
    return Reply.with("Sid not found!").status(404);
  }
}
