package com.shanjupay.transaction.convert;

import com.shanjupay.transaction.api.dto.OrderResultDTO;
import com.shanjupay.transaction.api.dto.PayOrderDTO;
import com.shanjupay.transaction.entity.PayOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 支付订单转换
 *
 * @author guolonghang
 * @date 2021-11-08
 */
@Mapper
public interface PayOrderConvert {

    PayOrderConvert INSTANCE = Mappers.getMapper(PayOrderConvert.class);

    /**
     * request2dto
     *
     * @param payOrderDTO 支付订单dto
     * @return {@code OrderResultDTO}
     */
    OrderResultDTO request2dto(PayOrderDTO payOrderDTO);

    /**
     * dto2request
     *
     * @param OrderResult 订单结果
     * @return {@code PayOrderDTO}
     */
    PayOrderDTO dto2request(OrderResultDTO OrderResult);

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
