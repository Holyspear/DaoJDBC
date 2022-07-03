package application;

import java.util.Date;
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
		
		System.out.println("\n===== TEST 4: Seller Insert =====");
		Seller newSeller = new Seller(null, "Greg Capulo", "greg.capulo@gmail.com", new Date(), 4000.00, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());

	}
}
