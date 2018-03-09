package com.lmy.service;

import com.lmy.dao.ProvinceRepository;
import com.lmy.model.Province;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2018/3/9.
 */
@Service
public class ProvinceService {

    @Resource
    private ProvinceRepository provinceRepository;

    public List<Province> getAllProvince() {
        return provinceRepository.findAll();
    }
}
