package com.zh.funding.service.api;

import com.zh.funding.frontentity.po.MemberPO;

public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginacct);
}
