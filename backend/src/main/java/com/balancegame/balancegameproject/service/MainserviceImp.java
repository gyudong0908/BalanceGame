package com.balancegame.balancegameproject.service;

import com.balancegame.balancegameproject.dto.ChoiceDTO;
import com.balancegame.balancegameproject.dto.GameDTO;
import com.balancegame.balancegameproject.dto.ReviewDTO;
import com.balancegame.balancegameproject.dto.UserinfoDTO;
import com.balancegame.balancegameproject.entity.*;
import com.balancegame.balancegameproject.jwt.JwtService;
import com.balancegame.balancegameproject.repository.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class MainserviceImp implements MainService{
    GameRepository gameRepository;
    ChoiceRepository choiceRepository;
    InvenstorRepository invenstorRepository;
    UserInfoRepository userInfoRepository;
    ReviewRepository reviewRepository;
    JwtService jwtService = new JwtService();
    @Autowired
    MainserviceImp(GameRepository gameRepository, ChoiceRepository choiceRepository, InvenstorRepository invenstorRepository, UserInfoRepository userInfoRepository, ReviewRepository reviewRepository){
        this.gameRepository = gameRepository;
        this.choiceRepository = choiceRepository;
        this.invenstorRepository = invenstorRepository;
        this.userInfoRepository = userInfoRepository;
        this.reviewRepository = reviewRepository;
    }
    @Override
    public GameDTO view(){
        return gameRepository.getById(1).toDTO();
    }
    @Override
    @Transactional
    public void createGame(GameDTO gameDTO){
        Game game = gameDTO.toEntity();
        List<ChoiceDTO> choices = gameDTO.getChoices();
        gameRepository.save(game);
        for(ChoiceDTO choiceDTO :choices){
            Choice choice = choiceDTO.toEntity();
            choice.setGame(game);
            choiceRepository.save(choice);
        }
    }
    @Override
    public void createReview(){}
    @Override
    public Page<GameDTO> readGame(Pageable pageable, String state) {
        int page = pageable.getPageNumber() -1;
        int pageLimit = 2; // 한페이지에 보여 줄 갯수
        Page <Game> games;
        if(state.equals("ASC")){
            System.out.println("됨");
            games = gameRepository.findAll(PageRequest.of(page, pageLimit,Sort.by(Sort.Direction.ASC,"startTime")));
        }else{
            games = gameRepository.findAll(PageRequest.of(page, pageLimit,Sort.by(Sort.Direction.DESC,"startTime")));
        }
        List<Integer> totalPoints = new ArrayList<>();
        for(Game game : games){
            for (Choice choice: game.getChoices()){
                totalPoints.add(choiceRepository.getTotalPointByChoice(choice));
            }
        }

        Page<GameDTO> gameDTOS = games.map(game -> game.toDTO());
        int i = 0;
        for(GameDTO gameDTO : gameDTOS){
            for(ChoiceDTO choiceDTO :gameDTO.getChoices()){
                choiceDTO.setTotalPoint(totalPoints.get(i));
                i++;
            }
        }
        return gameDTOS;
    }
    @Override
    public void investPoint(String token, Integer choiceId, Integer point){
        String id = jwtService.getId(token);
        Choice choice = choiceRepository.getById(choiceId);
        Userinfo userinfo = userInfoRepository.getById(Integer.parseInt(id));
        userinfo.setPoint(userinfo.getPoint() - point);
        invenstorRepository.save(new Invenstor(userinfo,choice, point));
        userInfoRepository.save(userinfo);
    }
    @Override
    public UserinfoDTO getUserInfo(String token){
        try{
            int userId = Integer.parseInt(jwtService.getId(token));
            return userInfoRepository.getById(userId).toDTO();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }
    @Override
    @Transactional
    public UserinfoDTO userLogin(String email, String nickname){
        UserinfoDTO userinfoDTO;

        if(userInfoRepository.getByEmail(email) == null){
            userinfoDTO = userInfoRepository.save(new Userinfo(email,nickname,1000)).toDTO();

        }else{
            userinfoDTO = userInfoRepository.getByEmail(email).toDTO();
        }
        return userinfoDTO;
    }

    @Override
    public void addReview(String token, ReviewDTO reviewDTO){
        String id = jwtService.getId(token);
        Review review = reviewDTO.toEntity();
        review.setUserInfo(new Userinfo(Integer.parseInt(id)));
        reviewRepository.save(review);
    }

    @Override
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void removeGameData(){
        LocalDateTime deleteTime = LocalDateTime.now().minusHours(1);
        List<Game> removeGames = gameRepository.getRemoveGame(deleteTime);
        List<Invenstor> winInvenstors = new ArrayList<>();
        int firstTotalPoint = 0;
        int secondTotalPoint = 0;
        double rate;

        // 포인트 분배 로직
        for(Game game :removeGames){
            System.out.println(removeGames);
            firstTotalPoint = choiceRepository.getTotalPointByChoice(game.getChoices().get(0)) == null? 0 : choiceRepository.getTotalPointByChoice(game.getChoices().get(0));
            secondTotalPoint = choiceRepository.getTotalPointByChoice(game.getChoices().get(1)) == null? 0 : choiceRepository.getTotalPointByChoice(game.getChoices().get(1));
            if(firstTotalPoint > secondTotalPoint){
                winInvenstors.addAll(game.getChoices().get(0).getInvenstors());
                rate = (firstTotalPoint+secondTotalPoint )/  (double) firstTotalPoint;
                invenstorRepository.dividePoint(rate,game.getChoices().get(0).getId());
            } else if (firstTotalPoint < secondTotalPoint) {
                winInvenstors.addAll(game.getChoices().get(1).getInvenstors());
                rate = (firstTotalPoint+secondTotalPoint) / (double) secondTotalPoint;
                invenstorRepository.dividePoint(rate,game.getChoices().get(1).getId());
            } else if (firstTotalPoint == secondTotalPoint) {
                winInvenstors.addAll(game.getChoices().get(0).getInvenstors());
                winInvenstors.addAll(game.getChoices().get(1).getInvenstors());
                rate = 1;
                invenstorRepository.dividePoint(rate,game.getChoices().get(0).getId());
                invenstorRepository.dividePoint(rate,game.getChoices().get(1).getId());
            }
        }
        // 시간이 지난 게임 삭제하는 부분
        gameRepository.deleteAll(removeGames);
    }
    @Scheduled(cron = "0 0 00 * * *")
    public void addPoint(){
        userInfoRepository.addUserDefaultPoint();
    }
}
