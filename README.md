# BalanceGame
## 프로젝트 ppt
[밸런스게임.pdf](https://github.com/gyudong0908/BalanceGame/files/13682595/default.pdf)
## 메인 화면
![image](https://github.com/gyudong0908/BalanceGame/assets/121427661/caa0bbad-a8d2-4d70-b46a-d6d70b056679)
## 기능
1. 게임 생성 후 1시간이 지나면 게임이 종료되며 더 많이 건 point의 유저들에게 point 분배
2. kako Oauth2를 이용한 로그인
3. 매 자정을 기준으로 모든 사용자에게 1000pt 배분
## 어려웠던 점
1. 게임 생성 후 1시간이 지나면 게임이 종료되며 point를 분배하는 로직
   - 위에 로직이 어려웠던 이유는 게임 생성시 1시간이 지났는지를 확인 하는 과정이었습니다. 매초마다 또는 매 분마다 1시간이 지났는지를 확인하기에는 많은 과부하가 걸릴거 같아 5분을 기점으로 확인하는 것으로 구현했습니다
