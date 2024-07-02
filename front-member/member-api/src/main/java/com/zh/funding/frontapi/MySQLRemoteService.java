package com.zh.funding.frontapi;

import com.zh.funding.frontentity.po.MemberPO;
import com.zh.funding.frontentity.vo.ProjectVO;
import com.zh.funding.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("crowd-mysql")
public interface MySQLRemoteService {
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO);

    ResultEntity<String> saveProjectVORemote(ProjectVO projectVO, Integer memberId);
}
