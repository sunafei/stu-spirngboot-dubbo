package com.sun.consumer.controller;

import com.sun.api.bean.UserAddress;
import com.sun.api.bean.UserInfo;
import com.sun.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @ResponseBody
    @RequestMapping("/getUserAddress")
    public List<UserAddress> getUserAddressList(@RequestParam("uid")String userId) {
        return orderService.getUserAddressList(userId);
    }

    @ResponseBody
    @RequestMapping("/getUserInfo")
    public UserInfo getUserInfo(@RequestParam("uid")String userId) {
        return orderService.getUserInfo(userId);
    }
}
