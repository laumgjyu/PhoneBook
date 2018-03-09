package com.lmy.dao;

import com.lmy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lmy on 2018/3/9.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
