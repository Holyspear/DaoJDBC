package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("===== TEST 1: Seller FindById =====");		
		Seller sellerFinded = sellerDao.findById(3);		
		System.out.println(sellerFinded);
		
		System.out.println("\n===== TEST 2: Seller FindByDepartment =====");
		Department department = new Department(2, null);
		List<Seller> listSeller = sellerDao.findByDepartment(department);
		for(Seller sellers : listSeller) {
			System.out.println(sellers);
		}
		
		System.out.println("\n===== TEST 3: Seller FindAll =====");
		listSeller = sellerDao.findAll();
		for(Seller sellers : listSeller) {
			System.out.println(sellers);
		}

	}
}
