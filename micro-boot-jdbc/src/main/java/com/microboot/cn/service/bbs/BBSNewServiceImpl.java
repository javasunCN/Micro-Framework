package com.microboot.cn.service.bbs;


import com.microboot.cn.repository.bbs.BBSNewRepository;
import com.microboot.cn.pojo.BBSNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BBSNewServiceImpl implements BBSNewService {

    @Autowired
    private BBSNewRepository bbsNewRepository;

    @Override
    public void createBBSNew(BBSNew bbsNew) {
        bbsNewRepository.save(bbsNew);
    }

    @Override
    public List<BBSNew> getBBSNew() {
        return bbsNewRepository.findAll();
    }

    @Override
    public BBSNew findById(Long id) {
        return bbsNewRepository.findById(id);
    }

    @Override
    public void update(BBSNew bbsNew, Long l) {
        bbsNewRepository.update(bbsNew);
    }

    @Override
    public void deleteBBSNewById(Long id) {
        bbsNewRepository.deleteById(id);
    }

    @Override
    public BBSNew updatePartially(BBSNew bbsNew, Long id) {
        BBSNew news = findById(id);
        news.setContact(bbsNew.getContact());
        bbsNewRepository.update(news);
        return news;
    }
}
