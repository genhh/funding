package com.zh.funding.handler;

import java.util.List;

import com.zh.funding.frontentity.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zh.funding.frontentity.vo.AddressVO;
import com.zh.funding.frontentity.vo.OrderProjectVO;
import com.zh.funding.service.api.OrderService;
import com.zh.funding.util.ResultEntity;

@RestController
public class OrderProviderHandler {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/save/address/remote")
	public ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO) {
		
		try {
			orderService.saveAddress(addressVO);
			
			return ResultEntity.successWithoutData();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultEntity.failed(e.getMessage());
		}
		
	}
	
	@RequestMapping("/get/address/vo/remote")
	ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId) {
		
		try {
			List<AddressVO> addressVOList = orderService.getAddressVOList(memberId);
			
			return ResultEntity.successWithData(addressVOList);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultEntity.failed(e.getMessage());
		}
		
	}
	
	@RequestMapping("/get/order/project/vo/remote")
	ResultEntity<OrderProjectVO> getOrderProjectVORemote(
			@RequestParam("projectId") Integer projectId, 
			@RequestParam("returnId") Integer returnId) {
		
		try {
			OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(projectId, returnId);
			
			return ResultEntity.successWithData(orderProjectVO);
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultEntity.failed(e.getMessage());
		}
		
	}

	@RequestMapping("/save/order/remote")
	ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO) {

		try {
			orderService.saveOrder(orderVO);

			return ResultEntity.successWithoutData();

		} catch (Exception e) {
			e.printStackTrace();

			return ResultEntity.failed(e.getMessage());
		}

	}

}
