package com.shanjupay.merchant.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.RandomUuidUtil;
import com.shanjupay.merchant.api.AppService;
import com.shanjupay.merchant.api.dto.AppDTO;
import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.convert.AppConvert;
import com.shanjupay.merchant.entity.App;
import com.shanjupay.merchant.entity.Merchant;
import com.shanjupay.merchant.mapper.AppMapper;
import com.shanjupay.merchant.mapper.MerchantMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 应用程序服务impl
 *
 * @author guolonghang
 * @date 2021-11-04
 */
@Service
@Slf4j
public class AppServiceImpl implements AppService {

    /**
     * 应用映射器
     */
    @Autowired
    private AppMapper appMapper;

    /**
     * 商人映射器
     */
    @Autowired
    private MerchantMapper merchantMapper;


    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public List<AppDTO> queryAppByMerchant(Long merchantId) throws BusinessException {
        QueryWrapper<App> wrapper = new QueryWrapper<>();
        wrapper.eq("MERCHANT_ID",merchantId);
        List<App> apps = appMapper.selectList(wrapper);
        if (null==apps){
            log.info("该商户 {} 下应用数量为0",merchantId);
        }
        return AppConvert.instance.listEntityToDto(apps);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppDTO getAppById(String id) throws BusinessException {

        QueryWrapper<App> wrapper = new QueryWrapper<>();
        wrapper.eq("APP_ID",id);
        App app = appMapper.selectOne(wrapper);
        if (null ==app){
            throw new RuntimeException("应用不存在");
        }

        return AppConvert.instance.entityToAPPDto(app);
    }

    /**
     * 创建应用程序
     *
     * @param merchantId 商户id
     * @param app        应用程序
     * @return {@code AppDTO}
     * @throws BusinessException 业务异常
     */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public AppDTO createAPP(Long merchantId, AppDTO app) throws BusinessException {

        //通过id查询对应对商户是否存在
        Merchant merchant = merchantMapper.selectById(merchantId);
        log.info("merchant: {}", JSON.toJSONString(merchant));
        if (null == merchant) {
            throw new BusinessException(CommonErrorCode.E_200227);
        }
        //查询资质是否通过
        if (!"2".equals(merchant.getAuditStatus())) {
            throw new BusinessException(CommonErrorCode.E_200236);
        }

        if (isExistAppName(app.getAppName())) {
            log.info("app {} 已经存在", app.getAppName());
            throw new RuntimeException("app已经存在");
        }

        //商户保存应用信息
        app.setAppId(RandomUuidUtil.getUUID());
        app.setMerchantId(merchantId);
        App entity = AppConvert.instance.appDtoToEntity(app);
        //保存应用信息
        try {
            appMapper.insert(entity);
        } catch (Exception e) {
            log.info("保存应用失败：{}", e.getMessage());
            throw new RuntimeException("保存应用失败");
        }
        return AppConvert.instance.entityToAPPDto(entity);
    }

    /**
     * 是否存在应用程序名称
     *
     * @param appName 应用程序名称
     * @return {@code Boolean}
     */
    public Boolean isExistAppName(String appName) {
        Integer count = appMapper.selectCount(new QueryWrapper<App>
                ().lambda().eq(App::getAppName, appName));
        return count.intValue() > 0;
    }
}
