package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.model.UserModel;

@Repository
public interface UserDto extends JpaRepository<UserModel, Long> {

    UserModel findByEmail(String email);
}
