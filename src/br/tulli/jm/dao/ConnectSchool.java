package br.tulli.jm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    } catch (Exception e) {
      System.err.println("Error when connecting to database. Error: " + e.getMessage());
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
