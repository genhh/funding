package com.zh.funding.handler;

import com.zh.funding.frontentity.po.MemberPO;
import com.zh.funding.service.api.MemberService;
import com.zh.funding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class MemberProviderHandler {
    @Autowired
    MemberService memberService;

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct) {

        try {

            // 1.调用本地Service完成查询
            MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);

            // 2.如果没有抛异常，那么就返回成功的结果
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            e.printStackTrace();

            // 3.如果捕获到异常则返回失败的结果
            return ResultEntity.failed(e.getMessage());
        }

    }
}
