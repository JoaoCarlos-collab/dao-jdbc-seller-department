package model.dao.impl;

import databaseconnection.ConfigurationDatabase;
import databaseconnection.exceptions.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

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

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
