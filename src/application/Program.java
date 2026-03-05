package application;
import databaseconnection.exceptions.DateUtils;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main (String[] args){

        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findId(4);
        System.out.println(seller);
        System.err.println("-----------------------------------------------------");
        Department department = new Department(1, null);
        List<Seller> listFindId = sellerDao.findByDepartment(department);
        listFindId.forEach(System.out::println);
        System.out.println("-----------------------------------------------------");
        
        List<Seller> listFindAll = sellerDao.findAll();
        listFindAll.forEach(System.out::println);
    }
}
