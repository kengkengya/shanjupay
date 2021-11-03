package com.shanjupay.merchant.controller;

import com.alibaba.fastjson.JSON;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.PhoneUtil;
import com.shanjupay.common.util.StringUtil;
import com.shanjupay.merchant.api.MerchantService;
import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.vo.MerchantDetailVO;
import com.shanjupay.merchant.common.util.SecurityUtil;
import com.shanjupay.merchant.convert.MerchantDetailConvert;
import com.shanjupay.merchant.convert.MerchantRegisterConvert;
import com.shanjupay.merchant.service.FileService;
import com.shanjupay.merchant.service.SmsService;
import com.shanjupay.merchant.vo.MerchantRegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.BatchUpdateException;
import java.util.UUID;

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

    /**
     * 文件服务
     */
    @Autowired
    FileService fileService;

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

    @ApiOperation("证件上传")
    @ApiImplicitParam(name = "upload", value = "文件上传", required = true)
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException, BatchUpdateException {

        //获取文件名称
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成新的文件名字
        String fileName = UUID.randomUUID().toString() + suffix;
        //上传文件，返回文件下载url
        String fileUrl = fileService.upload(file.getBytes(), fileName);
        return fileUrl;
    }

    @ApiOperation("商户资质申请")
    @ApiImplicitParam(name = "saveMerchant", value = "商户资质申请", required = true, dataType = "MerchantDetailVO", paramType = "body")
    @PostMapping("/my/merchant/save")
    public void saveMerchant(@RequestBody MerchantDetailVO merchantDetailVO) throws BusinessException {

        if (null == merchantDetailVO) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        //解析模拟token得到商户id
        Long merchantId = SecurityUtil.getMerchantId();
        log.info("商户id：{}",merchantId);
        log.info("merchantDetailVO:{}", JSON.toJSONString(merchantDetailVO));
        MerchantDTO merchantDTO = MerchantDetailConvert.instance.merchantVoToDto(merchantDetailVO);
        log.info("merchantDTO:{}", JSON.toJSONString(merchantDTO));
        merchantService.applyMerchant(merchantId, merchantDTO);
    }


}
