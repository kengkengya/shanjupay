package com.shanjupay.merchant.api;

import com.shanjupay.merchant.api.dto.MerchantDTO;

/**
 * 商家服务
 * Created by Administrator.
 *
 * @author guolonghang
 * @date 2021-11-01
 */
public interface MerchantService {

    /**
     * 通过id查询商户
     *
     * @param id id
     * @return {@code MerchantDTO}
     */
    MerchantDTO queryMerchantById(Long id);

    /**
     *
     * 商户注册
     * 只接受商户账号、密码、手机号等商户信息，为了提高可扩展性，使用DTO
     * @param merchantDTO 商人dto
     * @return {@code MerchantDTO}
     */
    MerchantDTO createMerchant(MerchantDTO merchantDTO);
}
