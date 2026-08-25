package application;

import java.time.LocalDate;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class App {
    
    public static void main(String[] args) {
        Department obj = new Department(1, "TI");
        Seller seller = new Seller(1, "Bob", "Bob@email.com", LocalDate.now(), 3000.00, obj);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        
        System.out.println(obj);
        System.out.println(seller);
    }
}
