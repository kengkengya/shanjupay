package com.shanjupay.transaction.convert;

import com.shanjupay.transaction.api.dto.PayChannelParamDTO;
import com.shanjupay.transaction.entity.PayChannelParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 支付通道参数转换
 *
 * @author guolonghang
 * @date 2021-11-08
 */
@Mapper
public interface PayChannelParamConvert {

    PayChannelParamConvert INSTANCE= Mappers.getMapper(PayChannelParamConvert.class);

    /**
     * entity2dto
     *
     * @param entity 实体
     * @return {@code PayChannelParamDTO}
     */
    PayChannelParamDTO entity2dto(PayChannelParam entity);

    /**
     * dto2entity
     *
     * @param dto dto
     * @return {@code PayChannelParam}
     */
    PayChannelParam dto2entity(PayChannelParamDTO dto);

    /**
     * listentity2listdto
     *
     * @param PlatformChannel 平台通道
     * @return {@code List<PayChannelParamDTO>}
     */
    List<PayChannelParamDTO> listentity2listdto(List<PayChannelParam> PlatformChannel);

    /**
     * listdto2listentity
     *
     * @param PlatformChannelDTO 平台通道dto
     * @return {@code List<PayChannelParam>}
     */
    List<PayChannelParam> listdto2listentity(List<PayChannelParamDTO> PlatformChannelDTO);
}
