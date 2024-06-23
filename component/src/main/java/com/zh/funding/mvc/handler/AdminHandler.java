package com.zh.funding.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.zh.funding.constant.CrowdConstant;
import com.zh.funding.entity.Admin;
import com.zh.funding.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

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

    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
                              // keyword默认值使用空字符串，和SQL语句配合实现两种情况适配
                              @RequestParam(value="keyword", defaultValue="") String keyword,

                              // pageNum默认值使用1
                              @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,

                              // pageSize默认值使用5
                              @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,

                              ModelMap modelMap) {

        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);

        return "admin-page";
    }

    @RequestMapping("/admin/save.html")
    public String save(Admin admin){

        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String getEditPage(@RequestParam("adminId") Integer adminId, ModelMap modelMap){
        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute("admin",admin);

        return "admin-edit";
    }

    @RequestMapping("/admin/update.html")
    public String updatePage(Admin admin, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword){
        adminService.updateAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword) {
        adminService.remove(adminId);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }
}
