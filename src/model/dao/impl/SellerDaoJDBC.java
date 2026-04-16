package model.dao.impl;

import databaseconnection.ConfigurationDatabase;
import databaseconnection.exceptions.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
    	PreparedStatement preparedStatement = null;
    	
    	try {
    		
    		preparedStatement = connection.prepareStatement(
    				"INSERT INTO seller "
    				+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
    				+ "VALUES "
    				+ "(?, ?, ?, ?, ?)",
    				new String[] {"Id"}
    				);
    		preparedStatement.setString(1, seller.getName());
    		preparedStatement.setString(2, seller.getEmail());
    		preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
    		preparedStatement.setDouble(4, seller.getBaseSalary());
    		preparedStatement.setInt(5, seller.getDepartment().getId());
    		
    		int insertedLines = preparedStatement.executeUpdate();
    			if (insertedLines > 0) {
	    			ResultSet resultSet = preparedStatement.getGeneratedKeys();
	    			if(resultSet.next()) {
	    				int id = resultSet.getInt(1);
	    				seller.setId(id);
    			}
	    			ConfigurationDatabase.closeResultSet(resultSet);
    		}
    		else {
    			throw new DbException("It was not possible to insert the row.");
    		}
			
		} catch (SQLException e) {
			throw new DbException("The insert command could not be executed.\n" + e.getMessage());

		}finally {
			ConfigurationDatabase.closeStatement(preparedStatement);
		}
    }

    @Override
    public void update(Seller seller) {
    	PreparedStatement preparedStatement = null;
    	
    	try {
    		
    		preparedStatement = connection.prepareStatement(
    				"UPDATE seller "
    				+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
    				+ "WHERE Id = ?"
    				);
    		preparedStatement.setString(1, seller.getName());
    		preparedStatement.setString(2, seller.getEmail());
    		preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
    		preparedStatement.setDouble(4, seller.getBaseSalary());
    		preparedStatement.setInt(5, seller.getDepartment().getId());
    		preparedStatement.setInt(6, seller.getId());
    		
    		preparedStatement.executeUpdate();
    			
			
		} catch (SQLException e) {
			throw new DbException("The insert command could not be executed.\n" + e.getMessage());

		}finally {
			ConfigurationDatabase.closeStatement(preparedStatement);
		}

    }

    @Override
    public void delete(Seller seller) {

    }

    @Override
    public Seller findId(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
            		"SELECT seller.*, department.NameDEP AS DepName "
            		+ "FROM seller "
            		+ "INNER JOIN department " 
            		+ "ON seller.DepartmentId = department.IDDEP "
            		+ "WHERE seller.Id = ?"
            );
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Department department = instantiateDepartment(resultSet);
                Seller seller = instantiateSeller(resultSet, department);
                return seller;
            }

        } catch (SQLException e) {
            throw new DbException("The findId command could not be executed.\n" + e.getMessage());

        }finally {
            ConfigurationDatabase.closeStatement(preparedStatement);
            ConfigurationDatabase.closeResultSet(resultSet);
        }
        return null;
    }
    

    @Override
    public List<Seller> findAll() {
        
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	
    	try {
    		preparedStatement = connection.prepareStatement(
				"SELECT seller.*, department.NameDEP AS DepName "
	    		+ "FROM seller "
	    		+ "INNER JOIN department " 
	    		+ "ON seller.DepartmentId = department.IDDEP "
	    		+ "ORDER BY Name"
				);
    		
    		resultSet = preparedStatement.executeQuery();
    		
    		List<Seller> listSeller = new ArrayList<Seller>();
    		Map<Integer, Department> map = new HashMap<>();
    		
			while (resultSet.next()) {
				Department depar = map.get(resultSet.getInt("DepartmentId"));
				if(depar == null) {
					Department departmentt = instantiateDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), departmentt);
				}
				
				Seller seller = instantiateSeller(resultSet, depar);
				listSeller.add(seller);
			}
			return listSeller;
			
		} catch (Exception e) {
			 throw new DbException("The findAll command could not be executed.\n" + e.getMessage());
			 
		}finally {
			ConfigurationDatabase.closeStatement(preparedStatement);
			ConfigurationDatabase.closeResultSet(resultSet);
		}
    }

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
            		"SELECT seller.*, department.NameDEP AS DepName "
            		+ "FROM seller "
            		+ "INNER JOIN department " 
            		+ "ON seller.DepartmentId = department.IDDEP "
            		+ "WHERE DepartmentId = ? "
            		+ "ORDER BY Name"
            );
            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();
            
            List <Seller> listSeller = new ArrayList<Seller>();
            Map <Integer, Department> map = new HashMap<>();
            
            while (resultSet.next()){
            	
            	Department depar = map.get(resultSet.getInt("DepartmentId"));
            	if (depar == null) {
            		Department departmentt = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), departmentt);
            	}   
            	Seller seller = instantiateSeller(resultSet, depar);
                listSeller.add(seller);
            }
            
            return listSeller;

        } catch (SQLException e) {
            throw new DbException("The findId command could not be executed.\n" + e.getMessage());

        }finally {
            ConfigurationDatabase.closeStatement(preparedStatement);
            ConfigurationDatabase.closeResultSet(resultSet);
        }
	}
	
	public Department instantiateDepartment(ResultSet resultSet) throws SQLException {
    	Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }
    
    public Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
    	Seller seller = new Seller();
        seller.setId(resultSet.getInt("ID"));
        seller.setName(resultSet.getString("NAME"));
        seller.setEmail(resultSet.getString("EMAIL"));
        seller.setBirthDate(resultSet.getDate("BIRTHDATE"));
        seller.setBaseSalary(resultSet.getDouble("BASESALARY"));
        seller.setDepartment(department);
        return seller;
    }
}
