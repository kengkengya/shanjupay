package com.shanjupay.common.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * qiniu跑龙套
 *
 * @author guolonghang
 * @date 2021-11-03
 */
public class QiniuUtils {
    /**
     * 日志记录器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(QiniuUtils.class);

    /**
     * upload2 qiniu
     *
     * @param accessKey 访问密钥
     * @param secretKey 秘密密钥
     * @param bucket    桶
     * @param bytes     字节
     * @param fileName  文件名称
     */
    public static void upload2Qiniu(String accessKey, String secretKey, String bucket, byte[] bytes,
                                    String fileName) {
        //构造一个带指定Region对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名，这里建议由自己来控制文件名
        String key = fileName;
        //通常这里得到文件的字节数组
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            LOGGER.error(r.toString());
            try {
                LOGGER.error(r.bodyString());
            } catch (QiniuException e) {
                e.printStackTrace();
            }
            throw new RuntimeException(r.toString());
        }
    }

}

 

