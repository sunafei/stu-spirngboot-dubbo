package com.sun.api.service;

import com.sun.api.bean.UserAddress;
import com.sun.api.bean.UserInfo;

import java.util.List;

public interface UserService {
	
	/**
	 * 按照用户id返回所有的收货地址
	 * @param userId
	 * @return
	 */
	List<UserAddress> getUserAddressList(String userId);

	/**
	 * 按照用户id返回所有的收货地址
	 * @param userId
	 * @return
	 */
	UserInfo getUserInfo(String userId);

}
