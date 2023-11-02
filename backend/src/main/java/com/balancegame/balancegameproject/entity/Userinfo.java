package com.balancegame.balancegameproject.entity;


import com.balancegame.balancegameproject.dto.UserinfoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Userinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private Integer point;
    @Column(name = "nickname")
    private String nickName;
    public Userinfo(Integer id){
        this.id = id;
    }
    public Userinfo(String email, String nickName, Integer point){ this.email = email; this.nickName = nickName; this.point = point;}
    public void setPoint(Integer point){
        this.point = point;
    }
    public UserinfoDTO toDTO(){
        return UserinfoDTO.builder()
                .id(id)
                .email(email)
                .point(point)
                .nickName(nickName)
                .build();
    }
}
