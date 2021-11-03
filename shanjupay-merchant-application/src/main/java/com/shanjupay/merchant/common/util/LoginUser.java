package com.shanjupay.merchant.common.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录用户
 *
 * @author guolonghang
 * @date 2021-11-03
 */
@Data
public class LoginUser {
	private String mobile;
	private Map<String, Object> payload = new HashMap<>();
	private String clientId;
	private String username;

}
