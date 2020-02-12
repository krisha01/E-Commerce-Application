package com.krisha.ecomm.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.krisha.ecomm.exception.AdvertException;
import com.krisha.ecomm.pojo.Adverts;
import com.krisha.ecomm.pojo.Category;

public class AdvertDAO extends DAO {

    public Adverts create(Adverts adverts) throws AdvertException {
        try {
            begin();            
            getSession().save(adverts);     
            commit();
            return adverts;
        } catch (HibernateException e) {
            rollback();
            throw new AdvertException("Exception Occured while creating Adverts: " + e.getMessage());
        }
    }

    public void delete(Adverts adverts)
            throws AdvertException {
        try {
            begin();
            getSession().delete(adverts);
            commit();
        } catch (HibernateException exception) {
            rollback();
            throw new AdvertException("Unable to delete Adverts", exception);
        }
    }
    
    public List<Adverts> list() throws AdvertException{
    	
    	try {
    		System.out.println("Inside list method of AdvertDAO");
            begin();
            Query q = getSession().createQuery("from Adverts");
            List<Adverts> adverts = q.list();
            commit();
            return adverts;
        } catch (HibernateException e) {
            rollback();
            throw new AdvertException("Unable to delete Advertise", e);
        }
    	
    }
}