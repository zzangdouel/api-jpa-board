package com.api.jpa.board.controller;

import com.api.jpa.board.repository.UserRepository;
import com.api.jpa.board.entity.BoardGood;
import com.api.jpa.board.entity.User;
import com.api.jpa.board.model.Response;
import com.api.jpa.board.repository.BoardGoodRepository;
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
@RequestMapping("/boardGood")
public class BoardGoodController {

    private Logger logger = LoggerFactory.getLogger(BoardGoodController.class);

    private final BoardGoodRepository boardGoodRepository;

    private final UserRepository userRepository;

    /**
     * 사용자가 좋아요한 글 히스토리 가져오기
     */
    @GetMapping("/getListByAccountId")
    public List<BoardGood> getListByAccountId(HttpSession httpSession) {
        String accountId = httpSession.getAttribute("accountId").toString();
        return  boardGoodRepository.findByAccountId(accountId);
    }

    /**
     * 사용자 글 좋아요 등록
     */
    @PostMapping("/postGoodPoint")
    public ResponseEntity<Response> postGoodPoint(HttpSession httpSession, @RequestBody BoardGood boardGood) {
        String accountId = httpSession.getAttribute("accountId").toString();
        User user = Optional.ofNullable(userRepository.findByAccountId(accountId)).orElseGet(() -> userRepository.findByAccountId(accountId));
        Response response = null;

        if(accountId.equals("") || user == null){
            response = Response.builder().code(1).status(false).accountId(accountId).message("권한 없음 실패").build();
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }

        List<BoardGood> findByAccountIdList = boardGoodRepository.findByAccountId(accountId);
        boolean bFlag = true;
        for(BoardGood boardGoodList : findByAccountIdList){
            if(boardGoodList.getBoardId() == boardGood.getBoardId()){
                bFlag = false;
                response = Response.builder().code(1).status(false).accountId(accountId).message("중복 추천 실패").build();
                break;
            }
        }

        if(bFlag){
            int boardId = boardGood.getBoardId();
            boardGood.setCreateDate(LocalDateTime.now());
            boardGood.setAccountId(accountId);
            boardGoodRepository.save(boardGood);

            int sumSize  = sumGoodPoint(boardId);
            boardGoodRepository.putBoardGoodPoint(sumSize, boardId);
            response = Response.builder().code(0).status(true).accountId(accountId).message("권한 있음 성공").build();
        }
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    /**
     * 글 좋아요 카운트
     */
    public int sumGoodPoint(int boardId){
        List<BoardGood> findByBoardIdList =  boardGoodRepository.findByBoardId(boardId);
        int sumSize = findByBoardIdList.size();
        return sumSize;
    }


}
