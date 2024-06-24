package com.zh.funding.service.api;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelathinship(Map<String, List<Integer>> map);
}
