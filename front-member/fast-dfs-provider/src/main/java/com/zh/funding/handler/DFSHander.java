package com.zh.funding.handler;

import com.github.tobato.fastdfs.domain.fdfs.*;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zh.funding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@RestController
public class DFSHander {

    @Autowired
    protected FastFileStorageClient storageClient;
    /**
     * 专门负责上传文件到FastDFS服务器的客户端类(待测试)
     * @param inputStream		要上传的文件的输入流
     * @param originalName		要上传的文件的原始文件名
     * @return	包含上传结果以及上传的文件在OSS上的访问路径
     */
    @RequestMapping("/xxxxx")
    public ResultEntity<String> uploadFileToDFS(
            InputStream inputStream,
            String originalName) {


        // 生成上传文件的目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // 生成上传文件在OSS服务器上保存时的文件名
        // 原始文件名：beautfulgirl.jpg
        // 生成文件名：wer234234efwer235346457dfswet346235.jpg
        // 使用UUID生成文件主体名称
        String fileMainName = UUID.randomUUID().toString().replace("-", "");

        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));

        // 使用目录、文件主体名称、文件扩展名称拼接得到对象名称
        String objectName = folderName + "/" + fileMainName + extensionName;

        // Metadata



        try {
            // 上传文件和Metadata
            StorePath path = storageClient.uploadFile(inputStream, 10, objectName, null);
            assertNotNull(path);


            // 验证获取MetaData

            Set<MetaData> fetchMetaData = storageClient.getMetadata(path.getGroup(), path.getPath());

            // 根据响应状态码判断请求是否成功
            if(path.getPath() != null) {
                // 当前方法返回成功
                return ResultEntity.successWithData(path.getPath());
            } else {
                // 当前方法返回失败
                return ResultEntity.failed("失败");
            }
        } catch (Exception e) {
            e.printStackTrace();

            // 当前方法返回失败
            return ResultEntity.failed(e.getMessage());
        }
    }
}
