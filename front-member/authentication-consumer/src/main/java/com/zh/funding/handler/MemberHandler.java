package com.zh.funding.handler;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zh.funding.frontapi.MySQLRemoteService;
import com.zh.funding.frontapi.RedisRemoteService;
import com.zh.funding.constant.CrowdConstant;
import com.zh.funding.frontentity.po.MemberPO;
import com.zh.funding.frontentity.vo.MemberLoginVO;
import com.zh.funding.frontentity.vo.MemberVO;
import com.zh.funding.util.CrowdUtil;
import com.zh.funding.util.ResultEntity;

@Controller
public class MemberHandler {
	

	
	@Autowired
	private RedisRemoteService redisRemoteService;
	
	@Autowired
	private MySQLRemoteService mySQLRemoteService;
	
	@RequestMapping("/auth/member/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping("/auth/member/do/login")
	public String login(
			@RequestParam("loginacct") String loginacct, 
			@RequestParam("userpswd") String userpswd,
			ModelMap modelMap,
			HttpSession session) {
		
		// 1.调用远程接口根据登录账号查询MemberPO对象
		ResultEntity<MemberPO> resultEntity = 
				mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
		
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
			
			return "member-login";
			
		}
		
		MemberPO memberPO = resultEntity.getData();
		
		if(memberPO == null) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
			
			return "member-login";
		}
		
		// 2.比较密码
		String userpswdDataBase = memberPO.getUserpswd();

		userpswd = CrowdUtil.md5(userpswd);
		
		if(!Objects.equals(userpswd, userpswdDataBase)) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
			
			return "member-login";
		}
		
		// 3.创建MemberLoginVO对象存入Session域
		MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
		session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);
		
		return "redirect:/auth/member/to/center/page";
	}
	
	@RequestMapping("/auth/do/member/register")
	public String register(MemberVO memberVO, ModelMap modelMap) {
		
		// 1.获取用户输入的手机号
		String phoneNum = memberVO.getPhoneNum();
		
		// 2.拼Redis中存储验证码的Key
		String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
		
		// 3.从Redis读取Key对应的value
		ResultEntity<String> resultEntity = redisRemoteService.getRedisStringValueByKeyRemote(key);
		
		// 4.检查查询操作是否有效
		String result = resultEntity.getResult();
		if(ResultEntity.FAILED.equals(result)) {
			
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
			
			return "member-reg";
		}
		
		String redisCode = resultEntity.getData();
		
		if(redisCode == null) {
			
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
			
			return "member-reg";
		}
		
		// 5.如果从Redis能够查询到value则比较表单验证码和Redis验证码
		String formCode = memberVO.getCode();
		
		if(!Objects.equals(formCode, redisCode)) {
			
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
			
			return "member-reg";
		}
		
		// 6.如果验证码一致，则从Redis删除
		redisRemoteService.removeRedisKeyRemote(key);
		
		// 7.执行密码加密
		String userpswdBeforeEncode = memberVO.getUserpswd();
		
		String userpswdAfterEncode = CrowdUtil.md5(userpswdBeforeEncode);
		
		memberVO.setUserpswd(userpswdAfterEncode);
		
		// 8.执行保存
		// ①创建空的MemberPO对象
		MemberPO memberPO = new MemberPO();
		
		// ②复制属性
		BeanUtils.copyProperties(memberVO, memberPO);
		
		// ③调用远程方法
		ResultEntity<String> message = mySQLRemoteService.saveMember(memberPO);

		if (message.getResult().equals(ResultEntity.FAILED)){
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, message.getMessage());

			return "member-reg";
		}
		
		// 使用重定向避免刷新浏览器导致重新执行注册流程
		return "redirect:/auth/member/to/login/page";
	}
	
	@ResponseBody
	@RequestMapping("/auth/member/send/short/message.json")
	public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {
		
		// 1.发送验证码到phoneNum手机
		ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendCodeByShortMessage(phoneNum);
		
		// 2.判断短信发送结果
		if(ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())) {
			// 3.如果发送成功，则将验证码存入Redis
			// ①从上一步操作的结果中获取随机生成的验证码
			String code = sendMessageResultEntity.getData();
			
			// ②拼接一个用于在Redis中存储数据的key
			String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
			
			// ③调用远程接口存入Redis
			ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 15, TimeUnit.MINUTES);
			
			// ④判断结果
			if(ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult())) {
				
				return ResultEntity.successWithData(code);//把验证码写到请求中，方便调试
			}else {
				return saveCodeResultEntity;
			}
		} else {
			return sendMessageResultEntity;
		}
		
	}

}
