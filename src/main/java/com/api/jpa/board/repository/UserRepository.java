package com.api.jpa.board.repository;

import com.api.jpa.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAccountId(String accountId);

}
