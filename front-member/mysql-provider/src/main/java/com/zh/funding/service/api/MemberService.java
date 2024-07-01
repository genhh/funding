package com.zh.funding.service.api;

import com.zh.funding.frontentity.po.MemberPO;
import com.zh.funding.util.ResultEntity;

public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginacct);
    ResultEntity<String> saveMember(MemberPO memberPO);
}
