package com.api.jpa.board.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "T_BOARD_GOOD")
public class BoardGood{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private int seq;

    @Column(name = "BOARD_ID")
    private int boardId;

    @Column(name = "ACCOUNT_ID")
    private String accountId;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

}
