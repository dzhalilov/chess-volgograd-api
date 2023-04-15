package com.example.chessvolgograd.controller;

import com.example.chessvolgograd.service.PlayerService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = AdminController.REST_URL)
@Slf4j
@RequiredArgsConstructor
public class AdminController {

  static final String REST_URL = "rest/admin";

  private final PlayerService playerService;

  @PostMapping("/ratings")
  public ResponseEntity<Integer> update() {
    log.info("Start updating base in {}", LocalDateTime.now());
    Integer populatedPlayers = playerService.populateFromAPI();
    log.info("End updating base in {}", LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.CREATED).body(populatedPlayers);
  }
}
