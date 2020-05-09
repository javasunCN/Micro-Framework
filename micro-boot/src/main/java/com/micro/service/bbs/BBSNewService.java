package com.micro.service.bbs;

import com.micro.pojo.BBSNew;

import java.util.List;

public interface BBSNewService {

    void createBBSNew(BBSNew bbsNew);
    List<BBSNew> getBBSNew();
    BBSNew findById(long id);
    BBSNew update(BBSNew bbsNew, long l);
    void deleteBBSNewById(long id);
    BBSNew updatePartially(BBSNew bbsNew, long id);
}
