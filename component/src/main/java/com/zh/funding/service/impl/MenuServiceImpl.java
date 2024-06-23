package com.zh.funding.service.impl;

import com.zh.funding.mapper.MenuMapper;
import com.zh.funding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;

}
