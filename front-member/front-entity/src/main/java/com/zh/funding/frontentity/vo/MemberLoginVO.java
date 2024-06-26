package com.zh.funding.frontentity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginVO {
	
	private Integer id;
	
    private String username;
	
	private String email;
	
}