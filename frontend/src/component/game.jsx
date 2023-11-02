import axios from "axios";
import { useEffect, useState } from "react";
import Button from 'react-bootstrap/Button';
import '../css/game.css';
import { useGameData, useUserInfo } from "../context";
import InvestPoint from "./investPoint";
import Comment from "./comment";

function Game(props){
    const [gameData] = useGameData();
    const [userInfo] = useUserInfo();
    
    return(
        <div className="gameList-div">
            {
                gameData.map((data,idx)=>{
                    return(
                        <div className="game-div">
                            {                                
                                (data.choices[0].invenstorDTOS.some(obj=>obj.userId == userInfo.userId)) || (data.choices[1].invenstorDTOS.some(obj=>obj.userId == userInfo.userId))?
                                <ChoceGame data = {data}></ChoceGame>:<NoChoiceGame data = {data} gameIdx = {idx}></NoChoiceGame>                    
                            }                            
                        </div>
                    )
                })
            }
        </div>
    )
}

function NoChoiceGame(props){
    const startTime = new Date(props.data.startTime);
    const endTime = new Date(startTime - new Date());
    return(
        <div class="row">
            <h2>마감시간: {endTime.getMinutes()}분</h2>
            <h2 class="card-title">{props.data.title}</h2>
            <div class="col-sm-6 mb-3 mb-sm-0">
                <div class="card">
                    <h5>{props.data.choices[0].content}</h5>
                </div>
                <div className="invest">
                        <InvestPoint title={props.data.title} data = {props.data.choices[0]}></InvestPoint>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="card">
                    <h5>{props.data.choices[1].content}</h5>
                </div>
                <div className="invest">
                    <InvestPoint title={props.data.title} data = {props.data.choices[1]}></InvestPoint>
                </div>
            </div>
            <div className="comment-div">
                <Comment data ={props.data}></Comment>
            </div>
        </div>
    )
}
function ChoceGame(props){
    const startTime = new Date(props.data.startTime)
    const endTime = new Date(startTime - new Date());
    return(
        <div class="row">
        <h2>마감시간: {endTime.getMinutes()}분</h2>
        <h2 class="card-title">{props.data.title}</h2>
        <div class="col-sm-6 mb-3 mb-sm-0">
            <div class="card">
                <h5>{props.data.choices[0].content}</h5>
                <div>총 포인트: {props.data.choices[0].totalPoint?props.data.choices[0].totalPoint:0}p</div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="card">
                <h5>{props.data.choices[0].content}</h5>
                <div> 총 포인트: {props.data.choices[1].totalPoint?props.data.choices[1].totalPoint:0}p</div>
            </div>
        </div>
        <div className="comment-div">
            <Comment data ={props.data}></Comment>
        </div>
    </div>
    )
}
export default Game;



