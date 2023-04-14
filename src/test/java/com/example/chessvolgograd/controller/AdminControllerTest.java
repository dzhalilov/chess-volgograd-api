package com.example.chessvolgograd.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.chessvolgograd.model.Player;
import com.example.chessvolgograd.repository.PlayerRepository;
import com.example.chessvolgograd.util.PlayerIncomeUtil;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AdminController.class)
@ContextConfiguration
public class AdminControllerTest {

  @MockBean
  private PlayerRepository playerRepository;
  @MockBean
  private PlayerIncomeUtil playerIncomeUtil;
  @Autowired
  private MockMvc mockMvc;

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

  @Test
  void authenticationError_401() throws Exception {
    mockMvc.perform(post("/rest/admin/ratings"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = {"ADMIN"})
  void authenticationError_201() throws Exception {
    List<Player> players = List.of(alina, artem);
    Mockito.when(playerIncomeUtil.populateFromAPI()).thenReturn(players);
    mockMvc.perform(post("/rest/admin/ratings"))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(
            MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(players.size())));
  }

}