package com.zh.funding.frontentity.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLauchInfoVO  implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	// 简单介绍
	private String descriptionSimple;
	
	// 详细介绍
	private String descriptionDetail;
	
	// 联系电话
	private String phoneNum;
	
	// 客服电话
	private String serviceNum;

}