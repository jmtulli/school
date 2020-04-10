package br.tulli.jm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.tulli.jm.model.Group;

public class GroupDAO {
  private static Connection connection;
  private static Group group = null;

  static {
    connection = new ConnectSchool().getConnection();
  }

  public List<Group> findAllGroups() throws SQLException {
    List<Group> groups = new ArrayList<>();
    String query = "select * from user_group";
    PreparedStatement ps = connection.prepareStatement(query);
    ps.execute();
    ResultSet resultSet = ps.getResultSet();
    while (resultSet.next()) {
      group = new Group(resultSet.getInt(1), resultSet.getString(2));
      groups.add(group);
    }
    return groups;
  }

  public Group findGroupById(int id) throws SQLException {
    String query = "select * from user_group where groupid = ?";
    PreparedStatement ps = connection.prepareStatement(query);
    ps.setInt(1, id);
    ps.execute();
    ResultSet resultSet = ps.getResultSet();
    if (resultSet.next()) {
      group = new Group(resultSet.getInt(1), resultSet.getString(2));
      return group;
    }
    return null;
  }

}
