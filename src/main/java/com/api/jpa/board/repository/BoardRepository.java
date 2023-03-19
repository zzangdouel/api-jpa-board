package com.api.jpa.board.repository;

import com.api.jpa.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findByAccountId(String accountId);

    List<Board> findByBoardId(int boardId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE T_BOARD TB SET TB.content = :content, TB.title = :title, TB.updateDate = :updateDate where TB.boardId = :boardId")
    void putBoardContent(String content, String title, LocalDateTime updateDate, int boardId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE T_BOARD TB SET TB.deleteYn = :deleteYn, TB.deleteDate = :deleteDate where TB.boardId = :boardId")
    void deleteBoardContent(String deleteYn, LocalDateTime deleteDate, int boardId);


}
