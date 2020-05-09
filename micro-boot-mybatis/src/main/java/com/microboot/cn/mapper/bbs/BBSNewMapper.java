package com.microboot.cn.mapper.bbs;

import com.microboot.cn.pojo.BBSNew;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BBSNewMapper {

    void save(BBSNew bbsNew);

    List<BBSNew> findAll();

    BBSNew findById(long id);

    void update(BBSNew bbsNew);

    void deleteById(long id);
}
