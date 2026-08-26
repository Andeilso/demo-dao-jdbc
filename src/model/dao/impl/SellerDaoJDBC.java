package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO seller "
                + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                + "VALUES "
                + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, Date.valueOf(seller.getBirthDate()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "UPDATE seller "
                + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                + "Where Id =?"
            );

            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, Date.valueOf(seller.getBirthDate()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            st.setInt(6, seller.getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected == 0){
                throw new DbException("Update erro: Seller not Found!");
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
         PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                "DELETE FROM seller "
                + "WHERE Id = ?"
            );
            
            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if(rowsAffected == 0){
                throw new DbException("Delete error: Seller not Found!");
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
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
                + "Where seller.id = ?");
    
            st.setInt(1, id);
            rs = st.executeQuery();
    
            if(rs.next()){
                Department department = instantiateDepartment(rs);
                return instantiateSeller(rs, department);
            };
    
            throw new DbException("Seller not found!");
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "ORDER BY Name"
            );

            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();

            Map<Integer, Department> departmentMap = new HashMap<>();

            while(rs.next()){
                Department dep = departmentMap.get(rs.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    departmentMap.put(rs.getInt("DepartmentId"), dep);
                }

                sellerList.add(instantiateSeller(rs, dep));
            }

            return sellerList;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "WHERE DepartmentId = ? "
                + "ORDER BY Name"
            );

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();

            Map<Integer, Department> departmentMap = new HashMap<>();

            while(rs.next()){
                Department dep = departmentMap.get(rs.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    departmentMap.put(rs.getInt("DepartmentId"), dep);
                }

                sellerList.add(instantiateSeller(rs, dep));
            }

            return sellerList;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        return new Department(
            rs.getInt("DepartmentId"), 
            rs.getString("DepName")
        );
    }
    
    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException{
        return new Seller(
            rs.getInt("Id"),
            rs.getString("Name"),
            rs.getString("Email"),
            LocalDate.parse(rs.getDate("BirthDate").toString()),
            rs.getDouble("BaseSalary"),
            department
        );
    }
}
