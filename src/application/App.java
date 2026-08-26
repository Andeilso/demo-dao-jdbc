package application;

import java.time.LocalDate;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class App {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("===== Test 1: seller findById =====");
        Seller seller = sellerDao.findById(1);
        System.out.println(seller);
        System.out.println();
        
        System.out.println("===== Test 2: seller findByDepartment =====");
        List<Seller> sellerList = sellerDao.findByDepartment(new Department(2, null));
        for(Seller currentSeller : sellerList){
            System.out.println(currentSeller);
        }
        System.out.println();
        
        System.out.println("===== Test 3: seller findAll =====");
        sellerList = sellerDao.findAll();
        for(Seller currentSeller : sellerList){
            System.out.println(currentSeller);
        }
        System.out.println();
        
        System.out.println("===== Test 4: seller insert =====");
        Seller newSeller = new Seller(null, "Carlos Cavalcante", "carlos@email.com", LocalDate.now(), 4000.00, new Department(4, null));
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());
        System.out.println();
        
        System.out.println("===== Test 4: seller insert =====");
        sellerDao.deleteById(newSeller.getId());
        sellerList = sellerDao.findAll();
        for(Seller currentSeller : sellerList){
            System.out.println(currentSeller);
        }
        System.out.println();
    }
}
