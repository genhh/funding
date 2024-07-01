package com.zh.funding.service.impl;

import com.zh.funding.frontentity.po.MemberPO;
import com.zh.funding.frontentity.po.MemberPOExample;
import com.zh.funding.frontentity.po.MemberPOExample.Criteria;
import com.zh.funding.mapper.MemberPOMapper;
import com.zh.funding.service.api.MemberService;
import com.zh.funding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberPOMapper memberPOMapper;
    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        // 1.创建Example对象
        MemberPOExample example = new MemberPOExample();

        // 2.创建Criteria对象
        Criteria criteria = example.createCriteria();

        // 3.封装查询条件
        criteria.andLoginacctEqualTo(loginacct);

        // 4.执行查询
        List<MemberPO> list = memberPOMapper.selectByExample(example);

        // 5.获取结果
        return list.get(0);
    }

    @Override
    public ResultEntity<String> saveMember(MemberPO memberPO) {
        return null;
    }
}
