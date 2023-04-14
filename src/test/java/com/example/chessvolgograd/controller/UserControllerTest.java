package com.example.chessvolgograd.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.chessvolgograd.model.Player;
import com.example.chessvolgograd.model.PlayerSearchCriteria;
import com.example.chessvolgograd.service.PlayerService;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

  @MockBean
  private PlayerService playerService;
  @Autowired
  private MockMvc mockMvc;

  private static final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
  private final Player alina = Player.builder()
      .age(2010)
      .name("Джалилова Алина")
      .regionCode(34)
      .classicRating(1588)
      .rapidRating(1470)
      .blitzRating(1411)
      .id(149945)
      .sex("Ж")
      .fideId(123456)
      .fideClassicRating(1000)
      .build();
  private final Player artem = Player.builder()
      .age(2008)
      .name("Джалилов Артём")
      .regionCode(34)
      .classicRating(1425)
      .rapidRating(1604)
      .blitzRating(1299)
      .id(149964)
      .sex("М")
      .fideId(123457)
      .fideClassicRating(1000)
      .build();

  @BeforeAll
  public static void init() {
    params.put("name", List.of("Джалилов"));
    params.put("order", List.of("CLASSIC"));
    params.put("pageNumber", List.of("0"));
    params.put("pageSize", List.of("50"));
  }

  @Test
  void getAllWithFilter() throws Exception {
    when(playerService.getPlayersWithFilter(
        ArgumentMatchers.any(PlayerSearchCriteria.class))).thenReturn(
        List.of(alina, artem));
    mockMvc.perform(get("/rest/players").params(params))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.size()", Matchers.is(2)))
        .andExpect(jsonPath("$.[0].age", Matchers.is(2010)))
        .andExpect(jsonPath("$.[1].age", Matchers.is(2008)));
  }

  @Test
  void getCount() throws Exception {
    when(playerService.getCount(ArgumentMatchers.any(PlayerSearchCriteria.class))).thenReturn(2);
    mockMvc.perform(get("/rest/players/count").params(params))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$", Matchers.is(2)));
  }
}