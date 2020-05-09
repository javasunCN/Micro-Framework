package com.microboot.cn.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class BBSNew {

    private Long id;
    private String name;
    private String contact;
    private Date createTime;

    public BBSNew(Long id, String name, String contact, Date createTime) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.createTime = createTime;
    }
}
