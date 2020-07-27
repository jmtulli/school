package br.tulli.jm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.tulli.jm.model.User;

public class UserDAO {
  private static Connection connection;
  private static User user = null;

  static {
    connection = ConnectSchool.getConnection();
  }

  public User findUserByName(String name) throws SQLException {
    String query = "select * from user where name = ?";
    PreparedStatement ps = connection.prepareStatement(query);
    ps.setString(1, name);
    ps.execute();
    ResultSet resultSet = ps.getResultSet();
    if (resultSet.next()) {
      user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
      connection.rollback();
      return user;
    }
    return null;
  }

  public List<User> findAllUsers() throws SQLException {
    List<User> users = new ArrayList<>();
    String query = "select * from user";
    PreparedStatement ps = connection.prepareStatement(query);
    ps.execute();
    ResultSet resultSet = ps.getResultSet();
    while (resultSet.next()) {
      user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
      users.add(user);
    }
    connection.rollback();
    return users;
  }

  public User findEMailUser() throws SQLException {
    String query = "select * from user where groupid = (select groupid from user_group where name = 'mail')";
    PreparedStatement ps = connection.prepareStatement(query);
    ps.execute();
    ResultSet resultSet = ps.getResultSet();
    if (resultSet.next()) {
      user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
      connection.rollback();
      return user;
    }
    return null;
  }

  public void insertNewUser(String name, String password, Integer groupId) throws SQLException {
    String query = "insert into user (name, password, groupId) values (?, ?, ?)";
    PreparedStatement ps;
    try {
      ps = connection.prepareStatement(query);
      ps.setString(1, name);
      ps.setString(2, password);
      ps.setInt(3, groupId);
      ps.execute();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      System.err.println("Error when inserting user " + name + " to database. Error: " + e.getMessage());
    }
  }

  public void updateUser(String name, String password, Integer groupId, Integer userId) throws SQLException {
    String query = "update user set name = ?, password = ?, groupId = ? where userId = ?";
    PreparedStatement ps;
    try {
      ps = connection.prepareStatement(query);
      ps.setString(1, name);
      ps.setString(2, password);
      ps.setInt(3, groupId);
      ps.setInt(4, userId);
      ps.execute();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      System.err.println("Error when updating user " + name + " in the database. Error: " + e.getMessage());
    }
  }

  public void deleteUser(Integer userId) throws SQLException {
    String query = "delete from user where userId = ?";
    PreparedStatement ps;
    try {
      ps = connection.prepareStatement(query);
      ps.setInt(1, userId);
      ps.execute();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      System.err.println("Error when deleting user from database. Error: " + e.getMessage());
    }
  }

}
