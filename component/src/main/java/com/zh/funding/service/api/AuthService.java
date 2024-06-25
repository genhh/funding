package com.zh.funding.service.api;

import com.zh.funding.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelathinship(Map<String, List<Integer>> map);

    List<Auth> getAllAuth();

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
