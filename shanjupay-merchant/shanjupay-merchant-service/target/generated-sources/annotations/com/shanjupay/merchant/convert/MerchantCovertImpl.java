package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.entity.Merchant;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-02T11:27:41+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_312 (Azul Systems, Inc.)"
)
public class MerchantCovertImpl implements MerchantCovert {

    @Override
    public MerchantDTO entityToDto(Merchant merchant) {
        if ( merchant == null ) {
            return null;
        }

        MerchantDTO merchantDTO = new MerchantDTO();

        return merchantDTO;
    }

    @Override
    public Merchant dtoToEntity(MerchantDTO merchantDTO) {
        if ( merchantDTO == null ) {
            return null;
        }

        Merchant merchant = new Merchant();

        return merchant;
    }
}
