package com.zh.funding.mvc.handler;

import com.zh.funding.entity.Auth;
import com.zh.funding.entity.Role;
import com.zh.funding.service.api.AdminService;
import com.zh.funding.service.api.AuthService;
import com.zh.funding.service.api.RoleService;
import com.zh.funding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {

    @Autowired
    RoleService roleService;
    @Autowired
    AdminService adminService;
    @Autowired
    AuthService authService;
    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ) {
        // 1.查询已分配角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 2.查询未分配角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);
        // 3.存入模型（本质上其实是：request.setAttribute("attrName",attrValue);
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
        return "assign-role";
    }

    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            // 我们允许用户在页面上取消所有已分配角色再提交表单，所以可以不提供roleIdList 请求参数
            // 设置 required=false 表示这个请求参数不是必须的
            @RequestParam(value="roleIdList", required=false) List<Integer> roleIdList
    ) {

        adminService.saveAdminRoleRelationship(adminId, roleIdList);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam(value = "roleId") Integer roleId) {
        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelathinship(
            @RequestBody Map<String, List<Integer>> map) {
        authService.saveRoleAuthRelathinship(map);
        return ResultEntity.successWithoutData();
    }
    @ResponseBody
    @RequestMapping("assgin/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> auth = authService.getAllAuth();
        return ResultEntity.successWithData(auth);
    }
}
