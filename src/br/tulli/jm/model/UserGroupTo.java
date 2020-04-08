package br.tulli.jm.model;

public class UserGroupTo {
  private Integer userId;
  private String userName;
  private String password;
  private Group group;

  public UserGroupTo(Integer userId, String userName, String password, Group group) {
    this.userId = userId;
    this.userName = userName;
    this.password = password;
    this.group = group;
  }

  public Integer getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public Group getGroup() {
    return group;
  }
}
