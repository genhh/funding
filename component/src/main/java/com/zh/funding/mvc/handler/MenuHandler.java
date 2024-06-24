package com.zh.funding.mvc.handler;

import com.zh.funding.entity.Menu;
import com.zh.funding.service.api.MenuService;
import com.zh.funding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuHandler {
    @Autowired
    MenuService menuService;

    @ResponseBody
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> getMenuRoot() {
        List<Menu> menuList = menuService.getall();
        Map<Integer, Menu> menuMap = new HashMap<>();
        Menu root = null; // root
        for (Menu menu:menuList) {
            menuMap.put(menu.getId(), menu);
        }
        for (Menu menu:menuList) {
            if (menu.getPid() == null) {
                root = menu;
                continue;
            }
            Menu father = menuMap.get(menu.getPid());
            father.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }

    @ResponseBody
    @RequestMapping("")
    public ResultEntity<Menu> addMenu(Menu menu) {
        menuService.addMenu(menu);
        return ResultEntity.successWithoutData();
    }
}
