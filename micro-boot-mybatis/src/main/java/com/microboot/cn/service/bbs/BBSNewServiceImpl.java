package com.microboot.cn.service.bbs;


import com.microboot.cn.mapper.bbs.BBSNewMapper;
import com.microboot.cn.pojo.BBSNew;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BBSNewServiceImpl implements BBSNewService {

    @Resource
    private BBSNewMapper bbsNewMapper;

    @Override
    public void createBBSNew(BBSNew bbsNew) {
        bbsNewMapper.save(bbsNew);
    }

    @Override
    public List<BBSNew> getBBSNew() {
        return bbsNewMapper.findAll();
    }

    @Override
    public BBSNew findById(long id) {
        return bbsNewMapper.findById(id);
    }

    @Override
    public void update(BBSNew bbsNew, long l) {
         bbsNewMapper.update(bbsNew);
    }

    @Override
    public void deleteBBSNewById(long id) {
        bbsNewMapper.deleteById(id);
    }

    @Override
    public BBSNew updatePartially(BBSNew bbsNew, long id) {
        BBSNew news = findById(id);
        news.setContact(bbsNew.getContact());
        bbsNewMapper.save(news);
        return news;
    }
}
