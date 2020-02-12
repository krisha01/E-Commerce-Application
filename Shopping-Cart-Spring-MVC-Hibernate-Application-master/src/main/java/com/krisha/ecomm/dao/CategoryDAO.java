package com.krisha.ecomm.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.krisha.ecomm.exception.CategoryException;
import com.krisha.ecomm.pojo.Category;

public class CategoryDAO extends DAO {

    public Category get(String title) throws CategoryException {
        try {
            begin();
            Query q=getSession().createQuery("from Category where title= :title");
            q.setString("title",title);
            Category cat =(Category)q.uniqueResult();
            commit();
            return cat;
        } catch (HibernateException ex) {
            rollback();
            throw new CategoryException("Could not obtain the named category " + title + " " + ex.getMessage());
        }
    }

    public List<Category> list() throws CategoryException {
        try {
            begin();
            Query q = getSession().createQuery("from Category");
            List<Category> list = q.list();
            commit();
            return list;
        } catch (HibernateException ex) {
            rollback();
            throw new CategoryException("Unable to list the categories", ex);
        }
    }

    public Category create(String title) throws CategoryException {
        try {
            begin();
            Category cat = new Category(title);
            getSession().save(cat);
            commit();
            return cat;
        } catch (HibernateException ex) {
            rollback();
            throw new CategoryException("Exception occured while creating category: " + ex.getMessage());
        }
    }

    public void update(Category cat) throws CategoryException {
        try {
            begin();
            getSession().update(cat);
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new CategoryException("Unable to save category", ex);
        }
    }

    public void delete(Category cat) throws CategoryException {
        try {
            begin();
            getSession().delete(cat);
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new CategoryException("Unable to delete the category", ex);
        }
    }
}