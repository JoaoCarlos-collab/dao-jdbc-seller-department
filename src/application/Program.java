package application;
import databaseconnection.exceptions.DateUtils;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main (String[] args){

        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findId(4);
        //System.out.println(seller);
        
        System.out.println("-----------------------------------------------------");
        Department department = new Department(1, null);
        List<Seller> listFindId = sellerDao.findByDepartment(department);
        //listFindId.forEach(System.out::println);
        
        System.out.println("-----------------------------------------------------");
        List<Seller> listFindAll = sellerDao.findAll();
        //listFindAll.forEach(System.out::println);
        
        System.out.println("-----------------------------------------------------");
        Department departmentInsert = new Department(1, null);
        Seller insertSeller = new Seller("Maria Gorda", "MariaGorda@gmail.com", new Date(), 5000.00, departmentInsert);
        sellerDao.insert(insertSeller);
        //System.out.println(insertSeller);
        
        System.out.println("-----------------------------------------------------");
        Seller sellerId = sellerDao.findId(1);
        sellerId.setName("JBPassa");
        sellerId.setEmail("JB1999@gmail.com");
        sellerId.setBaseSalary(5000.00);
        sellerDao.delete(sellerId);
        System.out.println(sellerId);
    }
}
