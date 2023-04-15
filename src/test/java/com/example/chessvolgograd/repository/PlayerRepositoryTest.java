package com.example.chessvolgograd.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.chessvolgograd.BaseTest;
import com.example.chessvolgograd.model.Player;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class PlayerRepositoryTest extends BaseTest {

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

  @BeforeEach
  void clear() {
    playerRepository.deleteAll();
  }

  @Autowired
  private PlayerRepository playerRepository;

  @Test
  @DisplayName("Save players")
  public void savePlayer() {
    Player savedPlayer = playerRepository.save(player);
    assertThat(savedPlayer).usingRecursiveComparison().ignoringFields("id").isEqualTo(player);
  }

  @Test
  @DisplayName("Find players by id")
  public void findPlayerById() {
    playerRepository.save(player);
    Optional<Player> foundPlayerOption = playerRepository.findById(player.getId());
    assertThat(foundPlayerOption).isNotEmpty();
    assertThat(foundPlayerOption.get()).usingRecursiveComparison().isEqualTo(player);
  }
}