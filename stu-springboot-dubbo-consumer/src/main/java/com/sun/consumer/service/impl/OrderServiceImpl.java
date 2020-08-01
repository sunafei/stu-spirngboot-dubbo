package com.sun.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.api.bean.UserAddress;
import com.sun.api.bean.UserInfo;
import com.sun.api.service.OrderService;
import com.sun.api.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    //@Autowired
    @Reference(loadbalance = "random", timeout = 1000) //dubbo直连
    UserService userService;

    @HystrixCommand(fallbackMethod = "hello")
    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        List<UserAddress> addressList = userService.getUserAddressList(userId);
        return addressList;
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        return userService.getUserInfo(userId);
    }


    public List<UserAddress> hello(String userId) {
        return Arrays.asList(new UserAddress(10, "测试地址", "1", "测试", "测试", "Y"));
    }


}