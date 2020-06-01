package br.tulli.jm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import br.tulli.jm.util.Util;

public class ConnectSchool {

  private final static String DB_URL = "jdbc:mysql://localhost/db_school?useSSL=false&allowPublicKeyRetrieval=true&useTimezone=true&serverTimezone=UTC";
  private final static String DB_USER = "root";
  private final static String DB_PASS = "root";
  private static Connection connection = null;

  static {
    connect();
  }

  private static void connect() {
    System.out.println("connecting");
    try {
      if (connection == null) {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        connection.setAutoCommit(false);
      }
    } catch (CommunicationsException e) {
      e.printStackTrace();
      Util.showErrorMessage("Error when connecting to database. Check if the database service is running.\nError: " + e.getMessage());
      System.exit(0);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    return connection;
  }

  public static void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      System.err.println("Error when closing connecting to the database. Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

}
