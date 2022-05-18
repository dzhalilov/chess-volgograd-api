package com.example.chessvolgograd.util;

import com.example.chessvolgograd.model.Player;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipInputStream;

@Slf4j
public class PlayerIncomeUtil {
    private PlayerIncomeUtil() {
    }

    private static int errors = 0;
    private static final String URL_PATH_CLASSIC = "https://ratings.ruchess.ru/api/smanager_standard.csv.zip";
    private static final String URL_PATH_RAPID = "https://ratings.ruchess.ru/api/smanager_rapid.csv.zip";
    private static final String URL_PATH_BLITZ = "https://ratings.ruchess.ru/api/smanager_blitz.csv.zip";
    private static final String region = "34";

    public static synchronized List<Player> populateFromAPI() {
        log.info("Start downloading classic file from URL={}", URL_PATH_CLASSIC);
        List<String> listOfPlayers34Classic = readZipFileFromRemote(URL_PATH_CLASSIC);
        List<Player> listOfPersonClassic = creatListOfPersonWithClassic(listOfPlayers34Classic);
        log.info("Downloading player from API was completed with {} errors. Read {} players.", errors, listOfPersonClassic.size());
        errors = 0;

        log.info("Start downloading rapid file from URL={}", URL_PATH_RAPID);
        List<String> listOfPlayers34Rapid = readZipFileFromRemote(URL_PATH_RAPID);
        addToPlayersRapidRating(listOfPersonClassic, listOfPlayers34Rapid);
        log.info("Downloading player from API was completed with {} errors. Read {} players.", errors, listOfPersonClassic.size());
        errors = 0;

        log.info("Start downloading rapid file from URL={}", URL_PATH_BLITZ);
        List<String> listOfPlayers34Blitz = readZipFileFromRemote(URL_PATH_BLITZ);
        addToPlayersBlitzRating(listOfPersonClassic, listOfPlayers34Blitz);
        log.info("Downloading player from API was completed with {} errors. Read {} players.", errors, listOfPersonClassic.size());

        return listOfPersonClassic;
    }

    private static synchronized void addToPlayersRapidRating(List<Player> playerList, List<String> listFromAPI) {
        List<Player> listOfPerson = new ArrayList<>();
        Player player;
        for (String line : listFromAPI) {
            String[] temp = line.split(",");
            int rapidRating = 0;
            int id = 0;
            try {
                id = Integer.parseInt(temp[0]);
                rapidRating = Integer.parseInt(temp[7]);
                player = new Player(id, rapidRating);
                listOfPerson.add(player);
            } catch (Exception e) {
                errors++;
                System.out.println("Ошибка обработки строки: " + line + e.getMessage());
            }
        }
        for (Player value : playerList) {
            for (Player player1 : listOfPerson) {
                if (value.getId().equals(player1.getId())) {
                    value.setRapidRating(player1.getRapidRating());
                    break;
                }
            }
        }
    }

    private static synchronized void addToPlayersBlitzRating(List<Player> playerList, List<String> listFromAPI) {
        List<Player> listOfPerson = new ArrayList<>();
        Player player;
        for (String line : listFromAPI) {
            String[] temp = line.split(",");
            int blitzRating;
            int id;
            try {
                id = Integer.parseInt(temp[0]);
                blitzRating = Integer.parseInt(temp[7]);
                player = new Player(id, blitzRating, "Constructor for blitz");
                listOfPerson.add(player);
            } catch (Exception e) {
                errors++;
                System.out.println("Ошибка обработки строки: " + line + e.getMessage());
            }
        }
        for (Player value : playerList) {
            for (Player player1 : listOfPerson) {
                if (value.getId().equals(player1.getId())) {
                    value.setBlitzRating(player1.getBlitzRating());
                    break;
                }
            }
        }
    }

    private static synchronized List<Player> creatListOfPersonWithClassic(List<String> list) {
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
                player = new Player(id, name, sex, regionCode, age, rating, fideId, fideRating);
            } catch (Exception e) {
                System.out.println("Ошибка обработки строки: " + line + e.getMessage());
                errors++;
            }
            listOfPerson.add(player);
        }
        return listOfPerson;
    }

    private static synchronized List<String> readZipFileFromRemote(String urlPath) {
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
        String result = baos.toString(java.nio.charset.StandardCharsets.UTF_8);
        return Arrays.stream(result.split("\\n"))
                .filter(x -> x.split(",").length >= 8)
                .filter(x -> x.split(",")[4].equals(PlayerIncomeUtil.region))
                .toList();
    }
}
