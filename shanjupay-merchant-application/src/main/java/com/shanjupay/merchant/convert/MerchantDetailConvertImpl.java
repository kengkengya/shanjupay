package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.vo.MerchantDetailVO;

public class MerchantDetailConvertImpl implements MerchantDetailConvert {
    @Override
    public MerchantDetailVO merchantDtoToVo(MerchantDTO merchantDTO) {
        if (null == merchantDTO) {
            return null;
        }
        MerchantDetailVO merchantDetailVO = new MerchantDetailVO();
        merchantDetailVO.setMerchantNo(merchantDTO.getMerchantNo());
        merchantDetailVO.setMerchantName(merchantDTO.getMerchantName());
        merchantDetailVO.setMerchantType(merchantDTO.getMerchantType());
        merchantDetailVO.setBusinessLicensesImg(merchantDTO.getBusinessLicensesImg());
        merchantDetailVO.setContactsAddress(merchantDTO.getContactsAddress());
        merchantDetailVO.setIdCardAfterImg(merchantDTO.getIdCardAfterImg());
        merchantDetailVO.setIdCardFrontImg(merchantDTO.getIdCardFrontImg());

        return merchantDetailVO;
    }

    @Override
    public MerchantDTO merchantVoToDto(MerchantDetailVO merchantDetailVO) {
        if (null == merchantDetailVO) {
            return null;
        }
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setMerchantNo(merchantDetailVO.getMerchantNo());
        merchantDTO.setMerchantName(merchantDetailVO.getMerchantName());
        merchantDTO.setMerchantType(merchantDetailVO.getMerchantType());
        merchantDTO.setBusinessLicensesImg(merchantDetailVO.getBusinessLicensesImg());
        merchantDTO.setContactsAddress(merchantDetailVO.getContactsAddress());
        merchantDTO.setIdCardAfterImg(merchantDetailVO.getIdCardAfterImg());
        merchantDTO.setIdCardFrontImg(merchantDetailVO.getIdCardFrontImg());

        return merchantDTO;

    }
}
