package com.zh.funding.service.api;

import com.github.pagehelper.PageInfo;
import com.zh.funding.entity.Admin;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);
}
