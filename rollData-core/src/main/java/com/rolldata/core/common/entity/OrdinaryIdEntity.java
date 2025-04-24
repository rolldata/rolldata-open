package com.rolldata.core.common.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 普通ID基类,不自动创建ID,字段长度默认100
 * <p style='color: red;'>注意：@Column注解是在属性上的
 * <p >虽然@Column可以写在属性上，也可以写在get方法上，
 * <p >但是它必须和@Id在同样的位置才会起效。
 * 即@Id在字段属性上，@Column也必须写在字段属性上，写在get方法上是不生效的。
 *
 * @Title: OrdinaryIdEntity
 * @Description: id基础类
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2021-5-22
 * @version V1.0
 */
@MappedSuperclass
public abstract class OrdinaryIdEntity {

    @Id
    @Column(name = "ID", nullable = false, length = 100)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrdinaryIdEntity() {
        super();
    }

    public OrdinaryIdEntity(String id) {
        super();
        this.id = id;
    }

}
