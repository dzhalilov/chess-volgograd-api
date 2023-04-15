package com.example.chessvolgograd.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PlayerSearchCriteria {

  private String name;
  private String sex;
  private Integer ageStart;
  private Integer ageEnd;

  private PlayerOrder order = PlayerOrder.CLASSIC;
  private Integer pageNumber = 0;
  private Integer pageSize = 50;
}
