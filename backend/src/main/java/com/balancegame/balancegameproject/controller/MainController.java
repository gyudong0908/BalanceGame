package com.balancegame.balancegameproject.controller;

import com.balancegame.balancegameproject.dto.GameDTO;
import com.balancegame.balancegameproject.dto.ReviewDTO;
import com.balancegame.balancegameproject.dto.UserinfoDTO;
import com.balancegame.balancegameproject.entity.Game;
import com.balancegame.balancegameproject.service.MainService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {
    MainService mainService;
    @Autowired
    MainController(MainService mainService){
        this.mainService = mainService;
    }
    @GetMapping("/")
    public Page<GameDTO> readData(@PageableDefault(page=1) Pageable pageable, @RequestParam String state) {
        Page<GameDTO> gameDTOS = mainService.readGame(pageable, state);
        System.out.println(gameDTOS);
        return gameDTOS;
    }
    @PostMapping("game")
    public void createGame(@RequestBody GameDTO gameDTO){ mainService.createGame(gameDTO);}
    @GetMapping("invest")
    public void investPoint(@RequestParam Integer point, @RequestParam Integer choiceId, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 원하는 쿠키 이름과 일치하는 경우 해당 쿠키의 값을 얻음
                if ("token".equals(cookie.getName())) {
                    System.out.println(cookie.getValue());
                    mainService.investPoint(cookie.getValue(),choiceId,point);
                }
            }
        }
    }
    @PostMapping("review")
    public void addReview(@RequestBody ReviewDTO reviewDTO, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 원하는 쿠키 이름과 일치하는 경우 해당 쿠키의 값을 얻음
                System.out.println(cookie);
                if ("token".equals(cookie.getName())) {
                    System.out.println(cookie.getValue());
                    token = cookie.getValue();
                }
            }
        }
        mainService.addReview(token,reviewDTO);
    }

    @GetMapping("userInfo")
    public UserinfoDTO getUserInfo(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 원하는 쿠키 이름과 일치하는 경우 해당 쿠키의 값을 얻음
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        if(token != null){
            UserinfoDTO userinfoDTO  = mainService.getUserInfo(token);
            if(userinfoDTO == null){
                Cookie cookie = new Cookie("token", null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            return userinfoDTO;
        }else{
            return null;
        }
    }
}
