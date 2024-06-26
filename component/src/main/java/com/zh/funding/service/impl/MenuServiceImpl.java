package com.zh.funding.service.impl;

import com.zh.funding.entity.Menu;
import com.zh.funding.entity.MenuExample;
import com.zh.funding.mapper.MenuMapper;
import com.zh.funding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> getall() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void addMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
