package com.example.chessvolgograd.util;

import com.example.chessvolgograd.config.AppConfig;
import com.example.chessvolgograd.model.Player;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipInputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerIncomeUtil {

  private final AppConfig appConfig;
  private int errors = 0;

  public List<Player> populateFromAPI() {
    log.info("Start downloading classic file from URL={}", appConfig.getUrlPathClassic());
    List<String> listOfPlayers34Classic = readZipFileFromRemote(appConfig.getUrlPathClassic());
    List<Player> listOfPersonClassic = creatListOfPersonWithClassic(listOfPlayers34Classic);
    log.info("Downloading player from API was completed with {} errors. Read {} players.", errors,
        listOfPersonClassic.size());
    errors = 0;

    log.info("Start downloading rapid file from URL={}", appConfig.getUrlPathRapid());
    List<String> listOfPlayers34Rapid = readZipFileFromRemote(appConfig.getUrlPathRapid());
    addToPlayersRapidAndBlitzRating(listOfPersonClassic, listOfPlayers34Rapid, "rapid");
    log.info("Downloading player from API was completed with {} errors. Read {} players.", errors,
        listOfPersonClassic.size());
    errors = 0;

    log.info("Start downloading rapid file from URL={}", appConfig.getUrlPathBlitz());
    List<String> listOfPlayers34Blitz = readZipFileFromRemote(appConfig.getUrlPathBlitz());
    addToPlayersRapidAndBlitzRating(listOfPersonClassic, listOfPlayers34Blitz, "blitz");
    log.info("Downloading player from API was completed with {} errors. Read {} players.", errors,
        listOfPersonClassic.size());

    return listOfPersonClassic;
  }

  private void addToPlayersRapidAndBlitzRating(List<Player> playerList,
      List<String> listFromAPI, String typeOfGame) {
    for (String line : listFromAPI) {
      String[] temp = line.split(",");
      int rating;
      int id;
      try {
        id = Integer.parseInt(temp[0]);
        rating = Integer.parseInt(temp[7]);
        int index = Collections.binarySearch(playerList, Player.builder().id(id).build(),
            Comparator.comparing(Player::getId));
        if (index >= 0) {
          if (typeOfGame.equals("rapid")) {
            playerList.get(index).setRapidRating(rating);
          } else if (typeOfGame.equals("blitz")) {
            playerList.get(index).setBlitzRating(rating);
          }
        }
      } catch (Exception e) {
        errors++;
        log.error("Ошибка обработки строки: " + line + e.getMessage());
      }
    }
  }

  private List<Player> creatListOfPersonWithClassic(List<String> list) {
    List<Player> listOfPerson = new ArrayList<>();
    Player player = null;
    for (String line : list) {
      String[] temp = line.split(",");
      int rating = 0;
      int fideRating = 0;
      int fideId = 0;
      try {
        int id = Integer.parseInt(temp[0]);
        String name = temp[1];
        String sex = temp[2].equals("f") ? "Ж" : "М";
        int regionCode = Integer.parseInt(temp[4]);
        int age = Integer.parseInt(temp[6]);
        if (!temp[7].equals("")) {
          rating = Integer.parseInt(temp[7]);
        }
        if (temp.length == 8) {
        } else {
          fideId = Integer.parseInt(temp[8]);
          if (!line.endsWith(",")) {
            fideRating = Integer.parseInt(temp[9]);
          }
        }
        player = Player.builder()
            .id(id)
            .name(name)
            .sex(sex)
            .regionCode(regionCode)
            .age(age)
            .classicRating(rating)
            .fideId(fideId)
            .fideClassicRating(fideRating)
            .build();
      } catch (Exception e) {
        log.error("Ошибка обработки строки: " + line + e.getMessage());
        errors++;
      }
      listOfPerson.add(player);
    }
    return listOfPerson;
  }

  private List<String> readZipFileFromRemote(String urlPath) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      URL url = new URL(urlPath);
      InputStream in = new BufferedInputStream(url.openStream(), 1024);
      ZipInputStream stream = new ZipInputStream(in, StandardCharsets.UTF_8);
      byte[] buffer = new byte[1024];
      while (stream.getNextEntry() != null) {
        int read;
        while ((read = stream.read(buffer, 0, 1024)) >= 0) {
          baos.write(buffer, 0, read);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    String result = baos.toString(StandardCharsets.UTF_8);
    return Arrays.stream(result.split("\\n"))
        .filter(x -> x.split(",").length >= 8)
        .filter(x -> x.split(",")[4].equals(appConfig.getRegion()))
        .toList();
  }
}
