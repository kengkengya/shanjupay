package com.shanjupay.merchant.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

public class SmsServiceImplTest {
    @Mock
    RestTemplate restTemplate;
    @Mock
    Logger log;
    @InjectMocks
    SmsServiceImpl smsServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendMsg() throws Exception {
        String result = smsServiceImpl.sendMsg("123456");
        Assert.assertEquals("123456", result);
    }

    @Test
    public void testCheckVerifiyCode() throws Exception {
        smsServiceImpl.checkVerifiyCode("fvdsgre", "123456");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme