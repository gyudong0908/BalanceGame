package com.balancegame.balancegameproject.dto;

import com.balancegame.balancegameproject.entity.Choice;
import com.balancegame.balancegameproject.entity.Game;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChoiceDTO {
    private Integer id;
    private Integer gameId;
    private String content;
    private Integer totalPoint;
    private List<InvenstorDTO> invenstorDTOS;
    public Choice toEntity(){
        return Choice.builder()
                .id(id)
                .content(content)
                .game(new Game(gameId))
                .build();
    }
}
