package com.example.chessvolgograd.controller;

import com.example.chessvolgograd.model.Player;
import com.example.chessvolgograd.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "players")
public class UserController {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping
    @Cacheable
    public List<Player> getAll() {
        return playerRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
