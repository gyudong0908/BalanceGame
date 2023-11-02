package com.balancegame.balancegameproject.dto;

import com.balancegame.balancegameproject.entity.Choice;
import com.balancegame.balancegameproject.entity.Game;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EntityListeners;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GameDTO {
    private Integer id;
    private String title;
    private LocalDateTime startTime;
    @JsonIgnoreProperties("choices")
    private List<ChoiceDTO> choices;
    private List<ReviewDTO> reviewDTOS;
    public Game toEntity(){
        return Game.builder()
                .id(id)
                .title(title)
                .startTime(startTime)
                .choices(null)
                .build();
    }
}
