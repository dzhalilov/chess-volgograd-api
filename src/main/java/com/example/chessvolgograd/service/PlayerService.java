package com.example.chessvolgograd.service;

import com.example.chessvolgograd.model.Player;
import com.example.chessvolgograd.model.PlayerOrder;
import com.example.chessvolgograd.model.PlayerSearchCriteria;
import com.example.chessvolgograd.repository.PlayerRepository;
import com.example.chessvolgograd.util.PlayerSpecificationsBuilder;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

  private final PlayerRepository playerRepository;

  public List<Player> getPlayersWithFilter(PlayerSearchCriteria criteria) {
    Specification<Player> spec = getSpecification(criteria);
    Pageable pageable = getPageable(criteria);
    return playerRepository.findAll(spec, pageable).toList();
  }

  public Integer getCount(PlayerSearchCriteria criteria) {
    Specification<Player> spec = getSpecification(criteria);
    return playerRepository.findAll(spec).size();
  }

  private Specification<Player> getSpecification(PlayerSearchCriteria criteria) {
    PlayerSpecificationsBuilder builder = new PlayerSpecificationsBuilder();
    if (Objects.nonNull(criteria.getName())) {
      builder.with("name", "%", criteria.getName());
    }
    if (Objects.nonNull(criteria.getAgeStart())) {
      builder.with("age", ">", criteria.getAgeStart());
    }
    if (Objects.nonNull(criteria.getAgeEnd())) {
      builder.with("age", "<", criteria.getAgeEnd());
    }
    if (Objects.nonNull(criteria.getSex())) {
      builder.with("sex", "=", criteria.getSex().equals("f") ? "Ж" : "М");
    }
    return builder.build();
  }

  private Pageable getPageable(PlayerSearchCriteria criteria) {
    Sort sort = criteria.getOrder().equals(PlayerOrder.ID) ?
        Sort.by(Sort.Direction.ASC, criteria.getOrder().label)
        : Sort.by(Sort.Direction.DESC, criteria.getOrder().label);
    return PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), sort);
  }
}