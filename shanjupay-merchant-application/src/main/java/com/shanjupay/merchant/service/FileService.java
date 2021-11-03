package com.shanjupay.merchant.service;

import java.sql.BatchUpdateException;

/**
 * 文件服务
 *
 * @author guolonghang
 * @date 2021-11-03
 */
public interface FileService {

    /**
     * 上传
     *
     * @param bytes    字节
     * @param fileName 文件名称
     * @return {@code String}
     * @throws BatchUpdateException 批量更新异常
     */
    public String upload(byte[] bytes,String fileName) throws BatchUpdateException;
}
