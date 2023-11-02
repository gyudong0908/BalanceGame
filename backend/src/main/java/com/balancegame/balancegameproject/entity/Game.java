package com.balancegame.balancegameproject.entity;

import com.balancegame.balancegameproject.dto.GameDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @CreatedDate
    @Column(name = "starttime")
    private LocalDateTime startTime;
    @OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE)
    private List<Choice> choices;
    @OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    public GameDTO toDTO(){
        return GameDTO.builder()
                .id(id)
                .title(title)
                .startTime(startTime)
                .choices(choices.stream().map(s->s.toDTO()).toList())
                .reviewDTOS(reviews.stream().map(s->s.toDTO()).toList())
                .build();
    }
    public Game(Integer id){
        this.id = id;
    }
}
