package com.example.chessvolgograd;

import org.testcontainers.containers.PostgreSQLContainer;

public class BaseTest {

  static PostgreSQLContainer container = (PostgreSQLContainer)
      new PostgreSQLContainer<>("postgres")
          .withDatabaseName("test-db")
          .withUsername("postgres")
          .withPassword("pass");

  static {
    container.start();
  }
}
