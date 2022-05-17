package com.example.chessvolgograd.controller;

import com.example.chessvolgograd.model.Player;
import com.example.chessvolgograd.model.SearchCriteria;
import com.example.chessvolgograd.repository.PlayerRepository;
import com.example.chessvolgograd.service.PlayerService;
import com.example.chessvolgograd.util.PlayerSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = UserController.REST_USER_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "players")
@CrossOrigin("http://localhost:8081/")
public class UserController {
    static final String REST_USER_URL = "rest/players";

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerService playerService;

//    @GetMapping
//    @Cacheable
//    public List<Player> getAll() {
//        return playerRepository.findAll();
//    }

    @GetMapping
    @Cacheable
    public List<Player> getAll(@RequestParam Map<String, String> params) {
        return playerService.getPlayersWithFilter(params);
//        PlayerSpecification spec1 = new PlayerSpecification(new SearchCriteria("sex", ":", "Ж"));
//        PlayerSpecification spec2 = new PlayerSpecification(new SearchCriteria("age", ":", "2010"));
//        return playerRepository.findAll(Specification.where(spec1).and(spec2));
//        return playerRepository.findAll(Sort.by(Sort.Direction.DESC, "classicRating"));
    }

    @GetMapping("/count")
    @Cacheable
    public long getCount() {
        return playerRepository.count();
    }
}
