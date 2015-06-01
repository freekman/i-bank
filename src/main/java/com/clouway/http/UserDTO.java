package com.clouway.http;

/**
 * Created byivan.genchev1989@gmail.com.
 */
class UserDTO {

  public String name;
  public String password;
  public Double amount;

  public UserDTO() {
  }

  public UserDTO(String name, String password, Double amount) {
    this.name = name;
    this.password = password;
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserDTO userDTO = (UserDTO) o;

    if (name != null ? !name.equals(userDTO.name) : userDTO.name != null) return false;
    if (password != null ? !password.equals(userDTO.password) : userDTO.password != null) return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    return result;
  }
}
