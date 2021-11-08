package com.shanjupay.transaction.convert;

import com.shanjupay.transaction.api.dto.OrderResultDTO;
import com.shanjupay.transaction.entity.PayOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 订单转换结果
 *
 * @author guolonghang
 * @date 2021-11-08
 */
@Mapper
public interface OrderResultConvert {

    OrderResultConvert INSTANCE = Mappers.getMapper(OrderResultConvert.class);

    /**
     * entity2dto
     *
     * @param entity 实体
     * @return {@code OrderResultDTO}
     */
    OrderResultDTO entity2dto(PayOrder entity);

    /**
     * dto2entity
     *
     * @param dto dto
     * @return {@code PayOrder}
     */
    PayOrder dto2entity(OrderResultDTO dto);
}
