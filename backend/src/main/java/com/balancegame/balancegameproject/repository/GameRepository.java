package com.balancegame.balancegameproject.repository;

import com.balancegame.balancegameproject.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    @Query(value = "SELECT * FROM game where game.starttime < :dateTime", nativeQuery = true)
    List<Game> getRemoveGame(@Param("dateTime")LocalDateTime  dateTime);
    @Modifying
    @Transactional
    @Query(value = "delete from Game where startTime < :dateTime")
    void deleteGameOverTime(@Param("dateTime")LocalDateTime dateTime);
}