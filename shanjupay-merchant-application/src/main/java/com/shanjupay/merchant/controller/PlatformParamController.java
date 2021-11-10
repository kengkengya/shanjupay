package com.shanjupay.merchant.controller;

import com.shanjupay.merchant.common.util.SecurityUtil;
import com.shanjupay.transaction.api.PayChannelService;
import com.shanjupay.transaction.api.dto.PayChannelDTO;
import com.shanjupay.transaction.api.dto.PayChannelParamDTO;
import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 平台支付参数controller
 *
 * @author guolonghang
 * @date 2021-11-08
 */
@Api(value = "商户平台‐渠道和支付参数相关", tags = "商户平台‐渠道和支付参数", description = "商户平 台‐渠道和支付参数相关")
@Slf4j
@RestController
public class PlatformParamController {

    @org.apache.dubbo.config.annotation.Reference
    PayChannelService payChannelService;

    @ApiOperation("查询所有支付服务")
    @ApiImplicitParam(name = "queryPlatformChannels", value = "查询所有支付服务")
    @GetMapping("/my/platform-channels")
    public List<PlatformChannelDTO> queryPlatformChannels() {
        return payChannelService.queryPlatformChannels();
    }

    @ApiOperation("绑定服务类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用ID", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "platformChannel", value = "平台支付类型", dataType = "String", paramType = "query")
    })
    @PostMapping("/my/apps/{appId}/platform‐channels")
    public void bindPlatformForApp(@PathVariable("appId") String appId, @RequestParam("platformChannelCodes") String platformChannelCodes) {
        payChannelService.bindPlatformChannelForApp(appId, platformChannelCodes);
    }

    @ApiOperation("查询应用是否绑定服务类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用ID", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "platformChannel", value = "平台支付类型", dataType = "String", paramType = "query")
    })
    @GetMapping("/my/merchants/apps/platform-channels")
    public int queryAppBindPlatformChannel(@RequestParam("appId") String appId, @RequestParam("platformChannelCodes") String platformChannel) {
        log.info("appId:{}", appId);
        log.info("platformChannel:{}", platformChannel);
        return payChannelService.queryAppBindPlatformChannel(appId, platformChannel);
    }

    @ApiOperation("根据平台服务类型获取支付渠道列表")
    @ApiImplicitParam(name = "platformChannelCodes", value = "服务类型编码", dataType = "String", paramType = "path", required = true)
    @GetMapping("/my/pay‐channels/platform‐channel/{platformChannelCodes}")
    public List<PayChannelDTO> queryPayChannelByPlatformChannel(@PathVariable("platformChannelCodes") String platformChannelCodes) {
        return payChannelService.queryPayChannelByPlatformChannel(platformChannelCodes);
    }

    @ApiOperation("商户配置支付渠道参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payChannelParam", value = "商户配置支付渠道参数", required = true, dataType = "PayChannelParamDTO", paramType = "body")
    })
    @RequestMapping(value = "/my/pay‐channel‐params", method = {RequestMethod.POST, RequestMethod.PUT})
    public void createPayChannelParam(@RequestBody PayChannelParamDTO payChannelParamDTO) {
        Long merchantId = SecurityUtil.getMerchantId();
        payChannelParamDTO.setMerchantId(merchantId);
        payChannelService.savePayChannelParam(payChannelParamDTO);
    }

    @ApiOperation("获取指定应用指定服务类型下所包含的原始支付渠道参数列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "platformChannel", value = "服务类型", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/my/pay‐channel‐params/apps/{appId}/platform‐ channels/{platformChannel}")
    public List<PayChannelParamDTO> queryPayChannelParam(@PathVariable("appId") String appId, @PathVariable("platformChannel") String platformChannelCodes) {
        return payChannelService.queryPayChannelParamByAppAndPlatform(appId, platformChannelCodes);
    }

    @ApiOperation("获取指定应用指定服务类型下所包含的某个原始支付渠道参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "platformChannel", value = "服务类型", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "payChannel", value = "支付渠道", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/my/pay‐channel‐params/apps/{appId}/platform‐ channels/{platformChannel}/pay‐channels/{payChannel}")
    public PayChannelParamDTO queryPayChannelParam(@PathVariable("appId") String appId, @PathVariable("platformChannel") String platformChannelCodes,
                                                   @PathVariable("payChannel") String payChannel) {
        return payChannelService.queryParamByAppPlatformAndPayChannel(appId, platformChannelCodes, payChannel);
    }

}
