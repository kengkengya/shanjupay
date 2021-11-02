package com.shanjupay.merchant.controller;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.PhoneUtil;
import com.shanjupay.common.util.StringUtil;
import com.shanjupay.merchant.api.MerchantService;
import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.convert.MerchantRegisterConvert;
import com.shanjupay.merchant.service.SmsService;
import com.shanjupay.merchant.vo.MerchantRegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version 1.0
 **/
@Slf4j
@RestController
@Api(value = "商户平台应用接口", tags = "商户平台应用接口", description = "商户平台应用接口")
public class MerchantController {

    /**
     * 注入远程的bean
     */
    @org.apache.dubbo.config.annotation.Reference
    MerchantService merchantService;

    /**
     * 将本地的bean进行注入
     */
    @Autowired
    SmsService smsService;

    @ApiOperation(value = "根据id查询商户信息")
    @GetMapping("/merchants/{id}")
    public MerchantDTO queryMerchantById(@PathVariable("id") Long id) {

        MerchantDTO merchantDTO = merchantService.queryMerchantById(id);
        return merchantDTO;
    }

    @ApiOperation("测试")
    @GetMapping(path = "/hello")
    public String hello() {
        return "hello";
    }

    @ApiOperation("测试")
    @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "string")
    @PostMapping(value = "/hi")
    public String hi(String name) {
        return "hi," + name;
    }

    @ApiOperation("获取手机验证码")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "string", paramType = "query")
    @GetMapping("/sms")
    public String getSMSCode(@RequestParam String phone) {
        log.info("向手机号:{}发送验证码", phone);
        //向验证码服务请求发送验证码
        String msg = smsService.sendMsg(phone);
        return msg;
    }

    @ApiOperation("商户注册")
    @ApiImplicitParam(name = "merchantRegister", value = "商户注册", required = true, dataType = "MerchantRegisterVO",
            paramType = "body")
    @PostMapping("/merchants/register")
    public MerchantRegisterVO registerMerchant(@RequestBody MerchantRegisterVO merchantRegister) {
        if (merchantRegister == null) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        //判断手机号是否为空
        if (StringUtil.isBlank(merchantRegister.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_200230);
        }
        //判断手机号格式是否正确
        if (!PhoneUtil.isMatches(merchantRegister.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_200224);
        }
        //判断用户名是否为空
        if (StringUtil.isBlank(merchantRegister.getUsername())) {
            throw new BusinessException(CommonErrorCode.E_200231);
        }
        //判断密码是否为空
        if (StringUtil.isBlank(merchantRegister.getPassword())) {
            throw new BusinessException(CommonErrorCode.E_200232);
        }
        //校验验证码
        smsService.checkVerifiyCode(merchantRegister.getVerifiykey(), merchantRegister.getVerifiyCode());
        //注册商户,将vo类转换成dto
        MerchantDTO merchantDTO = MerchantRegisterConvert.instance.merchantRegisterVOToDto(merchantRegister);
        merchantService.createMerchant(merchantDTO);
        return merchantRegister;
    }
}
