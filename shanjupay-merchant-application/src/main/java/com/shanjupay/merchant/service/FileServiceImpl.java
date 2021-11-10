package com.shanjupay.merchant.service;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.BatchUpdateException;


/**
 * 文件服务impl
 *
 * @author guolonghang
 * @date 2021-11-09
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    /**
     * url
     */
    @Value("${oss.qiniu.url}")
    private  String url;

    /**
     * 访问密钥
     */
    @Value("${oss.qiniu.accessKey}")
    private  String accessKey;
    /**
     * 秘密密钥
     */
    @Value("${oss.qiniu.secretKey}")
    private  String secretKey;
    /**
     * 桶
     */
    @Value("${oss.qiniu.bucket}")
    private  String bucket;

    @Override
    public String upload(byte[] bytes, String fileName) throws BusinessException{
        try {
            QiniuUtils.upload2Qiniu(accessKey,secretKey,bucket,bytes,fileName);
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        return url+fileName;
    }
}
