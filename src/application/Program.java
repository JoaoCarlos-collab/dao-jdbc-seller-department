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
        List<Seller> list = sellerDao.findByDepartment(department);
        list.forEach(System.out::println);
    }
}
