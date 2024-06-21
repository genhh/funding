package com.zh.funding.mvc.handler;

import com.zh.funding.constant.CrowdConstant;
import com.zh.funding.entity.Admin;
import com.zh.funding.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {
    @Autowired
    AdminService adminService;

    @RequestMapping("/admin/do/login.html")
    public String doLogin(String loginAcct, String userPswd, HttpSession session) {
        //login check
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
        //save admin in session
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }
}
