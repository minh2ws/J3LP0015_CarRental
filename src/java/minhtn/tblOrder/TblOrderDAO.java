/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblOrder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import minhtn.cart.CartObject;
import minhtn.tblUsers.TblUsersDTO;
import minhtn.utilities.DBUtils;
import minhtn.utilities.DateUtils;

/**
 *
 * @author minhv
 */
public class TblOrderDAO implements Serializable {
    private List<TblOrderDTO> listOrder;

    public List<TblOrderDTO> getListOrder() {
        return listOrder;
    }
    
    public boolean createOrderRentalCar(String orderId, String email, 
            CartObject cart) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "INSERT INTO TblOrder(orderID, bookingDate, returnDate, status, totalAmount, totalAfterDiscount, email)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
                ps = cn.prepareStatement(sql);
                ps.setString(1, orderId);
                ps.setDate(2, cart.getRentalDate());
                ps.setDate(3, cart.getReturnDate());
                ps.setString(4, "ACTIVE");
                ps.setDouble(5, cart.getTotalPriceOfCart());
                ps.setDouble(6, cart.getTotalPriceOfCartHasDiscounted());
                ps.setString(7, email);
                
                int row = ps.executeUpdate();
                if (row > 0) 
                    return true;
            } //end if connection is connected
        } finally {
           if (rs != null)
                rs.close();
            
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
        return false;
    }
    
    public void getListOrderByEmail(String email) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT orderID, u.email, bookingDate, returnDate, o.status, totalAmount, totalAfterDiscount \n" +
                    "FROM TblOrder o, TblUsers u \n" +
                    "WHERE  o.email = u.email AND o.email = ? AND o.status = 'ACTIVE' \n" +
                    "GROUP BY o.orderID, u.email, bookingDate, returnDate, o.status, totalAmount, totalAfterDiscount \n" +
                    "ORDER BY bookingDate DESC";
                
                ps = cn.prepareStatement(sql);
                ps.setString(1, email);
                
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    String orderId = rs.getString("orderID");
                    Date rentalDate = DateUtils.fixDate(rs.getDate("bookingDate"));
                    Date returnDate = DateUtils.fixDate(rs.getDate("returnDate"));
                    String status = rs.getString("status");
                    double totalAmount = rs.getDouble("totalAmount");
                    double totalAfterDiscount = rs.getDouble("totalAfterDiscount");
                    
                    if (listOrder == null)
                        listOrder = new ArrayList<>();
                    
                    TblOrderDTO order = new TblOrderDTO(orderId, rentalDate, returnDate, status, totalAmount, totalAfterDiscount, email);
                    
                    listOrder.add(order);
                }
            } //end if connection is existed
        } finally {
            if (rs != null)
                rs.close();
            
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
    }
    
    public void getListOrderByAdmin() throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT orderID, email, bookingDate, returnDate, status, totalAmount, totalAfterDiscount\n " +
                    "FROM TblOrder\n " +
                    "GROUP BY orderID, email, bookingDate, returnDate, status, totalAmount, totalAfterDiscount\n " +
                    "ORDER BY bookingDate DESC";
                
                ps = cn.prepareStatement(sql);
                
                rs = ps.executeQuery();
                while (rs.next()) {      
                    String email = rs.getString("email");
                    String orderId = rs.getString("orderID");
                    Date rentalDate = DateUtils.fixDate(rs.getDate("bookingDate"));
                    Date returnDate = DateUtils.fixDate(rs.getDate("returnDate"));
                    String status = rs.getString("status");
                    double totalAmount = rs.getDouble("totalAmount");
                    double totalAfterDiscount = rs.getDouble("totalAfterDiscount");
                    
                    if (listOrder == null)
                        listOrder = new ArrayList<>();
                    
                    TblOrderDTO order = new TblOrderDTO(orderId, rentalDate, returnDate, status, totalAmount, totalAfterDiscount, email);
                    
                    listOrder.add(order);
                }
            } //end if connection is existed
        } finally {
            if (rs != null)
                rs.close();
            
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
    }
    
    public void searchOrder(String searchValue, String fromDate, String toDate, 
            TblUsersDTO user) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT o.orderID, o.email, bookingDate, returnDate, status, totalAmount, totalAfterDiscount\n" +
                    "FROM TblOrder o, TblOrderDetail od,  TblCar c \n" +
                    "WHERE o.orderID = od.orderID AND od.carID = c.carID \n";
                
                //check role for search
                String role = user.getRole();
                if (!role.equals("ADMIN"))
                    sql += "AND o.email = '" + user.getEmail() + "' AND status = 'ACTIVE' \n";
                
                if (!searchValue.trim().isEmpty())
                    sql += "AND carName like '%" + searchValue + "%' \n";
                
                if (!fromDate.trim().isEmpty() && !toDate.trim().isEmpty())
                    sql += "AND (bookingDate BETWEEN '" + fromDate + "' AND '" + toDate + "') \n";
                
                sql += "GROUP BY o.orderID, o.email, bookingDate, returnDate, status, totalAmount, totalAfterDiscount";
                
                ps = cn.prepareStatement(sql);
                
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    String orderId = rs.getString("orderID");
                    String status = rs.getString("status");
                    Date rentalDate = DateUtils.fixDate(rs.getDate("bookingDate"));
                    Date returnDate = DateUtils.fixDate(rs.getDate("returnDate"));
                    double totalAmount = rs.getDouble("totalAmount");
                    double totalAfterDiscount = rs.getDouble("totalAfterDiscount");
                    
                    if (listOrder == null)
                        listOrder = new ArrayList<>();
                    
                    TblOrderDTO order = new TblOrderDTO(orderId, rentalDate, returnDate, status, totalAmount, totalAfterDiscount, user.getEmail());
                    
                    listOrder.add(order);
                }
            } //end if connection is existed
        } finally {
            if (rs != null)
                rs.close();
            
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
    }
    
    public boolean deleteOrder(String orderId) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "UPDATE TblOrder \n" +
                    "SET status = 'DEACTIVE' \n" +
                    "WHERE orderID = ?";
                ps = cn.prepareStatement(sql);
                ps.setString(1, orderId);
                
                int row = ps.executeUpdate();
                if (row > 0) 
                    return true;
            } //end if connection is connected
        } finally {
           if (rs != null)
                rs.close();
            
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
        return false;
    }
}
