package br.tulli.jm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSchool {

  private final String DB_URL = "jdbc:mysql://localhost/db_school?useSSL=false&allowPublicKeyRetrieval=true";
  private final String DB_USER = "root";
  private final String DB_PASS = "root";
  private static Connection connection = null;

  public ConnectSchool() {
    System.out.println("construtor ConnectSchool");
    connect();
  }

  private void connect() {
    try {
      if (connection == null) {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        System.out.println("conectado");
      } else {
        System.out.println("connection != null");
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
      System.out.println("close connection");
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
