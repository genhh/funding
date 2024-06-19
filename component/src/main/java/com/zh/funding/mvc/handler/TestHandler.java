package com.zh.funding.mvc.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestHandler {
    @RequestMapping("/test/aaa/bbb/ccc.html")
    public String doTest() {
        return "target";
    }
}
