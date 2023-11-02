import axios from 'axios';
import Accordion from 'react-bootstrap/Accordion';
import { useGameData } from '../context';
import '../css/comment.css';
import { useState } from 'react';
import { useUserInfo } from '../context';
function Comment(props) {
  const [reviewText,setReviewText] = useState('');
  const [data, setData] = useState(props.data.reviewDTOS);
  const [userInfo] = useUserInfo();
  function addReview(gameId){
    let reviewData = {
      "comment" : reviewText,
      "gameId" : gameId
    }
    axios.post("http://localhost:8080/review",reviewData,{ withCredentials: true }).then((res)=>{
        setData([...data, {"comment":reviewText,"userinfoDTO":{"nickName":userInfo.nickName}}]);
        setReviewText('');
    })
  }
  function mouseUpHandler(e){
      if(e.key ==='Enter'){
        e.preventDefault();
        addReview(props.data.id);
      }
  }
    return (
      <Accordion defaultActiveKey="0">
        <Accordion.Item>
          <Accordion.Header>댓글</Accordion.Header>
          <Accordion.Body>
            {
              data.map(data=>{
                return(
                  <div className='review-div'>
                    <div className='reviewComment'>{data.comment}</div>
                    <div className='reviewCreateUser'>작성자: {data.userinfoDTO.nickName}</div>
                  </div>
                )
              })
            }
            <input type="text" onChange={(e)=>{setReviewText(e.target.value)}} value = {reviewText} placeholder='댓글을 입력하세요.' onKeyUp={(e)=>{mouseUpHandler(e)}}/>
            <button onClick={()=>{addReview(props.data.id)}}>저장</button>
          </Accordion.Body>
        </Accordion.Item>
        </Accordion>
        )
}
export default Comment;