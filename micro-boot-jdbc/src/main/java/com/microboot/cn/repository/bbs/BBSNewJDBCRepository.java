package com.microboot.cn.repository.bbs;

import com.microboot.cn.pojo.BBSNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BBSNewJDBCRepository implements BBSNewRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int save(BBSNew bbsNew) {
        return jdbcTemplate.update(
                "INSERT INTO `bbsnew`(`id`, `contact`, `name`, `create_time`) VALUES (?, ?, ?, ?);",
                bbsNew.getId(), bbsNew.getContact(), bbsNew.getName(), bbsNew.getCreateTime());
    }

    @Override
    public List<BBSNew> findAll() {
        return jdbcTemplate.query(
                "SELECT `id`, `contact`, `name`, `create_time` FROM bbsnew ",
                (rs, rowNum) ->
                        new BBSNew(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("contact"),
                                rs.getDate("create_time")
                        )
        );
    }

    @Override
    public BBSNew findById(Long id) {
        Optional<BBSNew> news = jdbcTemplate.queryForObject(
                "SELECT `id`, `contact`, `name`, `create_time` FROM bbsnew  WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new BBSNew(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("contact"),
                                rs.getDate("create_time")
                        ))
        );
        return news.get();
    }

    @Override
    public int update(BBSNew bbsNew) {
        return jdbcTemplate.update(
                "UPDATE `bbsnew` SET `name` = ?, `contact` = ?, `create_time` = ? WHERE `id` = ?",
                bbsNew.getName(), bbsNew.getContact(), bbsNew.getCreateTime(), bbsNew.getId());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "DELETE `bbsnew` WHERE id = ?",
                id);
    }
}
