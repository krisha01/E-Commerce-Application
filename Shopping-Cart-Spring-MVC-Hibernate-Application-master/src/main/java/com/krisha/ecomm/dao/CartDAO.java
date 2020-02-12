package com.krisha.ecomm.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.krisha.ecomm.exception.CartException;
import com.krisha.ecomm.exception.CategoryException;
import com.krisha.ecomm.pojo.Adverts;
import com.krisha.ecomm.pojo.Cart;
import com.krisha.ecomm.pojo.Category;
import com.krisha.ecomm.pojo.User;

public class CartDAO extends DAO {
	
	public CartDAO(){
	
	}

	public Cart insert(Cart cart) throws CartException {
		try{
			begin();            
			getSession().save(cart);     
            commit();
            return cart;
		} catch (HibernateException ex){
			rollback();
            throw new CartException("Unable to save cart", ex);
		}
	}
	
	public void update(Cart cart) throws CategoryException {
        try {
            begin();
            getSession().update(cart);
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new CategoryException("Unable to save cart", ex);
        }
    }
	
	public User update(User user) throws CategoryException {
        try {
            begin();
            getSession().update(user);
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new CategoryException("Unable to save user", ex);
        }
        return user;
    }
	
	public List<Cart> list(){
		begin();
		Query q = getSession().createQuery("from Cart");
		List<Cart> cart1 = q.list();
		commit();
		return cart1;
	}
	
	public Cart updateCart(Cart cart) throws CategoryException {
        try {
            begin();
            getSession().update(cart);
            commit();
            return cart;
        } catch (HibernateException ex) {
            rollback();
            throw new CategoryException("Unable to save cart", ex);
        }
    }
	

}
