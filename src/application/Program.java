package application;
import databaseconnection.exceptions.DateUtils;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main (String[] args){

        Department obj = new Department(1, "Computer");
        Department obj1 = new Department(2,"Books");

        Date dateBirth = DateUtils.treatmentDate("24/06/2004");
        Seller seller = new Seller(1,"João","jo902198@gmail.com",dateBirth, 2000.00,obj);
        System.out.println(seller);
        System.out.println();
        Date dateBirth1 = DateUtils.treatmentDate("24/10/2000");
        Seller seller1 = new Seller(2,"João Gomes","joaoGomes90@gmail.com", dateBirth1, 2500.00,obj1);
        System.out.println(seller1);
        System.out.println();
        Date dateBirth2 = DateUtils.treatmentDate("24/06/1990");
        Seller seller2 = new Seller(3,"João Carlos","joaoCarlos198@gmail.com", dateBirth2, 2000.00,obj1);
        System.out.println(seller2);

        SellerDao sellerDao = DaoFactory.createSellerDao();
    }
}
