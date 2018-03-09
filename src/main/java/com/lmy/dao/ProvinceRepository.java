package com.lmy.dao;

import com.lmy.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lmy on 2018/3/9.
 */
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    Province findByName(String name);
}
