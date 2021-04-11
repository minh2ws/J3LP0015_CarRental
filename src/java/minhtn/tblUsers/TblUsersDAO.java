/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblUsers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import minhtn.utilities.DBUtils;

/**
 *
 * @author minhv
 */
public class TblUsersDAO implements Serializable {

    public TblUsersDTO checkLogin(String email, String password) 
	throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "SELECT email, password, phone, name, address, dateCreate, role, status " +
                        "FROM TblUsers " +
                        "WHERE email = ? AND password = ?";
                ps = cn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);
		rs = ps.executeQuery();
		if (rs.next()) {
		    String emailString = rs.getString("email");
		    String passwordString = rs.getString("password");
		    if (email.equals(emailString) && password.equals(passwordString)) {
			String phone = rs.getString("phone");
			String name = rs.getString("name");
			String address = rs.getString("address");
			Date dateCreate = rs.getDate("dateCreate");
                        String role = rs.getString("role");
			String status = rs.getString("status");
			TblUsersDTO dto = new TblUsersDTO(email, password, phone, name, address, dateCreate, role, status);
			return dto;
		    } //end if user and password matched
		    return null;
		}//end if record is existed
            } //end if connection is existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return null;
    }
    
    public boolean registerAccount(String email, String password, String phone, 
            String name,String address) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = DBUtils.makeConnection();
           
            if (cn != null) {
                String sql = "INSERT INTO TblUsers "
                        + "(email, password, phone, name, address, role, status) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?) ";
                ps = cn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setString(3, phone);
                ps.setString(4, name);
                ps.setString(5, address);
                ps.setString(6, "GUEST");
                ps.setString(7, "NEW");
                
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            } //end if connection is connected
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return false;
    }
    
    public void updateStatusAccount(String email)
            throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = DBUtils.makeConnection();
           
            if (cn != null) {
                String sql = "UPDATE tblUsers "
                        + "SET status = 'ACTIVE' "
                        + "WHERE email = ?";
                ps = cn.prepareStatement(sql);
                ps.setString(1, email);
                
                ps.executeUpdate();
            } //end if connection is connected
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }
}
