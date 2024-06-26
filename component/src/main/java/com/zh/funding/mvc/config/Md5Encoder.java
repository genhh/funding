package com.zh.funding.mvc.config;

import com.zh.funding.util.CrowdUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Md5Encoder implements PasswordEncoder {
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(CrowdUtil.md5((String)rawPassword));
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return CrowdUtil.md5((String)rawPassword);
    }
}
