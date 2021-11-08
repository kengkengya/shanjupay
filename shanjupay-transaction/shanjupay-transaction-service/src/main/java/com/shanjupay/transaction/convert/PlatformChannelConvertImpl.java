package com.shanjupay.transaction.convert;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import com.shanjupay.transaction.entity.PlatformChannel;

import java.util.ArrayList;
import java.util.List;

public class PlatformChannelConvertImpl implements PlatformChannelConvert{
    @Override
    public PlatformChannelDTO entity2dto(PlatformChannel entity) {
        if (null==entity){
            throw new BusinessException(CommonErrorCode.E_200201);
        }

        PlatformChannelDTO platformChannelDTO = new PlatformChannelDTO();
        platformChannelDTO.setId(entity.getId());
        platformChannelDTO.setChannelCode(entity.getChannelCode());
        platformChannelDTO.setChannelName(entity.getChannelName());
        return platformChannelDTO;
    }

    @Override
    public PlatformChannel dto2entity(PlatformChannelDTO dto) {
        if (null==dto){
            throw new BusinessException(CommonErrorCode.E_200201);
        }

        PlatformChannel platformChannel = new PlatformChannel();
        platformChannel.setId(platformChannel.getId());
        platformChannel.setChannelCode(dto.getChannelCode());
        platformChannel.setChannelName(dto.getChannelName());
        return platformChannel;
    }

    @Override
    public List<PlatformChannelDTO> listentity2listdto(List<PlatformChannel> PlatformChannel) {
        if (PlatformChannel.isEmpty()){
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        List<PlatformChannelDTO> list = new ArrayList<>();
        for (PlatformChannel platformChannel : PlatformChannel) {
            list.add(entity2dto(platformChannel));
        }

        return list;
    }
}
