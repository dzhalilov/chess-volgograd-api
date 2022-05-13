package com.example.chessvolgograd.repository;

import com.example.chessvolgograd.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
