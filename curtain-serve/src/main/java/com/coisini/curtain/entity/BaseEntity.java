package com.coisini.curtain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @Description Entity基类
 *      @MappedSuperclass 表眀该类是映射的超类
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * @JsonIgnore 忽略序列化
     */
    @JsonIgnore
    @Column(insertable=false, updatable=false)
    private Date createTime;
    @JsonIgnore
    @Column(insertable=false, updatable=false)
    private Date updateTime;
    @JsonIgnore
    private Date deleteTime;

}
