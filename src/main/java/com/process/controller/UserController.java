package com.process.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.process.entity.User;
import com.process.service.UserService;

@Controller
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/user")
	public ModelAndView getIndex() {
		ModelAndView mav = new ModelAndView("index");
		User user = userService.selectUserById(1);
		mav.addObject("user", user);
		return mav;
	}

}
