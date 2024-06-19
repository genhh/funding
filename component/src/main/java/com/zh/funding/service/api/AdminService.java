package com.zh.funding.service.api;

import com.zh.funding.entity.Admin;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();
}
