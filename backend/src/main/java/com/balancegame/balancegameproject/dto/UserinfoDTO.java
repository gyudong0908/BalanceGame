package com.balancegame.balancegameproject.dto;

import com.balancegame.balancegameproject.entity.Userinfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserinfoDTO {
    private Integer id;
    private String email;
    private Integer point;
    private String nickName;
    public Userinfo toEntity(){
        return Userinfo.builder()
                .id(id)
                .email(email)
                .point(point)
                .nickName(nickName)
                .build();
    }
}