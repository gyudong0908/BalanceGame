import { useLocation} from "react-router-dom";
import AddModal from "../component/addModal";
import Game from "../component/game";
import KakaoImg from "../img/kakao_login_medium_narrow.png";
import { useEffect, useState } from "react";
import { Button } from "react-bootstrap";
import axios from "axios";
import '../css/mainPage.css';
import { useUserInfo, useGameData } from "../context";
import InfiniteScroll from "react-infinite-scroller";

function MainPage(){
    const [userInfo, setUserInfo] = useUserInfo();
    const [ gameData, setGameData] = useGameData();
    const [isLoading, setIsLoading] = useState(false);
    let [sortButtonTogle,setSortButtonTogle] = useState(true);
    const [page,setPage] = useState(1);

    function getData(){
        axios.get(`http://localhost:8080?page=${page}&state=${sortButtonTogle? 'DESC':'ASC'}`).then(response=>{
                console.log(1)           
                setGameData([...gameData,...response.data.content]);
                setPage(page+1);
                setIsLoading(true);
        }).catch(error=>{
                alert("error 발생: " + error.message);
                console.log(error);    
        });

    }

    function getUserInfo(){
        axios.get(`http://localhost:8080/userInfo`,{ withCredentials: true }).then((res)=>{
                setUserInfo({
                    'userId' : res.data.id,
                    'nickName' : res.data.nickName,
                    'point' : res.data.point
                });
            })
    }

    function sortGameData(){
        setIsLoading(false);
        if(sortButtonTogle){
            axios.get(`http://localhost:8080?page=1&state=ASC`).then(response=>{           
                if(response.data.content.length === 0){
                    alert("게임이 더 없습니다.");
                    return;
                }
                setGameData(response.data.content);
                setPage(2);
        }).catch(error=>{
                alert("error 발생: " + error.message);
                console.log(error);    
        });
            setSortButtonTogle(false);
        }else{
            axios.get(`http://localhost:8080?page=1&state=DESC`).then(response=>{           
                if(response.data.content.length === 0){
                    alert("게임이 더 없습니다.");
                    return;
                }
                setGameData(response.data.content);
                setPage(2);
        }).catch(error=>{
                alert("error 발생: " + error.message);
                console.log(error);    
        });

            setSortButtonTogle(true);
        }
    }

    useEffect(()=>{
        getUserInfo();
    },[])

    return(
        <InfiniteScroll
            pageStart={1}
            loadMore={getData}
            hasMore={true}
            // loader={<div className="loader" key={0}>Loading ...</div>}
            >
            {
                isLoading? (
                    <div className="mainPage-div">
                        <div className="main-game-div">
                        <AddModal></AddModal>
                        <h2>Balance Game</h2>
                        <Button variant="primary" onClick={()=>{sortGameData();}}>마감 순</Button>
                        <Game getData = {getData} ></Game>
                    </div>        
                    <div className="Login-div">
                        {            
                            getCookie("token") === undefined?
                            <div>
                                <h2>로그인</h2>
                                <a href="http://localhost:8080/oauth2/authorization/kakao">
                                    <img src={KakaoImg} alt="" />
                                </a>
                            </div> :
                            <div><h3>유저 정보</h3><p>이름: {userInfo.nickName}</p><p>보유 포인트: {userInfo.point}p</p>
                                <a href="http://localhost:8080/logout">로그아웃</a>
                            </div>
                        }
                    </div>
                </div>
                ) : null
            }
        </InfiniteScroll>
    )
}


function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
  }

export default MainPage;