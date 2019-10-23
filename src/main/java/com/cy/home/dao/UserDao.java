package com.cy.home.dao;

import com.cy.home.domain.VO.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDao {

    public User findUserByUsername(String s);

}
