package com.shanjupay.transaction.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shanjupay.common.cache.Cache;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.RedisUtil;
import com.shanjupay.common.util.StringUtil;
import com.shanjupay.transaction.api.PayChannelService;
import com.shanjupay.transaction.api.dto.PayChannelDTO;
import com.shanjupay.transaction.api.dto.PayChannelParamDTO;
import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import com.shanjupay.transaction.convert.PayChannelParamConvert;
import com.shanjupay.transaction.convert.PlatformChannelConvert;
import com.shanjupay.transaction.entity.AppPlatformChannel;
import com.shanjupay.transaction.entity.PayChannelParam;
import com.shanjupay.transaction.entity.PlatformChannel;
import com.shanjupay.transaction.mapper.AppPlatformChannelMapper;
import com.shanjupay.transaction.mapper.PayChannelMapper;
import com.shanjupay.transaction.mapper.PayChannelParamMapper;
import com.shanjupay.transaction.mapper.PlatformChannelMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
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

    @Autowired
    private PayChannelParamMapper payChannelParamMapper;

    @Resource
    private Cache cache;


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


    /**
     * 查询支付渠道列表
     *
     * @param platformChannelCodes 平台通道编码
     * @return {@code List<PayChannelDTO>}
     * @throws BusinessException 业务异常
     */
    @Override
    public List<PayChannelDTO> queryPayChannelByPlatformChannel(String platformChannelCodes) throws BusinessException {
        List<PayChannelDTO> payChannelDTOS = platformChannelMapper.selectPayChannelByPlatformChannel(platformChannelCodes);
        log.info("payChannelDTOS:{}", payChannelDTOS);
        return payChannelDTOS;

    }


    /**
     * 保存支付通道参数
     *
     * @param payChannelParamDTO 支付通道参数dto
     * @throws BusinessException 业务异常
     */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void savePayChannelParam(PayChannelParamDTO payChannelParamDTO) throws BusinessException {
        //传入对象为空
        if (null == payChannelParamDTO) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        //应用id为空
        if (StringUtil.isBlank(payChannelParamDTO.getAppId())) {
            throw new BusinessException(CommonErrorCode.E_200202);
        }
        //支付渠道为空
        if (StringUtil.isBlank(payChannelParamDTO.getPayChannel())) {
            throw new BusinessException(CommonErrorCode.E_200202);
        }
        //服务类型代码为空
        if (StringUtil.isBlank(payChannelParamDTO.getPlatformChannelCode())) {
            throw new BusinessException(CommonErrorCode.E_200202);
        }
        //更新缓存   
        updateCache(payChannelParamDTO.getAppId(), payChannelParamDTO.getPlatformChannelCode());

        //根据appId和服务类型查询应用与服务类型绑定id
        Long appPlatformChannelId = selectIdByAppPlatformChannel(payChannelParamDTO.getAppId(),
                payChannelParamDTO.getPlatformChannelCode());
        //根据应用与服务类型绑定id和支付渠道 查询参数信息
        PayChannelParam payChannelParam = payChannelParamMapper.selectOne(new LambdaQueryWrapper<PayChannelParam>()
                .eq(PayChannelParam::getAppPlatformChannelId, appPlatformChannelId)
                .eq(PayChannelParam::getPayChannel, payChannelParamDTO.getPayChannel()));

        //如果参数信息不为空，则更新部分
        if (null != payChannelParam) {
            payChannelParam.setParam(payChannelParamDTO.getParam());
            payChannelParam.setChannelName(payChannelParamDTO.getChannelName());
            int update = payChannelParamMapper.updateById(payChannelParam);
            log.info("已更新 {} ", update + "条数据");
        } else {
            //将DTO转化对象，进行插入保存
            PayChannelParam entity = PayChannelParamConvert.INSTANCE.dto2entity(payChannelParamDTO);
            entity.setId(null);
            //应用与服务类型绑定id
            entity.setAppPlatformChannelId(appPlatformChannelId);
            int insert = payChannelParamMapper.insert(entity);
            log.info("已插入 {} ", insert + "条数据");
        }

    }

    /**
     * 更新缓存
     *
     * @param appId               应用程序id
     * @param platformChannelCode 平台渠道代码
     */
    private void updateCache(String appId, String platformChannelCode) {
        //处理redis缓存
        //1.key的构建
        String redisKey = RedisUtil.keyBuilder(appId, platformChannelCode);
        //2.查询key是否存在
        Boolean exists = cache.exists(redisKey);
        if (exists) {
            //存在则清除
            cache.del(redisKey);
            log.info("已删除一条缓存：{}",redisKey);
        }
        //3.从数据库查询应用的服务类型对应的实际支付参数，并重新存入缓存
        List<PayChannelParamDTO> payChannelParamDTOS = queryPayChannelParamByAppAndPlatform(appId, platformChannelCode);
        if (null == payChannelParamDTOS) {
            throw new BusinessException(CommonErrorCode.E_200202);
        }
        //将数据放入到缓存中去
        cache.set(redisKey, JSON.toJSON(payChannelParamDTOS).toString());
        log.info("已更新一条缓存：{}",redisKey);

    }

    /**
     * 根据appid和服务类型查询应用与服务类型绑定id
     *
     * @param appId               应用程序id
     * @param platformChannelCode 平台渠道代码
     * @return {@code Long}
     */
    private Long selectIdByAppPlatformChannel(String appId, String platformChannelCode) {
        //根据appid和服务类型查询应用与服务类型绑定id
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCode));
        if (null == appPlatformChannel) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        //应用未绑定该服务类型不可进行支付渠道参数配置
        if (StringUtil.isBlank(appPlatformChannel.getId().toString())) {
            throw new BusinessException(CommonErrorCode.E_200202);
        }
        return appPlatformChannel.getId();
    }

    /**
     * 获取指定应用指定服务类型下所包含的原始支付渠道参数列表
     *
     * @param appId                应用程序id
     * @param platformChannelCodes 平台通道编码
     * @return {@code List<PayChannelParamDTO>}
     * @throws BusinessException 业务异常
     */
    @Override
    public List<PayChannelParamDTO> queryPayChannelParamByAppAndPlatform(String appId, String platformChannelCodes) throws BusinessException {
        if (StringUtil.isEmpty(appId) || StringUtil.isEmpty(platformChannelCodes)) {
            throw new BusinessException(CommonErrorCode.E_200202);
        }

        String redisKey = RedisUtil.keyBuilder(appId, platformChannelCodes);
        Boolean exists = cache.exists(redisKey);
        log.info("redisKey：{}",exists);
        //如果缓存存在
        if (exists) {
            String value = cache.get(redisKey);
            List<PayChannelParamDTO> payChannelParamDTOS = JSON.parseArray(value, PayChannelParamDTO.class);
            return payChannelParamDTOS;
        }
        //如果缓存不存在，先查询应用id和服务类型代码在app_platform_channel主键
        Long appPlatformChannelId = selectIdByAppPlatformChannel(appId, platformChannelCodes);
        if (appPlatformChannelId == null) {
            throw new BusinessException(CommonErrorCode.E_200202);
        }
        List<PayChannelParam> payChannelParams = payChannelParamMapper.selectList(new LambdaQueryWrapper<PayChannelParam>()
                .eq(PayChannelParam::getAppPlatformChannelId, appPlatformChannelId)
        );
        return PayChannelParamConvert.INSTANCE.listentity2listdto(payChannelParams);
    }

    /**
     * 获取指定应用指定服务类型下所包含的某个原始支付参数
     *
     * @param appId                应用程序id
     * @param payChannel           支付通道
     * @param platformChannelCodes 平台通道编码
     * @return {@code PayChannelParamDTO}
     * @throws BusinessException 业务异常
     */
    @Override
    public PayChannelParamDTO queryParamByAppPlatformAndPayChannel(String appId, String platformChannelCodes, String payChannel) throws BusinessException {
        if (StringUtil.isEmpty(appId) || StringUtil.isEmpty(platformChannelCodes) || StringUtil.isEmpty(payChannel)) {
            throw new BusinessException(CommonErrorCode.E_200202);
        }
        Long appPlatformChannelId = selectIdByAppPlatformChannel(appId, platformChannelCodes);
        if (appPlatformChannelId == null) {
            throw new BusinessException(CommonErrorCode.E_200202);
        }
        PayChannelParam payChannelParam = payChannelParamMapper.selectOne(new LambdaQueryWrapper<PayChannelParam>()
                .eq(PayChannelParam::getAppPlatformChannelId, appPlatformChannelId)
                .eq(PayChannelParam::getPayChannel, payChannel));

        return PayChannelParamConvert.INSTANCE.entity2dto(payChannelParam);
    }
}
