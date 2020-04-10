package br.tulli.jm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSchool {

  private final static String DB_URL = "jdbc:mysql://localhost/db_school?useSSL=false&allowPublicKeyRetrieval=true";
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
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
