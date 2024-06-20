package com.zh.funding.util;

import javax.servlet.http.HttpServletRequest;


public class CrowdUtil {
    public boolean judgeRequestType(HttpServletRequest req){
        String reqHeader = req.getHeader("Accept");
        String xReqHeader = req.getHeader("X-Requested-with");

        return (reqHeader!=null && reqHeader.contains("application/json")) ||
                (xReqHeader!=null && xReqHeader.equals("XMLHttpRequest"));
    }
}
