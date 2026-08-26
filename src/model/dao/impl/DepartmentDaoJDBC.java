package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
    Connection conn = null;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
              "SELECT department.* "
              + "FROM department "  
              + "WHERE Id = ?"
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                Department department = instantiateDepartment(rs);
                return department;
            } else {
                throw new DbException("FindById: Department not found!");
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT department.* "
                + "FROM department"
            );

            rs = st.executeQuery();

            List<Department> departmentList = new ArrayList<>();

            while(rs.next()){
                departmentList.add(instantiateDepartment(rs));
            }

            return departmentList;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    @Override
    public void insert(Department department) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void update(Department department) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        return new Department(
            rs.getInt("Id"), 
            rs.getString("Name")
        );
    }
}
