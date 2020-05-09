package com.microboot.cn.repository.bbs;

import com.microboot.cn.pojo.BBSNew;

import java.util.List;

public interface BBSNewRepository {

    int save(BBSNew bbsNew);

    List<BBSNew> findAll();

    BBSNew findById(Long id);

    int update(BBSNew bbsNew);

    int deleteById(Long id);
}
