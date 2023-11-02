import { useState } from 'react';
import '../css/addModal.css'
import axios from 'axios';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
function AddModal(){
    const [show, setShow] = useState(false);
    const [gameTitle, setGameTitle] = useState('');
    const [choice1, setChoice1] = useState('');
    const [choice2, setChoice2] = useState('');
    const handleClose = () => {
        setShow(false);
        setGameTitle('');
        setChoice1('');
        setChoice2('');
    };
    const handleShow = () => setShow(true);

    function handleCreateGame() {
        const data = {
            title: gameTitle,
            choices: [
            { content: choice1 },
            { content: choice2 }
            ]
        };
        axios.post("http://localhost:8080/game",data).then(()=>{
            window.location.reload();
        }).catch(error=>{
            console.log(error);
            alert("에러 발생:" + error.message);
        })
    }
    return (
      <>
        <Button variant="primary" onClick={handleShow}>
          게임 생성
        </Button>
  
        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>게임 생성</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form>
              <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                <Form.Label>게임 제목</Form.Label>
                <Form.Control type="text" autoFocus onChange={(e)=>{setGameTitle(e.target.value)}} value={gameTitle}/>
              </Form.Group>
              <Form.Group className="mb-3" controlId="exampleForm.ControlInput2">
                <Form.Label>선택지 1</Form.Label>
                <Form.Control type="text" onChange={(e)=>{setChoice1(e.target.value)}} value={choice1}/>
              </Form.Group>
              <Form.Group className="mb-3" controlId="exampleForm.ControlInput3">
                <Form.Label>선택지 2</Form.Label>
                <Form.Control type="text" onChange={(e)=>{setChoice2(e.target.value)}} value={choice2}/>
              </Form.Group>
            </Form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="primary" onClick={()=>{handleClose(); handleCreateGame();}}>
              게임 생성하기
            </Button>
          </Modal.Footer>
        </Modal>
      </>
    );
  }
export default AddModal;