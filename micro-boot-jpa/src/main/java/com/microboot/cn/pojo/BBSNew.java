package com.microboot.cn.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="BBSNew")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BBSNew {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="contact")
    private String contact;
    @Column(name="create_time")
    private Date createTime;

}
