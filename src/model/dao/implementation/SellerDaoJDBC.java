package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("INSERT INTO seller " 
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, departmentObj.getName());
			ps.setString(2, departmentObj.getEmail());
			ps.setDate(3, new java.sql.Date(departmentObj.getBirthDate().getTime()));
			ps.setDouble(4, departmentObj.getBaseSalary());
			ps.setInt(5, departmentObj.getDepartment().getId());

			int rowsAffecteds = ps.executeUpdate();

			if (rowsAffecteds > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				
				if (rs.next()) {
					int id = rs.getInt(1);
					departmentObj.setId(id);
				}
				
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error: no rows affected.");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);

		}
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
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department departmentObj = initiateDepartment(rs);
				Seller sellerObj = instantiateSeller(rs, departmentObj);

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

	private Seller instantiateSeller(ResultSet rs, Department departmentObj) throws SQLException {
		Seller sellerObj = new Seller();
		sellerObj.setId(rs.getInt("Id"));
		sellerObj.setName(rs.getString("Name"));
		sellerObj.setEmail(rs.getString("Email"));
		sellerObj.setBaseSalary(rs.getDouble("BaseSalary"));
		sellerObj.setBirthDate(rs.getDate("BirthDate"));
		sellerObj.setDepartment(departmentObj);
		return sellerObj;
	}

	private Department initiateDepartment(ResultSet rs) throws SQLException {
		Department departmentObj = new Department();
		departmentObj.setId(rs.getInt("DepartmentId"));
		departmentObj.setName(rs.getString("DepName"));
		return departmentObj;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "ORDER BY seller.Name");

			rs = st.executeQuery();
			List<Seller> listSeller = new ArrayList<>();
			Map<Integer, Department> mapDepartment = new HashMap<>();

			while (rs.next()) {

				Department newDepartment = mapDepartment.get(rs.getInt("DepartmentId"));

				if (newDepartment == null) {
					newDepartment = initiateDepartment(rs);
					mapDepartment.put(rs.getInt("DepartmentId"), newDepartment);
				}

				Seller sellerObj = instantiateSeller(rs, newDepartment);
				listSeller.add(sellerObj);
			}
			return listSeller;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department departmentObj) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department " + "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? " + "ORDER BY seller.Name");

			st.setInt(1, departmentObj.getId());
			rs = st.executeQuery();
			List<Seller> listSeller = new ArrayList<>();
			Map<Integer, Department> mapDepartment = new HashMap<>();

			while (rs.next()) {

				Department newDepartment = mapDepartment.get(rs.getInt("DepartmentId"));

				if (newDepartment == null) {
					newDepartment = initiateDepartment(rs);
					mapDepartment.put(rs.getInt("DepartmentId"), newDepartment);
				}

				Seller sellerObj = instantiateSeller(rs, newDepartment);
				listSeller.add(sellerObj);
			}
			return listSeller;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
