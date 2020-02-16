package net.honux.qna2020.web;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long>{
    public User findByEmail(String email);
}
