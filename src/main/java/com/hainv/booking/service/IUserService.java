package com.hainv.booking.service;

import com.hainv.booking.entity.dto.UserCustomerModal;
import com.hainv.booking.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User findUserByUserName(String username);

    List<UserCustomerModal> findAllUsers();
}
