package com.krisha.ecomm.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.AbstractController;

import com.krisha.ecomm.dao.AdvertDAO;
import com.krisha.ecomm.dao.CartDAO;
import com.krisha.ecomm.dao.CategoryDAO;
import com.krisha.ecomm.dao.DAO;
import com.krisha.ecomm.dao.UserDAO;
import com.krisha.ecomm.pojo.Cart;
import com.krisha.ecomm.pojo.GeneratePDF;
import com.krisha.ecomm.pojo.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/cart/*")
public class CheckController extends GeneratePDF{
	
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
	@Qualifier("cartDao")
	CartDAO cartDao;
	
	@Autowired
	ServletContext servletContext;
	
	
	@RequestMapping(value = "/cart/checkout", method = RequestMethod.POST)
	public ModelAndView showPdfReport(@ModelAttribute("cart") Cart cart, ModelMap model,BindingResult result, HttpServletRequest request) throws Exception
	{
		List<Cart> view=cartDao.list();
		model.addAttribute("cartitems", view);
		View views = new GeneratePDF();
		return new ModelAndView(views);
	}
	
}
