package com.krisha.ecomm.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.krisha.ecomm.dao.UserDAO;
import com.krisha.ecomm.exception.UserException;
import com.krisha.ecomm.pojo.User;
import com.krisha.ecomm.validator.UserValidator;

@Controller
@RequestMapping("/user/*")
public class UserController {

	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("userValidator")
	UserValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	protected String goToUserHome(HttpServletRequest request) throws Exception {
		return "user-home";
	}
	
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	protected String loginUser(HttpServletRequest request) throws Exception {

		HttpSession session = (HttpSession) request.getSession();
		
		try {

			System.out.print("loginUser");

			User user = userDao.get(request.getParameter("username"), request.getParameter("password"));
			
			if(user == null){
				System.out.println("Login Attempt Failed.");
				System.out.println("Username/Password is incorrect");
				session.setAttribute("errorMessage", "UserName/Password is incorrect");
				return "error";
			}
			
			else if(user.getUsertype().equals("Buyer")){
				session.setAttribute("user", user);
				return "buyer-home";
			}
			
			else if(!(user.getUsertype().equals("Buyer"))&&!(user.getUsertype().equals("Seller"))){
				session.setAttribute("errorMessage", "UserName/Password is incorrect");
				return "error";
			}
			
			else{
				session.setAttribute("user", user);
				return "user-home";
			}
			

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			session.setAttribute("errorMessage", "Some error occured while logging in");
			return "error";
		}

	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	protected ModelAndView registerUser() throws Exception {
		System.out.println("Registering the user");

		ModelAndView mv = new ModelAndView("register-user");
		Map<String,String> typeofuser = new LinkedHashMap<String,String>();
		typeofuser.put("Buyer", "Buyer");
		typeofuser.put("Seller", "Seller");
		
		mv.addObject("usertype", typeofuser);
        mv.addObject("user", new User());
        return mv;
	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request,  @ModelAttribute("user") User user, BindingResult bresult) throws Exception {

		validator.validate(user, bresult);

		if (bresult.hasErrors()) {
			ModelAndView mv = new ModelAndView("register-user");
			Map<String,String> typeofuser = new LinkedHashMap<String,String>();
			typeofuser.put("Buyer", "Buyer");
			typeofuser.put("Seller", "Seller");
			
			mv.addObject("usertype", typeofuser);
	        mv.addObject("user", user);
	        return mv;
		}

		try {

			System.out.println("Registering a new User");

			User user1 = userDao.register(user);
			
			request.getSession().setAttribute("user", user1);
			
			return new ModelAndView("account-success", "user", user1);

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "Some Error occured while Logging in");
		}
	}

		protected Map referenceData(HttpServletRequest request) throws Exception {

		Map data = new HashMap();

		Map<String,String> typeofuser = new LinkedHashMap<String,String>();
		typeofuser.put("Buyer", "Buyer");
		typeofuser.put("Seller", "Seller");
		data.put("usertype", typeofuser);
		
		return data;
	}
}
