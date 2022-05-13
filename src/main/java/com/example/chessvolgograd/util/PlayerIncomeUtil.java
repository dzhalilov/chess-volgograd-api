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
    private static final String URL_PATH = "https://ratings.ruchess.ru/api/smanager_standard.csv.zip";
    private static final String region = "34";

    public static List<Player> populateFromAPI() {
        log.info("Start downloading file from URL={}", URL_PATH);
        List<String> listOfPlayers34 = readZipFileFromRemote();
        log.info("File was downloaded and unziped");
        List<Player> listOfPerson = creatListOfPerson(listOfPlayers34);
        log.info("Downloading player from API was completed with {} errors. Read {} players.", errors, listOfPerson.size());

        System.out.println("############################################");
        listOfPerson.forEach(System.out::println);
        return listOfPerson;
    }

    private static List<Player> creatListOfPerson(List<String> list) {
        List<Player> listOfPerson = new ArrayList<>();
        Player player = null;
        for (String line : list) {
            String[] temp = line.split(",");
            int rating = 0;
            int fideRating = 0;
            String fideId = "";
            try {
                int id = Integer.parseInt(temp[0]);
                String name = temp[1];
                String sex = temp[2].equals("f") ? "Ж" : "М";
                String country = temp[3];
                int regionCode = Integer.parseInt(temp[4]);
                String region = temp[5];
                int age = Integer.parseInt(temp[6]);
                if (!temp[7].equals("")) {
                    rating = Integer.parseInt(temp[7]);
                }
                if (temp.length == 8) {
                    fideRating = 0;
                } else {
                    fideId = temp[8];
                    if (!line.endsWith(",")) {
                        fideRating = Integer.parseInt(temp[9]);
                    }
                }
                player = new Player(id, name, sex, country, regionCode, region, age, rating, fideId, fideRating);
            } catch (Exception e) {
                System.out.println("Ошибка обработки строки: " + line + e.getMessage());
                errors++;
            }
            listOfPerson.add(player);
        }
        return listOfPerson;
    }

    private static List<String> readZipFileFromRemote() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            URL url = new URL(PlayerIncomeUtil.URL_PATH);
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
