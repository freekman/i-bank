package com.clouway.http;

import com.clouway.core.BankUser;
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


  private BankUser bankUser;

  @Inject
  public ClientInformation(BankUser bankUser) {

    this.bankUser = bankUser;
  }

  @Post
  public Reply<?> sendInfo() {
    User user = bankUser.get();
    if (null != user) {
      return Reply.with(new UserDTO(user.getName(), user.getPassword(), user.getAmount())).as(Json.class).status(200);
    }
    return Reply.with("Sid not found!").status(404);
  }
}
