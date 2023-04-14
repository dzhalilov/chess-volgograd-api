package com.example.chessvolgograd.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.chessvolgograd.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class PlayerRepositoryTest {

  @Container
  PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer<>("postgres")
      .withDatabaseName("test-db")
      .withUsername("postgres")
      .withPassword("pass");

  @Autowired
  private PlayerRepository playerRepository;

  @Test
  public void savePlayer() {
    Player player = Player.builder()
        .id(123456)
        .sex("Ж")
        .name("Балалайкина Елена")
        .regionCode(34)
        .classicRating(1234)
        .rapidRating(1235)
        .blitzRating(1236)
        .age(2010)
        .fideId(3541534)
        .fideClassicRating(1000)
        .build();
    Player savedPlayer = playerRepository.save(player);
    assertThat(savedPlayer).usingRecursiveComparison().ignoringFields("id").isEqualTo(player);

  }
}