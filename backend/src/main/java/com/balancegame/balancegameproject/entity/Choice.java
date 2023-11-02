package com.balancegame.balancegameproject.entity;

import com.balancegame.balancegameproject.dto.ChoiceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId")
    private Game game;
    private String content;
    @OneToMany(mappedBy = "choice", cascade = CascadeType.REMOVE)
    private List<Invenstor> invenstors;
    public ChoiceDTO toDTO(){
        return ChoiceDTO.builder()
                .id(id)
                .gameId(game.getId())
                .content(content)
                .invenstorDTOS(invenstors.stream().map(s->s.toDTO()).toList())
                .build();
    }
    public Choice(Integer id){
        this.id = id;
    }
    public void setGame(Game game){
        this.game = game;
    }
}
