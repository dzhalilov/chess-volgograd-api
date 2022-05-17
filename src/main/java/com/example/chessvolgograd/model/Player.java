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

    public Player(int id, String name, String sex, int regionCode, int age, int classicRating, int fideId, int fideClassicRating) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.regionCode = regionCode;
        this.age = age;
        this.classicRating = classicRating;
        this.fideId = fideId;
        this.fideClassicRating = fideClassicRating;
    }

    public Player(int id, int rapidRating) {
        this.id = id;
        this.rapidRating = rapidRating;
    }

    public Player(int id, int blitzRating, String constructor_for_blitz) {
        this.id = id;
        this.blitzRating = blitzRating;
    }

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
