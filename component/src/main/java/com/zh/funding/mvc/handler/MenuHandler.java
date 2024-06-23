package com.zh.funding.mvc.handler;

import com.zh.funding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MenuHandler {
    @Autowired
    MenuService menuService;
}
