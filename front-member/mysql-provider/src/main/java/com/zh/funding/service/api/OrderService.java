package com.zh.funding.service.api;

import java.util.List;

import com.zh.funding.frontentity.vo.AddressVO;
import com.zh.funding.frontentity.vo.OrderProjectVO;
import com.zh.funding.frontentity.vo.OrderVO;

public interface OrderService {

	OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

	List<AddressVO> getAddressVOList(Integer memberId);

	void saveAddress(AddressVO addressVO);

    void saveOrder(OrderVO orderVO);
}
