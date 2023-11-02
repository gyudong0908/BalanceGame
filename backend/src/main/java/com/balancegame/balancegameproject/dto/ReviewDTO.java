package com.balancegame.balancegameproject.dto;

import com.balancegame.balancegameproject.entity.Choice;
import com.balancegame.balancegameproject.entity.Game;
import com.balancegame.balancegameproject.entity.Review;
import com.balancegame.balancegameproject.entity.Userinfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDTO {
    private Integer id;
    private  UserinfoDTO userinfoDTO;
    private String comment;
    private Integer gameId;
    public Review toEntity(){
        return Review.builder()
                .id(id)
                .game(new Game(gameId))
                .comment(comment)
                .build();
    }
}
