package com.shanjupay.merchant.service;

import com.alibaba.fastjson.JSON;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.PhoneUtil;
import com.shanjupay.common.util.StringUtil;
import com.shanjupay.merchant.api.MerchantService;
import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.convert.MerchantCovert;
import com.shanjupay.merchant.entity.Merchant;
import com.shanjupay.merchant.mapper.MerchantMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

/**
 * 商户服务实现
 * Created by Administrator.
 *
 * @author guolonghang
 * @date 2021-11-01
 */
@org.apache.dubbo.config.annotation.Service
@Slf4j
public class MerchantServiceImpl implements MerchantService {

    /**
     * 商户mapper
     */
    @Autowired
    MerchantMapper merchantMapper;

    /**
     * 通过id查询商户
     *
     * @param id id
     * @return {@code MerchantDTO}
     */
    @Override
    public MerchantDTO queryMerchantById(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        return MerchantCovert.instance.entityToDto(merchant);
    }

    /**
     * 商户注册
     *
     * @param merchantDTO 商人dto
     * @return {@code MerchantDTO}
     */
    @Override
    @Transactional
    public MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException {
        if (merchantDTO == null) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        //判断手机号是否为空
        if (StringUtil.isBlank(merchantDTO.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_200230);
        }
        //判断手机号格式是否正确
        if (!PhoneUtil.isMatches(merchantDTO.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_200224);
        }
        //判断用户名是否为空
        if (StringUtil.isBlank(merchantDTO.getUsername())) {
            throw new BusinessException(CommonErrorCode.E_200231);
        }
        //判断密码是否为空
        if (StringUtil.isBlank(merchantDTO.getPassword())) {
            throw new BusinessException(CommonErrorCode.E_200232);
        }

        //将dto转换为entity类
        Merchant entity = MerchantCovert.instance.dtoToEntity(merchantDTO);
        //设置审核状态
        entity.setAuditStatus("0");
        //插入商户表
        try {
            merchantMapper.insert(entity);
        } catch (Exception e) {
            log.info("插入商户表失败，{}", JSON.toJSONString(merchantMapper));
            throw new RuntimeException("插入商户表失败");
        }
        //将entity转换为dto
        MerchantDTO merchantDTONew = MerchantCovert.instance.entityToDto(entity);

        return merchantDTONew;
    }
}
