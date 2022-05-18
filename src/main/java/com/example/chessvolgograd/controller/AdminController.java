package com.example.chessvolgograd.controller;

import com.example.chessvolgograd.model.Player;
import com.example.chessvolgograd.repository.PlayerRepository;
import com.example.chessvolgograd.util.PlayerIncomeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = AdminController.REST_URL)
@Slf4j
public class AdminController {
    static final String REST_URL = "rest/admin";

    @Autowired
    PlayerRepository playerRepository;

    @PostMapping("/ratings")
    public ResponseEntity<Integer> update() {
        log.info("Start updating base in {}", LocalDateTime.now());
        List<Player> players = PlayerIncomeUtil.populateFromAPI();
        players.forEach(System.out::println);
        playerRepository.saveAll(players);
        log.info("End updating base in {}", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(players.size());
    }
}
