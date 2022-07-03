package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller departmentObj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller departmentObj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE seller.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Department departmentObj = new Department();
				departmentObj.setId(rs.getInt("DepartmentId"));
				departmentObj.setName(rs.getString("DepName"));
				Seller sellerObj = new Seller();
				sellerObj.setId(rs.getInt("Id"));
				sellerObj.setName(rs.getString("Name"));
				sellerObj.setEmail(rs.getString("Email"));
				sellerObj.setBaseSalary(rs.getDouble("BaseSalary"));
				sellerObj.setBirthDate(rs.getDate("BirthDate"));
				sellerObj.setDepartment(departmentObj);
				
				return sellerObj;
				
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
