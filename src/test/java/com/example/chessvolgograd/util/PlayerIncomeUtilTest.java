package com.example.chessvolgograd.util;

import com.example.chessvolgograd.config.AppConfig;
import com.example.chessvolgograd.model.Player;
import java.lang.reflect.Method;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class PlayerIncomeUtilTest {

  private final AppConfig appConfig = new AppConfig();

  @Test
  @DisplayName("Create Player's list with Classic rating")
  void creatListOfPersonWithClassic() throws Exception {
    Player player1 = Player.builder()
        .id(149945)
        .name("Джалилова Алина")
        .sex("Ж")
        .regionCode(34)
        .age(2010)
        .classicRating(1588)
        .fideId(34400370)
        .fideClassicRating(1330)
        .build();
    Player player2 = Player.builder()
        .id(149947)
        .name("Иванов Илья")
        .sex("М")
        .regionCode(34)
        .age(2010)
        .classicRating(1000)
        .build();
    String classicPlayer1 = "149945,Джалилова Алина,f,RUS,34,Волгоградская область,2010,1588,34400370,1330";
    String classicPlayer2 = "149947,Иванов Илья,,RUS,34,Волгоградская область,2010,1000,,";
    List<String> playersInStrings = List.of(classicPlayer1, classicPlayer2);
    List<Player> expected = List.of(player1, player2);
    Method method = PlayerIncomeUtil.class.getDeclaredMethod("creatListOfPersonWithClassic",
        List.class);
    method.setAccessible(true);
    List<Player> actual = (List<Player>) method.invoke(new PlayerIncomeUtil(appConfig),
        playersInStrings);
    Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @DisplayName("Add Blitz rating to players")
  void addToPlayersBlitzRating() throws Exception {
    Player playerBefore1 = Player.builder()
        .id(149945)
        .name("Джалилова Алина")
        .sex("Ж")
        .regionCode(34)
        .age(2010)
        .classicRating(1588)
        .fideId(34400370)
        .fideClassicRating(1330)
        .build();
    Player playerBefore2 = Player.builder()
        .id(149947)
        .name("Иванов Илья")
        .sex("М")
        .regionCode(34)
        .age(2010)
        .classicRating(1000)
        .build();
    Player playerAfter1 = Player.builder()
        .id(149945)
        .name("Джалилова Алина")
        .sex("Ж")
        .regionCode(34)
        .age(2010)
        .classicRating(1588)
        .fideId(34400370)
        .fideClassicRating(1330)
        .blitzRating(1411)
        .build();
    Player playerAfter2 = Player.builder()
        .id(149947)
        .name("Иванов Илья")
        .sex("М")
        .regionCode(34)
        .age(2010)
        .classicRating(1000)
        .build();
    List<Player> expected = List.of(playerAfter1, playerAfter2);
    List<Player> beforePlayers = List.of(playerBefore1, playerBefore2);
    String blitzPlayer1 = "149945,Джалилова Алина,f,RUS,34,Волгоградская область,2010,1411,34400370,1230";
    String blitzPlayer2 = "149947,Иванов Илья,,RUS,34,Волгоградская область,2010,,,";
    List<String> blitzPlayers = List.of(blitzPlayer1, blitzPlayer2);
    String rapid = "blitz";
    Method method = PlayerIncomeUtil.class.getDeclaredMethod("addToPlayersRapidAndBlitzRating",
        List.class, List.class, String.class);
    method.setAccessible(true);
    method.invoke(new PlayerIncomeUtil(appConfig), beforePlayers, blitzPlayers, rapid);
    Assertions.assertThat(beforePlayers).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @DisplayName("Add Rapid rating to players")
  void addToPlayersRapidRating() throws Exception {
    Player playerBefore1 = Player.builder()
        .id(149945)
        .name("Джалилова Алина")
        .sex("Ж")
        .regionCode(34)
        .age(2010)
        .classicRating(1588)
        .fideId(34400370)
        .fideClassicRating(1330)
        .build();
    Player playerBefore2 = Player.builder()
        .id(149947)
        .name("Иванов Илья")
        .sex("М")
        .regionCode(34)
        .age(2010)
        .classicRating(1000)
        .build();
    Player playerAfter1 = Player.builder()
        .id(149945)
        .name("Джалилова Алина")
        .sex("Ж")
        .regionCode(34)
        .age(2010)
        .classicRating(1588)
        .fideId(34400370)
        .fideClassicRating(1330)
        .rapidRating(1470)
        .build();
    Player playerAfter2 = Player.builder()
        .id(149947)
        .name("Иванов Илья")
        .sex("М")
        .regionCode(34)
        .age(2010)
        .classicRating(1000)
        .rapidRating(1078)
        .build();
    List<Player> expected = List.of(playerAfter1, playerAfter2);
    List<Player> beforePlayers = List.of(playerBefore1, playerBefore2);
    String rapidPlayer1 = "149945,Джалилова Алина,f,RUS,34,Волгоградская область,2010,1470,34400370,1245";
    String rapidPlayer2 = "149947,Иванов Илья,,RUS,34,Волгоградская область,2010,1078,,";
    List<String> rapidPlayers = List.of(rapidPlayer1, rapidPlayer2);
    String rapid = "rapid";
    Method method = PlayerIncomeUtil.class.getDeclaredMethod("addToPlayersRapidAndBlitzRating",
        List.class, List.class, String.class);
    method.setAccessible(true);
    method.invoke(new PlayerIncomeUtil(appConfig), beforePlayers, rapidPlayers, rapid);
    Assertions.assertThat(beforePlayers).usingRecursiveComparison().isEqualTo(expected);
  }

}