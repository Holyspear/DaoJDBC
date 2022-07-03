package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Department departmentObj = new Department(1, "Books");

		System.out.println(departmentObj);

		Seller sellerObj = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.00, departmentObj);
		
		System.out.println(sellerObj);

		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		Seller sellerFinded = sellerDao.findById(4);
		
		System.out.println(sellerFinded);

	}
}
