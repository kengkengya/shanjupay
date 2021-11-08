package com.shanjupay.transaction.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.transaction.api.PayChannelService;
import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import com.shanjupay.transaction.convert.PlatformChannelConvert;
import com.shanjupay.transaction.entity.AppPlatformChannel;
import com.shanjupay.transaction.entity.PlatformChannel;
import com.shanjupay.transaction.mapper.AppPlatformChannelMapper;
import com.shanjupay.transaction.mapper.PlatformChannelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 支付渠道服务、管理平台支付渠道，原始支付渠道，以及相关配置impl
 *
 * @author guolonghang
 * @date 2021-11-08
 */
@Slf4j
@org.apache.dubbo.config.annotation.Service
public class PayChannelServiceImpl implements PayChannelService {

    @Autowired
    private PlatformChannelMapper platformChannelMapper;

    @Autowired
    private AppPlatformChannelMapper appPlatformChannelMapper;

    /**
     * 查询支持的所有服务
     *
     * @return {@code List<PlatformChannelDTO>}
     * @throws BusinessException 业务异常
     */
    @Override
    public List<PlatformChannelDTO> queryPlatformChannels() throws BusinessException {

        List<PlatformChannel> platformChannelList = platformChannelMapper.selectList(null);
        List<PlatformChannelDTO> platformChannelDTOS = PlatformChannelConvert.INSTANCE.listentity2listdto(platformChannelList);
        log.info("支持的服务如下：{}", platformChannelList);
        return platformChannelDTOS;
    }

    /**
     * 应用绑定某种服务类型
     *
     * @param appId                应用程序id
     * @param platformChannelCodes 平台通道编码
     * @throws BusinessException 业务异常
     */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void bindPlatformChannelForApp(String appId, String platformChannelCodes) throws BusinessException {
        //根据appId和平台服务类型绑定app_platform_channel (应用和支付类型对应表)
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCodes));
        log.info("已查询到：{}", JSON.parseObject(String.valueOf(appPlatformChannel)));
        // 如果该应用没有此支付类型，则创建。
        if (null == appPlatformChannel) {
            AppPlatformChannel channel = new AppPlatformChannel();
            channel.setAppId(appId);
            channel.setPlatformChannel(platformChannelCodes);
            int insert = appPlatformChannelMapper.insert(channel);
            log.info("已插入 {} 条", insert);
        }

    }

    /**
     * 查询应用程序是否绑定了某个服务类型
     *
     * @param appId                应用程序id
     * @param platformChannelCodes 平台通道编码
     * @return int
     * @throws BusinessException 业务异常
     */
    @Override
    public int queryAppBindPlatformChannel(String appId, String platformChannelCodes) throws BusinessException {
        //根据appId和平台服务类型绑定app_platform_channel (应用和支付类型对应表)
        Integer count = appPlatformChannelMapper.selectCount(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCodes));
        log.info("已查询到：{}", count);

        if (count > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
