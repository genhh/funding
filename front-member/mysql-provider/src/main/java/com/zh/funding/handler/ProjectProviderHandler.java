package com.zh.funding.handler;

import com.zh.funding.frontentity.vo.DetailProjectVO;
import com.zh.funding.frontentity.vo.PortalTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zh.funding.frontentity.vo.ProjectVO;
import com.zh.funding.service.api.ProjectService;
import com.zh.funding.util.ResultEntity;

import java.util.List;

@RestController
public class ProjectProviderHandler {
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("/save/project/vo/remote")
	public ResultEntity<String> saveProjectVORemote(
			@RequestBody ProjectVO projectVO, 
			@RequestParam("memberId") Integer memberId) {
		
		try {
			// 调用“本地”Service执行保存
			projectService.saveProject(projectVO, memberId);
			
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultEntity.failed(e.getMessage());
		}
		
	}

	@RequestMapping("/get/project/detail/remote/{projectId}")
	public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId) {

		try {
			DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(projectId);

			return ResultEntity.successWithData(detailProjectVO);

		} catch (Exception e) {
			e.printStackTrace();

			return ResultEntity.failed(e.getMessage());
		}

	}

	@RequestMapping("/get/portal/type/project/data/remote")
	public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote() {

		try {
			List<PortalTypeVO> portalTypeVOList = projectService.getPortalTypeVO();

			return ResultEntity.successWithData(portalTypeVOList);
		} catch (Exception e) {
			e.printStackTrace();

			return ResultEntity.failed(e.getMessage());
		}

	}

}
