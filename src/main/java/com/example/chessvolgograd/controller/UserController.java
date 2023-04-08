package com.example.chessvolgograd.controller;

import com.example.chessvolgograd.model.Player;
import com.example.chessvolgograd.model.PlayerSearchCriteria;
import com.example.chessvolgograd.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = UserController.REST_USER_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "players")
@CrossOrigin("http://localhost:8081/")
@RequiredArgsConstructor
public class UserController {
    static final String REST_USER_URL = "rest/players";

    private final PlayerService playerService;

    @GetMapping
    @Cacheable
    public List<Player> getAllWithFilter(PlayerSearchCriteria criteria) {
        return playerService.getPlayersWithFilter(criteria);
    }

    @GetMapping("/count")
    @Cacheable
    public long getCount(PlayerSearchCriteria criteria) {
        return playerService.getCount(criteria);
    }
}
