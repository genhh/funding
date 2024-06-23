package com.zh.funding.service.api;

import com.github.pagehelper.PageInfo;
import com.zh.funding.entity.Role;

public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void saveRole(Role role);
}
