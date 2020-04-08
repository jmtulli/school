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

  public UserDAO() {
    connection = new ConnectSchool().getConnection();
  }

  public User findUserByName(String name) throws SQLException {
    String query = "select * from user where name = ?";
    PreparedStatement ps = connection.prepareStatement(query);
    ps.setString(1, name);
    ps.execute();
    ResultSet resultSet = ps.getResultSet();
    while (resultSet.next()) {
      user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
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
    return users;
  }

  public Boolean insertNewUser(String name, String password, Integer groupId) {
    String query = "insert into user (name, password, groupId) values (?, ?, ?)";
    PreparedStatement ps;
    try {
      ps = connection.prepareStatement(query);
      ps.setString(1, name);
      ps.setString(2, password);
      ps.setInt(3, groupId);
      ps.execute();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  public Boolean updateUser(String name, String password, Integer groupId, Integer userId) {
    String query = "update user set name = ?, password = ?, groupId = ? where userId = ?";
    PreparedStatement ps;
    try {
      ps = connection.prepareStatement(query);
      ps.setString(1, name);
      ps.setString(2, password);
      ps.setInt(3, groupId);
      ps.setInt(4, userId);
      ps.execute();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  public Boolean deleteUser(Integer userId) {
    String query = "delete from user where userId = ?";
    PreparedStatement ps;
    try {
      ps = connection.prepareStatement(query);
      ps.setInt(1, userId);
      ps.execute();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  public User getUser() {
    return user;
  }

  // FIXME fechar conexao no lugar apropriado
  public void closeConnection() {
    new ConnectSchool().closeConnection();
  }

}
