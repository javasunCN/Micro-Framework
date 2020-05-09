package com.microboot.cn.service.bbs;


import com.microboot.cn.pojo.BBSNew;
import com.microboot.cn.repository.bbs.BBSNewRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class BBSNewServiceImpl implements BBSNewService {

    @Resource
    private BBSNewRepository bbsNewRepository;

    @Override
    public void createBBSNew(BBSNew bbsNew) {
        bbsNewRepository.save(bbsNew);
    }

    @Override
    public List<BBSNew> getBBSNew() {
        return (List<BBSNew>)bbsNewRepository.findAll();
    }

    @Override
    public BBSNew findById(long id) {
        Optional<BBSNew> bbsNew = bbsNewRepository.findById(id);
        return bbsNew.get();
    }

    @Override
    public BBSNew update(BBSNew bbsNew, long l) {
        return bbsNewRepository.save(bbsNew);
    }

    @Override
    public void deleteBBSNewById(long id) {
        bbsNewRepository.deleteById(id);
    }

    @Override
    public BBSNew updatePartially(BBSNew bbsNew, long id) {
        BBSNew news = findById(id);
        news.setContact(bbsNew.getContact());
        return bbsNewRepository.save(news);
    }
}
