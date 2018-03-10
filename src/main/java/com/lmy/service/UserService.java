package com.lmy.service;

import com.lmy.core.cache.MemoryCache;
import com.lmy.dao.ProvinceRepository;
import com.lmy.dao.UserRepository;
import com.lmy.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by lmy on 2018/3/9.
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private ProvinceRepository provinceRepository;

    public List<User> getAllUsers() {
        List<User> users;
        if (MemoryCache.phoneNumberCacheMap.isEmpty() && MemoryCache.nameCacheMap.isEmpty()) {
            users = userRepository.findAll();
            for (User user : users) {
                MemoryCache.putByName(user);
                MemoryCache.putByPhoneNumber(user);
            }
        } else {
            users = MemoryCache.getCachedEntities();
        }
        return users;
    }

    public void deleteUser(User user) {
        MemoryCache.removeByNameInMap(user.getName());

        MemoryCache.removeByPhoneNumberInMap(user.getPhoneNumber());

        MemoryCache.removeByNameInTable(user.getName());

        MemoryCache.removeByPhoneNumberInTable(user.getPhoneNumber());

        userRepository.delete(user.getId());
    }

    public void addUser(User user) {
        MemoryCache.putByPhoneNumber(user);
        MemoryCache.putByName(user);

        user.setProvince(provinceRepository.findByName(user.getProvince().getName()));
        user.setLocalDate(LocalDate.now());
        userRepository.save(user);
    }
}
