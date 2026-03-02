package model.dao;

import databaseconnection.ConfigurationDatabase;
import model.dao.impl.SellerDaoJDBC;

import java.sql.Connection;

public class DaoFactory {

    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(ConfigurationDatabase.getConnection());
    }
}
