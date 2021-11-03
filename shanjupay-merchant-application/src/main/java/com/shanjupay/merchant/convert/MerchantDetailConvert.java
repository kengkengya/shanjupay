package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.vo.MerchantDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 商人细节转换
 *
 * @author guolonghang
 * @date 2021-11-03
 */
@Mapper
public interface MerchantDetailConvert {

    /**
     * 实例
     */
    MerchantDetailConvert instance = Mappers.getMapper(MerchantDetailConvert.class);

    /**
     * 商人登记vo的dto
     * 商户注册vo转dto
     *
     * @param merchantDTO 商户dto
     * @return {@code MerchantDetailVO}
     */
    MerchantDetailVO merchantDtoToVo(MerchantDTO merchantDTO);

    /**
     * 商人登记voto dto
     * 商户注册dto转vo
     *
     * @param merchantDetailVO 商家详细签证官
     * @return {@code MerchantDTO}
     */
    MerchantDTO merchantVoToDto(MerchantDetailVO merchantDetailVO);
}