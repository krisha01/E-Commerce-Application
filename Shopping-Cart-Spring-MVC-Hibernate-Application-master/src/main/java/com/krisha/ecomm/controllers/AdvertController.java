package com.krisha.ecomm.controllers;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.krisha.ecomm.dao.AdvertDAO;
import com.krisha.ecomm.dao.CategoryDAO;
import com.krisha.ecomm.dao.UserDAO;
import com.krisha.ecomm.exception.AdvertException;
import com.krisha.ecomm.pojo.Adverts;
import com.krisha.ecomm.pojo.Cart;
import com.krisha.ecomm.pojo.Category;
import com.krisha.ecomm.pojo.User;

@Controller
@RequestMapping("/advert/*")
public class AdvertController {
		
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

		@RequestMapping(value = "/advert/add", method = RequestMethod.POST)
		public ModelAndView addCategory(@ModelAttribute("advert") Adverts adverts, BindingResult result) throws Exception {

			try {			
				
				User u = userDao.get(adverts.getPostedBy());
				adverts.setUser(u);
				adverts = advertDao.create(adverts);
				
	            
	            for(Category c: adverts.getCategories()){
	            	c = categoryDao.get(c.getTitle());
	            	c.getAdverts().add(adverts);
	            	categoryDao.update(c);
				
	            }
	            if (adverts.getFilename().trim() != "" || adverts.getFilename() != null) {
					File directory;
					String check = File.separator;
					String path = null;
					if (check.equalsIgnoreCase("\\")) {
						path = servletContext.getRealPath("").replace("build\\", ""); 
					}

					if (check.equalsIgnoreCase("/")) {
						path = servletContext.getRealPath("").replace("build/", "");
						path += "/"; 
					}
					directory = new File(path + "\\" + adverts.getFilename());
					boolean temp = directory.exists();
					if (!temp) {
						temp = directory.mkdir();
					}
					if (temp) {
						CommonsMultipartFile photoInMemory = adverts.getPhoto();

						String fileName = photoInMemory.getOriginalFilename();

						File localFile = new File(directory.getPath(), fileName);

						photoInMemory.transferTo(localFile);
						adverts.setFilename(localFile.getPath());
						System.out.println("File Location : " + localFile.getPath());
						System.out.print("Registering new User");
						Adverts a = advertDao.create(adverts);

					} else {
						System.out.println("Directory Creation Failed....");
					}
					
				}
				
	            return new ModelAndView("advert-success", "advert", adverts);
	            
			} catch (AdvertException e) {
				System.out.println(e.getMessage());
				return new ModelAndView("error", "errorMessage", "Login Error");
			}
			
			
		}
		
		@RequestMapping(value = "/advert/list", method = RequestMethod.GET)
		public ModelAndView addCategory(HttpServletRequest request) throws Exception {

			ModelAndView mv = new ModelAndView("advert-list");
			List<Adverts> adverts = advertDao.list();
			mv.addObject("adverts", adverts);
	        mv.addObject("cart", new Cart());
	        return mv;
			
		}
		
		@RequestMapping(value = "/advert/sellerlist", method = RequestMethod.GET)
		public ModelAndView addCategories(HttpServletRequest request) throws Exception {

			try {			
				
				List<Adverts> adverts = advertDao.list();
				return new ModelAndView("seller-advert-list", "adverts", adverts);
				
			} catch (AdvertException e) {
				System.out.println(e.getMessage());
				return new ModelAndView("error", "errorMessage", "Login Error");
			}
			
			
		}

		@RequestMapping(value="/advert/add", method = RequestMethod.GET)
		public ModelAndView initializeForm(HttpServletRequest request) throws Exception {		
			ModelAndView mv = new ModelAndView();
			mv.addObject("categories", categoryDao.list());			
			mv.addObject("advert", new Adverts());
			mv.setViewName("advert-form");
			return mv;
		}


}