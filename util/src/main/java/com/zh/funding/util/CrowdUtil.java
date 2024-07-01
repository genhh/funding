package com.zh.funding.util;

import cn.hutool.core.util.RandomUtil;
import com.zh.funding.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import cn.hutool.core.util.StrUtil;

public class CrowdUtil {
    private static boolean mismatch(String str, String regex){
        if (StrUtil.isBlank(str)) {
            return true;
        }
        return !str.matches(regex);
    }

    public static boolean judgeRequestType(HttpServletRequest req){
        String reqHeader = req.getHeader("Accept");
        String xReqHeader = req.getHeader("X-Requested-with");

        //if type == ajax return true
        return (reqHeader!=null && reqHeader.contains("application/json")) ||
                (xReqHeader!=null && xReqHeader.equals("XMLHttpRequest"));
    }

    public static String md5(String source) {
        if(source == null || source.isEmpty()) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] input = source.getBytes();
            byte[] output = messageDigest.digest(input);

            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            //convert to 16 Hex
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();

            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultEntity<String> sendCodeByShortMessage(String phoneNum ) {
        // 1.校验手机号
        if (mismatch(phoneNum, CrowdConstant.PHONE_REGEX)) {
            // 2.如果不符合，返回错误信息
            return ResultEntity.failed("手机号格式错误！");
        }
        // 3.符合，生成验证码
        String code = RandomUtil.randomNumbers(6);

        System.out.println("*******发送短信验证码成功，验证码:" + code);
        // 返回ok
        return ResultEntity.successWithData(code);
    }
}
