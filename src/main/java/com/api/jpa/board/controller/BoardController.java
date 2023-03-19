package com.api.jpa.board.controller;

import com.api.jpa.board.entity.Board;
import com.api.jpa.board.entity.User;
import com.api.jpa.board.model.Response;
import com.api.jpa.board.repository.BoardRepository;
import com.api.jpa.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private Logger logger = LoggerFactory.getLogger(BoardController.class);

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    /**
     * 글 목록
     */
    @GetMapping("/getList")
    public List<Board> getList(HttpSession httpSession) {
        return  boardRepository.findAll();
    }

    /**
     * 사용자가 쓴 글 목록
     */
    @GetMapping("/getListByAccountId")
    public List<Board> getListByAccountId(HttpSession httpSession) {
        String accountId = httpSession.getAttribute("accountId").toString();
        return  boardRepository.findByAccountId(accountId);
    }

    /**
     * 글 작성
     */
    @PostMapping("/postBoardContent")
    public ResponseEntity<Response> postBoardContent(HttpSession httpSession, @RequestBody Board board) {
        Response response = accountIdValidate(httpSession);
        try {
            if(response.getStatus()){
                String accountId = httpSession.getAttribute("accountId").toString();
                board.setCreateDate(LocalDateTime.now());
                board.setAccountId(accountId);
                boardRepository.save(board);
            }
        }catch(Exception e){
            logger.error("exception preHandle"+e.getMessage(), e);
        }
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    /**
     * 글 수정
     */
    @PutMapping("/putBoardContent")
    public ResponseEntity<Response> putBoardContent(HttpSession httpSession, @RequestBody Board board) {
        int boardId = board.getBoardId();
        Response response = putValidate(httpSession, boardId);
        try{
            if(response.getStatus()){
                boardRepository.putBoardContent(board.getContent(), board.getTitle(),
                        LocalDateTime.now(),  board.getBoardId());
            }
        }catch(Exception e){
            logger.error("exception putBoardContent"+e.getMessage(), e);
        }
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    /**
     * 글 삭제
     */
    @PutMapping("/deleteBoardContent")
    public ResponseEntity<Response> deleteBoardContent(HttpSession httpSession, @RequestBody Board board) {
        int boardId = board.getBoardId();
        Response response = putValidate(httpSession, boardId);
        try{
            if(response.getStatus()){
                board.setDeleteDate(LocalDateTime.now());
                boardRepository.deleteBoardContent("Y", LocalDateTime.now(), boardId);
            }
        }catch(Exception e){
            logger.error("exception deleteBoardContent"+e.getMessage(), e);
        }

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    /**
     * 글 쓰기 권한 확인
     */
    public Response accountIdValidate(HttpSession httpSession){
        String accountId = httpSession.getAttribute("accountId").toString();
        User user = Optional.ofNullable(userRepository.findByAccountId(accountId)).orElseGet(() -> userRepository.findByAccountId(accountId));
        Response response = null;

        if(user != null){
            response = Response.builder().code(0).status(true).accountId(accountId).message("권한 있음 성공").build();
        }else{
            response = Response.builder().code(1).status(false).accountId(accountId).message("권한 없음 실패").build();
        }
        return response;
    }

    /**
     * 글 수정/삭제 권한 확인
     */
    public Response putValidate(HttpSession httpSession, int boardId){
        String accountId = httpSession.getAttribute("accountId").toString();
        List<Board> findByAccountId = boardRepository.findByAccountId(accountId);
        boolean bFlag = false;
        for(Board boardIdList : findByAccountId){
            if(boardIdList.getBoardId() == boardId){
                bFlag = true;
                break;
            }
        }
        Response response = null;
        if(bFlag){
            response = Response.builder().code(0).status(true).accountId(accountId).message("권한 있음 성공").build();
        }else{
            response = Response.builder().code(1).status(false).accountId(accountId).message("권한 없음 실패").build();
        }
        return response;
    }

}
