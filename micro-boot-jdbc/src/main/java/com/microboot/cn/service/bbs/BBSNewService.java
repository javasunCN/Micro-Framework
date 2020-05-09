package com.microboot.cn.service.bbs;

import com.microboot.cn.pojo.BBSNew;

import java.util.List;

public interface BBSNewService {

    void createBBSNew(BBSNew bbsNew);
    List<BBSNew> getBBSNew();
    BBSNew findById(Long id);
    void update(BBSNew bbsNew, Long l);
    void deleteBBSNewById(Long id);
    BBSNew updatePartially(BBSNew bbsNew, Long id);
}
