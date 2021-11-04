package com.shanjupay.merchant.api;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.merchant.api.dto.AppDTO;

import java.util.List;

/**
 * appservice
 *
 * @author guolonghang
 * @date 2021-11-04
 */
public interface AppService {

    /**
     * 创建应用程序
     *
     * @param merchantId 商户id
     * @param app        应用程序
     * @return {@code AppDTO}
     * @throws BusinessException 业务异常
     */
    AppDTO createAPP(Long merchantId,AppDTO app) throws BusinessException;

    /**
     * 根据商户ID查询所属商户应用
     *
     * @param merchantId 商人id
     * @return {@code List<AppDTO>}
     * @throws BusinessException 业务异常
     */
    List<AppDTO> queryAppByMerchant(Long merchantId) throws  BusinessException;

    /**
     * 通过id获取应用程序
     *
     * @param id id
     * @return {@code AppDTO}
     * @throws BusinessException 业务异常
     */
    AppDTO getAppById(String id) throws BusinessException;
}
