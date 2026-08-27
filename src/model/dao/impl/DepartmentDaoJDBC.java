package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO department "
                + "(Name)"
                + "VALUES (?)", Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, department.getName());
            st.executeUpdate();

            rs = st.getGeneratedKeys();

            if(rs.next()){
                department.setId(rs.getInt(1));
            } else {
                throw new DbException("Insert error!");
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    @Override
    public void update(Department department) {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                "UPDATE department SET Name = ? WHERE id = ?"
            );

            st.setString(1, department.getName());
            st.setInt(2, department.getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected == 0){
                throw new DbException("Update error: Department not found!");
            }

            System.out.println("Update completed successfully. Rows affected: " + rowsAffected);
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
                "DELETE FROM department WHERE Id = ?"
            );

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if(rowsAffected == 0){
                throw new DbException("Delete error: Department not found!");
            } else {
                System.out.println("Successfully deleted! Rows affected: " + rowsAffected);
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        return new Department(
            rs.getInt("Id"), 
            rs.getString("Name")
        );
    }
}
