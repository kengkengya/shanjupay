package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.vo.MerchantRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * 商户注册对象转换--应用层  dto与vo的转换
 *
 * @author guolonghang
 * @date 2021-11-02
 */
@Mapper
public interface MerchantRegisterConvert {

    /**
     * 实例
     */
    MerchantRegisterConvert instance = Mappers.getMapper(MerchantRegisterConvert.class);

    /**
     * 商户注册vo转dto
     *
     * @param merchantDTO 商户dto
     * @return {@code MerchantRegisterVO}
     */
    MerchantRegisterVO dtoToMerchantRegisterVo(MerchantDTO merchantDTO);

    /**
     * 商户注册dto转vo
     *
     * @param merchantRegisterVO 商户注册
     * @return {@code MerchantDTO}
     */
    MerchantDTO merchantRegisterVOToDto(MerchantRegisterVO merchantRegisterVO);
}
