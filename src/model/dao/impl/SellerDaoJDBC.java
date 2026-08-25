package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void update(Seller seller) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "Where seller.id = ?", ResultSet.TYPE_FORWARD_ONLY);
    
            st.setInt(1, id);
            rs = st.executeQuery();
    
            if(rs.next()){
                Department department = new Department(
                    rs.getInt("DepartmentId"), 
                    rs.getString("DepName")
                );
    
                return new Seller(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Email"),
                    LocalDate.parse(rs.getDate("BirthDate").toString()),
                    rs.getDouble("BaseSalary"),
                    department
                );
            };
    
            throw new DbException("Seller not found!");
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeConnection();
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
