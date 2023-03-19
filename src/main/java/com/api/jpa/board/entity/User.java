package com.api.jpa.board.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="T_USER")
public class User {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NICK_NAME")
    private String nickname;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "ACCOUNT_ID")
    private String accountId;

    @Column(name = "QUIT_YN")
    private String quitYn;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "QUIT_DATE")
    private LocalDateTime quitDate;

}
