package com.example.chessvolgograd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "players")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {
    @Id
    private int id;
    private String name;
    private String sex;
    private String country;
    private int regionCode;
    private String region;
    private int age;
    private int rusRating;
    private String fideId;
    private int fideRating;

    @Override
    public String toString() {
        return "Person{" +
                "ID РШФ=" + id +
                ", Имя='" + name + '\'' +
                ", Пол=" + sex +
                ", Страна='" + country + '\'' +
                ", Код региона=" + regionCode +
                ", Регион='" + region + '\'' +
                ", Год рождения=" + age +
                ", Рейтинг РШФ=" + rusRating +
                ", FIDE ID=" + fideId +
                ", Рейтинг FIDE=" + fideRating +
                '}';
    }
}
