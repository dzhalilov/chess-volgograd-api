package com.example.chessvolgograd.controller;

import com.example.chessvolgograd.model.Player;
import com.example.chessvolgograd.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
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
public class UserController {
    static final String REST_USER_URL = "rest/players";

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping
    @Cacheable
    public List<Player> getAll() {
        return playerRepository.findAll(Sort.by(Sort.Direction.DESC, "classicRating"));
//        return playerRepository.findAll();
    }

    @GetMapping("/count")
    @Cacheable
    public long getCount() {
        return playerRepository.count();
    }
}
