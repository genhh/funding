package com.zh.funding.service.api;

import com.zh.funding.entity.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getall();

    void addMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}
