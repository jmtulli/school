package br.tulli.jm.model;

public class User {

  private int userId;
  private String name;
  private String password;
  private int groupId;

  public User(Integer userId, String name, String password) {
    this.userId = userId;
    this.name = name;
    this.password = password;
  }

  public int getUserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  public int getGroupId() {
    return groupId;
  }

}
