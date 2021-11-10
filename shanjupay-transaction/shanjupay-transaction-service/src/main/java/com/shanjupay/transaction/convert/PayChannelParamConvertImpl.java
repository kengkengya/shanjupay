package com.shanjupay.transaction.convert;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.transaction.api.dto.PayChannelParamDTO;
import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import com.shanjupay.transaction.entity.PayChannelParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付通道参数转换impl
 *
 * @author guolonghang
 * @date 2021-11-10
 */
public class PayChannelParamConvertImpl implements PayChannelParamConvert{
    /**
     * entity2dto
     *
     * @param entity 实体
     * @return {@code PayChannelParamDTO}
     */
    @Override
    public PayChannelParamDTO entity2dto(PayChannelParam entity) {
        if (null==entity){
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        PayChannelParamDTO payChannelParamDTO = new PayChannelParamDTO();
        payChannelParamDTO.setMerchantId(entity.getMerchantId());
        payChannelParamDTO.setParam(entity.getParam());
        payChannelParamDTO.setPayChannel(entity.getPayChannel());
        payChannelParamDTO.setChannelName(entity.getChannelName());
        payChannelParamDTO.setAppPlatformChannelId(entity.getAppPlatformChannelId());

        return payChannelParamDTO;
    }

    /**
     * dto2entity
     *
     * @param dto dto
     * @return {@code PayChannelParam}
     */
    @Override
    public PayChannelParam dto2entity(PayChannelParamDTO dto) {
        if (null==dto){
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        PayChannelParam payChannelParam = new PayChannelParam();
        payChannelParam.setMerchantId(dto.getMerchantId());
        payChannelParam.setParam(dto.getParam());
        payChannelParam.setPayChannel(dto.getPayChannel());
        payChannelParam.setChannelName(dto.getChannelName());
        payChannelParam.setAppPlatformChannelId(dto.getAppPlatformChannelId());

        return payChannelParam;
    }

    /**
     * listentity2listdto
     *
     * @param platformChannel 平台通道
     * @return {@code List<PayChannelParamDTO>}
     */
    @Override
    public List<PayChannelParamDTO> listentity2listdto(List<PayChannelParam> platformChannel) {
        if (null==platformChannel){
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        List<PayChannelParamDTO> list = new ArrayList<>();
        for (PayChannelParam payChannelParam : platformChannel) {
            list.add(entity2dto(payChannelParam));
        }
        return list;
    }

    /**
     * listdto2listentity
     *
     * @param platformChannelDTO 平台通道dto
     * @return {@code List<PayChannelParam>}
     */
    @Override
    public List<PayChannelParam> listdto2listentity(List<PayChannelParamDTO> platformChannelDTO) {

        if (null==platformChannelDTO){
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        List<PayChannelParam> list = new ArrayList<>();
        for (PayChannelParamDTO platformChanneldto : platformChannelDTO) {
            list.add(dto2entity(platformChanneldto));
        }
        return list;
    }
}
