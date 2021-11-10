package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.api.dto.AppDTO;
import com.shanjupay.merchant.entity.App;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-09T15:26:07+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_312 (Azul Systems, Inc.)"
)
public class AppConvertImpl implements AppConvert {

    @Override
    public App appDtoToEntity(AppDTO appDTO) {
        if ( appDTO == null ) {
            return null;
        }

        App app = new App();

        app.setAppId( appDTO.getAppId() );
        app.setAppName( appDTO.getAppName() );
        app.setMerchantId( appDTO.getMerchantId() );
        app.setPublicKey( appDTO.getPublicKey() );
        app.setNotifyUrl( appDTO.getNotifyUrl() );

        return app;
    }

    @Override
    public AppDTO entityToAPPDto(App app) {
        if ( app == null ) {
            return null;
        }

        AppDTO appDTO = new AppDTO();

        appDTO.setAppId( app.getAppId() );
        appDTO.setAppName( app.getAppName() );
        appDTO.setMerchantId( app.getMerchantId() );
        appDTO.setPublicKey( app.getPublicKey() );
        appDTO.setNotifyUrl( app.getNotifyUrl() );

        return appDTO;
    }

    @Override
    public List<AppDTO> listEntityToDto(List<App> appList) {
        if ( appList == null ) {
            return null;
        }

        List<AppDTO> list = new ArrayList<AppDTO>( appList.size() );
        for ( App app : appList ) {
            list.add( entityToAPPDto( app ) );
        }

        return list;
    }
}
