package com.sun.provider.com.sun.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.api.bean.UserAddress;
import com.sun.api.bean.UserInfo;
import com.sun.api.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Service//暴露服务com.alibaba.dubbo.config.annotation.Service
@Component
public class UserServiceImpl implements UserService {

    @HystrixCommand
    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        System.out.println("UserServiceImpl........");
        UserAddress address1 = new UserAddress(1, "xxxxxxx", "1", "张三", "010-56253825", "Y");
        UserAddress address2 = new UserAddress(2, "yyyyyyy", "1", "李四", "010-56253825", "N");
        return Arrays.asList(address1,address2);
    }


    @HystrixCommand
    @Override
    public UserInfo getUserInfo(String userId) {
        System.out.println("UserServiceImpl.......");
        return UserInfo.builder().name("张三").tel("123456789").build();
    }

}
