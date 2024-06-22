package com.zh.funding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.funding.constant.CrowdConstant;
import com.zh.funding.entity.Admin;
import com.zh.funding.entity.AdminExample;
import com.zh.funding.entity.AdminExample.Criteria;
import com.zh.funding.exception.LoginAcctAlreadyInUseException;
import com.zh.funding.exception.LoginFailedException;
import com.zh.funding.mapper.AdminMapper;
import com.zh.funding.service.api.AdminService;
import com.zh.funding.util.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Override
    public void saveAdmin(Admin admin) {
        //adminMapper.insert(admin);
        //md5 加密
        String userPswd = admin.getUserPswd();
        userPswd = CrowdUtil.md5(userPswd);
        admin.setUserPswd(userPswd);

        //create generate time
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        admin.setCreateTime(createTime);

        //save
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            logger.info("异常全类名="+e.getClass().getName());
            //重名报错
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        AdminExample adminExample = new AdminExample();
        Criteria criteria = adminExample.createCriteria();
        //add select condition
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> list = adminMapper.selectByExample(adminExample);

        if (list == null || list.isEmpty()) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (list.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = list.get(0);

        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        String userPswdDB = admin.getUserPswd();
        String userPswdForm = CrowdUtil.md5(userPswd);

        if(!Objects.equals(userPswdDB,userPswdForm)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Admin> list = adminMapper.selectAdminByKeyword(keyword);

        Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
        logger.debug("adminList 全类名:"+list.getClass().getName());

        return new PageInfo<>(list);
    }

}
