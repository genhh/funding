package com.zh.funding.frontapi;

import com.zh.funding.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
@FeignClient("crowd-fdfs")
public interface FdfsRemoteService {
    @RequestMapping("/xxx")
    public ResultEntity<String> uploadFileToDFS(InputStream inputStream, String originalName);
}
