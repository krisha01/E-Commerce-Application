import java.util.HashSet;
import java.util.Set;

import com.krisha.ecomm.dao.AdvertDAO;
import com.krisha.ecomm.dao.CartDAO;
import com.krisha.ecomm.dao.CategoryDAO;
import com.krisha.ecomm.dao.UserDAO;
import com.krisha.ecomm.exception.AdvertException;
import com.krisha.ecomm.exception.CategoryException;
import com.krisha.ecomm.exception.UserException;
import com.krisha.ecomm.pojo.Adverts;
import com.krisha.ecomm.pojo.Cart;
import com.krisha.ecomm.pojo.Category;
import com.krisha.ecomm.pojo.User;

public class TestMain {

	public static void main(String[] args) throws CategoryException, AdvertException, UserException {
		
		UserDAO ud = new UserDAO();
		User u = ud.get(0);
		
		Category cat1 = new Category();		
		cat1.setTitle("Category");
		
		Cart cart =new Cart();
		cart.setTotalprice("TotalCost");
		
		Set<Adverts> advs = new HashSet<Adverts>();
		Adverts adv = new Adverts("advertisement1", "advertisement1", u , cat1, cart);
		
		AdvertDAO advertDao = new AdvertDAO();
		advertDao.create(adv);
		
		advs.add(adv);
		cat1.setAdverts(advs);
		
		CategoryDAO categoryDao = new CategoryDAO();
		categoryDao.update(cat1);
		
		CartDAO cartDao = new CartDAO();
		cartDao.update(cart);
		
		System.out.println("Testing Phase");

	}

}
