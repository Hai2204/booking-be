package com.hainv.booking.service;

import com.hainv.booking.entity.user.User;

import java.util.Optional;

public interface IUserService {

    User findUserByUserName(String username);
}
