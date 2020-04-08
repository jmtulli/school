package br.tulli.jm.model;

public class Group {
  private int groupId;
  private String name;

  public Group(int groupId, String name) {
    this.groupId = groupId;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getGroupId() {
    return groupId;
  }

  @Override
  public String toString() {
    return name;
  }

}
