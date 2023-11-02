package com.balancegame.balancegameproject.entity;

import com.balancegame.balancegameproject.dto.ReviewDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Userinfo userInfo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId")
    private Game game;

    private String comment;
    public void setUserInfo(Userinfo userInfo) {
        this.userInfo = userInfo;
    }

    public ReviewDTO toDTO(){
        return ReviewDTO.builder()
                .id(id)
                .userinfoDTO(userInfo.toDTO())
                .comment(comment)
                .gameId(game.getId())
                .build();
    }
}
