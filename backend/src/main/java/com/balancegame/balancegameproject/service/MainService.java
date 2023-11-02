package com.balancegame.balancegameproject.service;

import com.balancegame.balancegameproject.dto.GameDTO;
import com.balancegame.balancegameproject.dto.ReviewDTO;
import com.balancegame.balancegameproject.dto.UserinfoDTO;
import com.balancegame.balancegameproject.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MainService {
    GameDTO view();
    void createGame(GameDTO gameDTO);
    void createReview();
    Page<GameDTO> readGame(Pageable pageable, String state);
    void investPoint(String token, Integer choiceid, Integer point);
    UserinfoDTO getUserInfo(String token);
    void removeGameData();
    UserinfoDTO userLogin(String email, String nickname);
    void addPoint();
    void addReview(String token, ReviewDTO reviewDTO);
}
