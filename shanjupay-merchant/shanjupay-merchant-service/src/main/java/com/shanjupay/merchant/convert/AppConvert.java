package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.api.dto.AppDTO;
import com.shanjupay.merchant.entity.App;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * appconvert
 *
 * @author guolonghang
 * @date 2021-11-04
 */
@Mapper
public interface AppConvert {

    AppConvert instance = Mappers.getMapper(AppConvert.class);

    /**
     * app dto实体
     *
     * @param appDTO 应用dto
     * @return {@code App}
     */
    App appDtoToEntity(AppDTO appDTO);

    /**
     * 实体appdto
     *
     * @param app 应用程序
     * @return {@code AppDTO}
     */
    AppDTO entityToAPPDto(App app);

    /**
     * dto实体列表
     *
     * @param appList 应用程序列表
     * @return {@code List<AppDTO>}
     */
    List<AppDTO> listEntityToDto(List<App> appList);
}
