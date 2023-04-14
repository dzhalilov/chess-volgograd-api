package com.example.chessvolgograd.service;

import com.example.chessvolgograd.model.Player;
import com.example.chessvolgograd.model.PlayerOrder;
import com.example.chessvolgograd.model.PlayerSearchCriteria;
import com.example.chessvolgograd.repository.PlayerRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PlayerServiceTest {

  @Mock
  private PlayerRepository playerRepository;
  private PlayerService service;
  private PlayerSearchCriteria criteria;

  @Captor
  private ArgumentCaptor<Specification<Player>> specificationArgumentCaptor;

  @BeforeEach
  public void setup() {
    service = new PlayerService(playerRepository);
    criteria = PlayerSearchCriteria.builder()
        .name("Дж")
        .order(PlayerOrder.CLASSIC)
        .ageStart(2008)
        .ageEnd(2050)
        .pageSize(50)
        .pageNumber(1)
        .build();

  }

  @Test
  void getPlayersWithFilter() {
  }

  @Test
  @DisplayName("Count players by criteria")
  void getCount() {
    Mockito.when(playerRepository.findAll(specificationArgumentCaptor.capture()))
        .thenReturn(List.of(new Player(), new Player()));
    Integer actualNumber = service.getCount(criteria);
    Assertions.assertEquals(2, actualNumber);
  }
}