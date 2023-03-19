package com.api.jpa.board.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="T_BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private int boardId;

    @Column(name = "ACCOUNT_ID")
    private String accountId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "GOOD_POINT")
    private int goodPoint;

    @Column(name = "DELETE_YN")
    private String deleteYn = "N";

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "DELETE_DATE")
    private LocalDateTime deleteDate;


}
