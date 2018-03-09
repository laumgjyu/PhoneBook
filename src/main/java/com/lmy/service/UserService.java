package com.lmy.service;

import com.lmy.dao.UserRepository;
import com.lmy.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2018/3/9.
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
