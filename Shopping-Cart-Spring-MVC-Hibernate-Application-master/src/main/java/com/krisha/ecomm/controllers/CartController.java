package com.krisha.ecomm.controllers;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.krisha.ecomm.dao.AdvertDAO;
import com.krisha.ecomm.dao.CartDAO;
import com.krisha.ecomm.dao.CategoryDAO;
import com.krisha.ecomm.dao.DAO;
import com.krisha.ecomm.dao.UserDAO;
import com.krisha.ecomm.pojo.Adverts;
import com.krisha.ecomm.pojo.Cart;
import com.krisha.ecomm.pojo.User;

@Controller
@RequestMapping("/cart/*")
public class CartController extends DAO{

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
	
	
	
	@RequestMapping(value = "/cart/insert", method = RequestMethod.POST)
	public ModelAndView addCategory(@ModelAttribute("cart") Cart cart, BindingResult result, HttpServletRequest request) throws Exception {
		HttpSession session = (HttpSession) request.getSession();
		User user = (User)session.getAttribute("user");
		cart.setUser(user);
		user.setCart(cart);
		Cart cartdeets=null;
		 List<Cart> carts=cartDao.list();
		 int j = 0;
		 Cart cartitems = null;
		 for(Cart x: carts){
			 if(user.getPersonID()==x.getId()){
				 cartitems = cartDao.updateCart(x);
				 j = 1;
				 return new ModelAndView("user-cart","c",cartitems);
			 }
		 }
	 if (j==0){
		  cartdeets = cartDao.insert(cart);
	 }
	
		return new ModelAndView("user-cart","c",cartdeets);
	}
}
