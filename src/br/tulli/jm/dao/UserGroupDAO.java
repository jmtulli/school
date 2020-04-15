package br.tulli.jm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.tulli.jm.model.Group;
import br.tulli.jm.model.UserGroupTo;

public class UserGroupDAO {
  private static Connection connection = null;

  static {
    connection = ConnectSchool.getConnection();
  }

  public List<UserGroupTo> findAllUserGroup() {
    List<UserGroupTo> result = new ArrayList<>();
    String query = "select u.userId, u.name, u.password, ug.groupid, ug.name from user as u inner join user_group as ug where u.groupid = ug.groupid order by u.name asc";
    try {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.execute();
      ResultSet resultSet = statement.getResultSet();
      while (resultSet.next()) {
        UserGroupTo userGroup = new UserGroupTo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), new Group(resultSet.getInt(4), resultSet.getString(5)));
        result.add(userGroup);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        connection.rollback();
      } catch (SQLException e) {
        System.err.println("Error when reverting operation in database. Error: " + e.getMessage());
        e.printStackTrace();
      }
    }
    return result;
  }

  public Boolean isUserAdmin(Integer userId) {
    String query = "select u.userId, u.name, ug.groupid, ug.name from user as u inner join user_group as ug where u.groupid = (select ug.groupId where ug.name = 'Administrator') and u.userId = ? order by u.name asc";
    try {
      PreparedStatement ps = connection.prepareStatement(query);
      ps.setInt(1, userId);
      ps.execute();
      ResultSet resultSet = ps.getResultSet();
      if (resultSet.next()) {
        return true;
      }
    } catch (SQLException e) {
      System.err.println("Error when consulting database. Error: " + e.getMessage());
      return false;
    } finally {
      try {
        connection.rollback();
      } catch (SQLException e) {
        System.err.println("Error when reverting operation in database. Error: " + e.getMessage());
        e.printStackTrace();
      }
    }
    return false;
  }

}
