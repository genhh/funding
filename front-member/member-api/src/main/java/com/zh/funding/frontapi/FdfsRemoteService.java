package com.zh.funding.frontapi;

import com.zh.funding.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
@FeignClient("crowd-fdfs")
public interface FdfsRemoteService {
    @RequestMapping("/upload/files/fdfs")
    public ResultEntity<String> uploadFileToDFS(MultipartFile file);
}
