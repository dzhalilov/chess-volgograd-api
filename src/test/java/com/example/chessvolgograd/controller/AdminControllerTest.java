package com.example.chessvolgograd.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.chessvolgograd.service.PlayerService;
import com.example.chessvolgograd.util.PlayerIncomeUtil;
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
  private PlayerService playerService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  void authenticationError_401() throws Exception {
    mockMvc.perform(post("/rest/admin/ratings"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = {"ADMIN"})
  void authenticationError_201() throws Exception {
    Mockito.when(playerService.populateFromAPI()).thenReturn(2);
    mockMvc.perform(post("/rest/admin/ratings"))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(
            MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(2)));
  }

}