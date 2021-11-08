package com.shanjupay.transaction.convert;

import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import com.shanjupay.transaction.entity.PlatformChannel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 平台转换频道
 *
 * @author guolonghang
 * @date 2021-11-08
 */
@Mapper
public interface PlatformChannelConvert {

    PlatformChannelConvert INSTANCE = Mappers.getMapper(PlatformChannelConvert.class);

    /**
     * entity2dto
     *
     * @param entity 实体
     * @return {@code PlatformChannelDTO}
     */
    PlatformChannelDTO entity2dto(PlatformChannel entity);

    /**
     * dto2entity
     *
     * @param dto dto
     * @return {@code PlatformChannel}
     */
    PlatformChannel dto2entity(PlatformChannelDTO dto);

    /**
     * listentity2listdto
     *
     * @param PlatformChannel 平台通道
     * @return {@code List<PlatformChannelDTO>}
     */
    List<PlatformChannelDTO> listentity2listdto(List<PlatformChannel> PlatformChannel);
}
