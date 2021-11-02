package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 商户对象转换器--服务层
 *
 * @author guolonghang
 * @date 2021-11-02
 */
@Mapper
public interface MerchantCovert {

    /**
     * 实例
     */
    MerchantCovert instance = Mappers.getMapper(MerchantCovert.class);

    /**
     * 实体类转Dto对象
     *
     * @param merchant 商户
     * @return {@code MerchantDTO}
     */
    MerchantDTO entityToDto(Merchant merchant);

    /**
     * Dto对象转实体类
     *
     * @param merchantDTO 商户Dto
     * @return {@code Merchant}
     */
    Merchant dtoToEntity(MerchantDTO merchantDTO);

}
