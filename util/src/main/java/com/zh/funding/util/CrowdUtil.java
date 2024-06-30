package com.zh.funding.util;

import com.zh.funding.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class CrowdUtil {
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

}
