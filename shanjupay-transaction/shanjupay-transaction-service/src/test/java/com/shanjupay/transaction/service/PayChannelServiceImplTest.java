package com.shanjupay.transaction.service;

import com.shanjupay.transaction.api.dto.PayChannelDTO;
import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import com.shanjupay.transaction.mapper.AppPlatformChannelMapper;
import com.shanjupay.transaction.mapper.PlatformChannelMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class PayChannelServiceImplTest {
    @Mock
    PlatformChannelMapper platformChannelMapper;
    @Mock
    AppPlatformChannelMapper appPlatformChannelMapper;
    @Mock
    Logger log;
    @InjectMocks
    PayChannelServiceImpl payChannelServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueryPlatformChannels() throws Exception {
        List<PlatformChannelDTO> result = payChannelServiceImpl.queryPlatformChannels();
        Assert.assertEquals(Arrays.<PlatformChannelDTO>asList(new PlatformChannelDTO()), result);
    }

    @Test
    public void testBindPlatformChannelForApp() throws Exception {
        payChannelServiceImpl.bindPlatformChannelForApp("appId", "platformChannelCodes");
    }

    @Test
    public void testQueryAppBindPlatformChannel() throws Exception {
        int result = payChannelServiceImpl.queryAppBindPlatformChannel("appId", "platformChannelCodes");
        Assert.assertEquals(0, result);
    }

    @Test
    public void testQueryPayChannelByPlatformChannel() throws Exception {
        when(platformChannelMapper.selectPayChannelByPlatformChannel(anyString())).thenReturn(Arrays.<PayChannelDTO>asList(new PayChannelDTO()));

        List<PayChannelDTO> result = payChannelServiceImpl.queryPayChannelByPlatformChannel("platformChannelCodes");
        Assert.assertEquals(Arrays.<PayChannelDTO>asList(new PayChannelDTO()), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme