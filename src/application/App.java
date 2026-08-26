package application;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;

public class App {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        // System.out.println("===== Test 1: seller findById =====");
        // Seller seller = sellerDao.findById(1);
        // System.out.println(seller);
        // System.out.println();
        
        // System.out.println("===== Test 2: seller findByDepartment =====");
        // List<Seller> sellerList = sellerDao.findByDepartment(new Department(2, null));
        // for(Seller currentSeller : sellerList){
        //     System.out.println(currentSeller);
        // }
        // System.out.println();
        
        // System.out.println("===== Test 3: seller findAll =====");
        // sellerList = sellerDao.findAll();
        // for(Seller currentSeller : sellerList){
        //     System.out.println(currentSeller);
        // }
        // System.out.println();
        
        // System.out.println("===== Test 4: seller insert =====");
        // Seller newSeller = new Seller(null, "Carlos Cavalcante", "carlos@email.com", LocalDate.now(), 4000.00, new Department(4, null));
        // sellerDao.insert(newSeller);
        // System.out.println("Inserted! New id = " + newSeller.getId());
        // System.out.println();

        // sellerList = sellerDao.findAll();
        // for(Seller currentSeller : sellerList){
        //     System.out.println(currentSeller);
        // }
        // System.out.println();
        
        // System.out.println("===== Test 5: seller update =====");
        // newSeller.setName("Antonio Nunes");
        // newSeller.setEmail("antonio@email.com");
        // sellerDao.update(newSeller);
        // System.out.println("Update completed!");

        // sellerList = sellerDao.findAll();
        // for(Seller currentSeller : sellerList){
        //     System.out.println(currentSeller);
        // }
        // System.out.println();
        
        // System.out.println("===== Test 6: seller delete =====");
        // sellerDao.deleteById(newSeller.getId());
        // sellerList = sellerDao.findAll();
        // for(Seller currentSeller : sellerList){
        //     System.out.println(currentSeller);
        // }
        // System.out.println();
            
        System.out.println("===== Test 7: department findById =====");
        Department department = departmentDao.findById(1);
        System.out.println(department);
        System.out.println();
            
        System.out.println("===== Test 8: department findAll =====");
        List<Department> departmentList = departmentDao.findAll();
        for(Department dep : departmentList){
            System.out.println(dep);
        }
        System.out.println();
    }
}
