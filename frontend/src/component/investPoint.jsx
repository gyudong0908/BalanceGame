import { useState } from "react"
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import axios from "axios";
import { useGameData, useUserInfo } from "../context";


function InvestPoint(props){
    const [gameData,setGameData] = useGameData();
    const [show, setShow] = useState(false);
    const [point, setPoint] = useState('');
    const [userInfo] = useUserInfo();
    const handleClose = () => {
        setPoint();
        setShow(false)
    };
    function addPoint(){    
        if(userInfo.point <point){
            alert("보유 포인트보다 많은 값을 배팅 할 수 없습니다.");
            return;
        }
        axios.get(`http://localhost:8080/invest?point=${point}&choiceId=${props.data.id}`,{ withCredentials: true }
        ).then(()=>{
            window.location.reload();
        })
    }
    const handleShow = () => {
        if(getCookie("token")){
            setShow(true);
        }else{
            alert(" 포인트를 배팅하려면 로그인을 해주세요");
        }
    }

    return(
            <div className="investPointModal">
            <Button variant="primary" onClick={handleShow} onHide={handleClose}>배팅 하기</Button>
            <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Point 배팅</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                <p>게임제목:{props.title}</p>
                <p>{props.data.content}</p>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput2">
                    <Form.Label>배팅할 point를 입력해주세요</Form.Label>
                    <Form.Control type="text" onChange={(e)=>{
                        if(!isNaN(e.target.value)){
                            setPoint(e.target.value);
                        }else{
                            alert("숫자만 입력해 주세요");
                            e.target.value = e.target.value.substring(0,e.target.value.length -1);
                        }
                        }} value={point}/>
                </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary" onClick={()=>{handleClose(); addPoint();}}>
                배팅 하기
                </Button>
            </Modal.Footer>
            </Modal>
        </div>
    )
}
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
  }
export default InvestPoint;