package com.balancegame.balancegameproject.entity;

import com.balancegame.balancegameproject.dto.InvenstorDTO;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Invenstor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Userinfo userInfo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choiceId")
    private Choice choice;
    private Integer investPoint;
    public Invenstor(Userinfo userinfo, Choice choice, Integer investPoint){
        this.userInfo = userinfo;
        this.choice =choice;
        this.investPoint = investPoint;
    }
    public InvenstorDTO toDTO(){
        return InvenstorDTO.builder()
                .id(id)
                .userId(userInfo.getId())
                .choiceId(choice.getId())
                .investPoint(investPoint)
                .build();
    }
}
