package com.zh.funding.service.api;

import com.zh.funding.frontentity.vo.ProjectVO;

public interface ProjectService {

	void saveProject(ProjectVO projectVO, Integer memberId);

}
