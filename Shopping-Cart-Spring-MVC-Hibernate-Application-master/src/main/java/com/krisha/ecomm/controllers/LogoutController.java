package com.krisha.ecomm.controllers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.krisha.ecomm.dao.AdvertDAO;
import com.krisha.ecomm.dao.CategoryDAO;
import com.krisha.ecomm.dao.UserDAO;


@Controller
@RequestMapping("/advert/*")
public class LogoutController {

	@Autowired
	@Qualifier("advertDao")
	AdvertDAO advertDao;
	
	@Autowired
	@Qualifier("categoryDao")
	CategoryDAO categoryDao;
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(value = "/advert/logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "logout";
    }
}
