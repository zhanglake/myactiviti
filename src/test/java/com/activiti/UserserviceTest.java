package com.activiti;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.process.entity.User;
import com.process.service.UserService;

public class UserserviceTest extends BaseTestCase {

	@Autowired
	private UserService userService;
	Logger logger = Logger.getLogger(UserserviceTest.class);

	@Test
	public void selectUserByIdTest() {
		User user = userService.selectUserById(10);
		logger.debug("²éÕÒ½á¹û" + user);
	}
	
}