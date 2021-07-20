package io.github.luanelioliveira.samplekafka.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalDatabase {

  private final Connection connection;

  public LocalDatabase(String databaseName) throws SQLException {
    var url = "jdbc:sqlite:target/" + databaseName + ".db";
    connection = DriverManager.getConnection(url);
  }

  public void createIfNotExists(String sql) {
    try {
      connection.createStatement().execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void update(String statement, String... params) throws SQLException {
    prepare(statement, params).execute();
  }

  public ResultSet query(String query, String... params) throws SQLException {
    return prepare(query, params).executeQuery();
  }

  private PreparedStatement prepare(String statement, String... params) throws SQLException {
    var preparedStatement = connection.prepareStatement(statement);
    for (int index = 0; index < params.length; index++) {
      preparedStatement.setString(index + 1, params[index]);
    }
    return preparedStatement;
  }

}
