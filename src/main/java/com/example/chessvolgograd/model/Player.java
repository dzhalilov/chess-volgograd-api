package com.example.chessvolgograd.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "players")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Player {

  @Id
  private Integer id;
  private String name;
  private String sex;
  private int regionCode;
  private int age;
  private int classicRating;
  private int rapidRating;
  private int blitzRating;
  private int fideId;
  private int fideClassicRating;

  @Override
  public String toString() {
    return "Person{" +
        "ID РШФ=" + id +
        ", Имя='" + name + '\'' +
        ", Пол=" + sex +
        ", Код региона=" + regionCode +
        ", Год рождения=" + age +
        ", Рейтинг РШФ классика=" + classicRating +
        ", Рейтинг РШФ рапид=" + rapidRating +
        ", Рейтинг РШФ блиц=" + blitzRating +
        ", FIDE ID=" + fideId +
        ", Рейтинг FIDE=" + fideClassicRating +
        '}';
  }
}
