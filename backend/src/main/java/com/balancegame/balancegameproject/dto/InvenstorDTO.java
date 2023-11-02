package com.balancegame.balancegameproject.dto;

import com.balancegame.balancegameproject.entity.Choice;
import com.balancegame.balancegameproject.entity.Invenstor;
import com.balancegame.balancegameproject.entity.Userinfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvenstorDTO {
    private Integer id;
    private Integer userId;
    private Integer choiceId;
    private Integer investPoint;
    public Invenstor toEntity(){
        return Invenstor.builder()
                .id(id)
                .userInfo(new Userinfo(userId))
                .choice(new Choice(id))
                .investPoint(investPoint)
                .build();
    }
}
