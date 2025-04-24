package com.rolldata.core.common.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @Title: IdEntity
 * @Description: id基础类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
@MappedSuperclass
public abstract class IdEntity {
	private String id;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IdEntity() {
		super();
	}

	public IdEntity(String id) {
		super();
		this.id = id;
	}

}
