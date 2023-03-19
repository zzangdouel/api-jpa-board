package com.api.jpa.board.repository;

import com.api.jpa.board.entity.BoardGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface BoardGoodRepository extends JpaRepository<BoardGood, Integer> {
    List<BoardGood> findByAccountId(String accountId);
    List<BoardGood> findByBoardId(int boardId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE T_BOARD TB SET TB.goodPoint = :goodPoint where TB.boardId = :boardId")
    void putBoardGoodPoint(int goodPoint, int boardId);
}
