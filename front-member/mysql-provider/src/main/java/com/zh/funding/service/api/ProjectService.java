package com.zh.funding.service.api;

import com.zh.funding.frontentity.vo.DetailProjectVO;
import com.zh.funding.frontentity.vo.PortalTypeVO;
import com.zh.funding.frontentity.vo.ProjectVO;

import java.util.List;

public interface ProjectService {

	void saveProject(ProjectVO projectVO, Integer memberId);
	List<PortalTypeVO> getPortalTypeVO();

	DetailProjectVO getDetailProjectVO(Integer projectId);
}
