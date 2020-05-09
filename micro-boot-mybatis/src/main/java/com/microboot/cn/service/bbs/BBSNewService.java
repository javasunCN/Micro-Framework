package com.microboot.cn.service.bbs;

import com.microboot.cn.pojo.BBSNew;

import java.util.List;

public interface BBSNewService {

    void createBBSNew(BBSNew bbsNew);
    List<BBSNew> getBBSNew();
    BBSNew findById(long id);
    void update(BBSNew bbsNew, long l);
    void deleteBBSNewById(long id);
    BBSNew updatePartially(BBSNew bbsNew, long id);
}
