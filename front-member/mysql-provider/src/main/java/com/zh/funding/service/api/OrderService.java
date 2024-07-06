package com.zh.funding.service.api;

import java.util.List;

import com.zh.funding.frontentity.vo.AddressVO;
import com.zh.funding.frontentity.vo.OrderProjectVO;

public interface OrderService {

	OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

	List<AddressVO> getAddressVOList(Integer memberId);

	void saveAddress(AddressVO addressVO);

}
