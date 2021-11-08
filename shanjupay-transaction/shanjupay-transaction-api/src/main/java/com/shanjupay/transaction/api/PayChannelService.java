package com.shanjupay.transaction.api;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.transaction.api.dto.PlatformChannelDTO;

import java.util.List;

/**
 * 支付渠道服务、管理平台支付渠道，原始支付渠道，以及相关配置
 *
 * @author guolonghang
 * @date 2021-11-08
 */
public interface PayChannelService {

    /**
     * 查询支持的服务类型
     *
     * @return {@code List<PlatformChannelDTO>}
     * @throws BusinessException 业务异常
     */
    List<PlatformChannelDTO> queryPlatformChannels() throws BusinessException;

    /**
     * 应用绑定支付方式
     *
     * @param appId                应用程序id
     * @param platformChannelCodes 平台通道编码
     * @throws BusinessException 业务异常
     */
    void bindPlatformChannelForApp(String appId,String platformChannelCodes) throws BusinessException;

    /**
     * 查询应用是否绑定了某个服务
     *
     * @param appId                应用程序id
     * @param platformChannelCodes 平台通道编码
     * @return int
     * @throws BusinessException 业务异常
     */
    int queryAppBindPlatformChannel(String appId, String platformChannelCodes) throws BusinessException;
}
