package com.sun.api.service;

import com.sun.api.bean.UserAddress;
import com.sun.api.bean.UserInfo;

import java.util.List;

public interface OrderService {
	
	List<UserAddress> getUserAddressList(String userId);

	UserInfo getUserInfo(String userId);
}
