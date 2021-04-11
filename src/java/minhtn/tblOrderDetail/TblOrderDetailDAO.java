/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblOrderDetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import minhtn.cart.CartItem;
import minhtn.tblCar.TblCarDTO;
import minhtn.utilities.DBUtils;

/**
 *
 * @author minhv
 */
public class TblOrderDetailDAO implements Serializable {
    
    public boolean createOrderDetail(String orderId, List<CartItem> listCartItem) 
        throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "INSERT INTO TblOrderDetail(carID, orderID, quantity, total)\n" +
                    "VALUES (?, ?, ?, ?)";
                ps = cn.prepareStatement(sql);
                
                cn.setAutoCommit(false);
                
                int totalRow = 0;
                for (CartItem cartItem : listCartItem) {
                    ps.setString(1, cartItem.getCar().getCarID());
                    ps.setString(2, orderId);
                    ps.setInt(3, cartItem.getQuantity());
                    ps.setDouble(4, cartItem.getTotal());
                    ps.addBatch();
                    totalRow++;
                }
                
                int[] result = ps.executeBatch();
                
                if (result.length == totalRow) {
                    cn.commit();
                    return true;
                }
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
    
    public List<CartItem> getOrderDetail(String orderId) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CartItem> listItem = null;
        
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT od.carID, carName, image, price, od.quantity \n" +
                    "FROM TblCar c, TblOrderDetail od \n" +
                    "WHERE c.carID = od.carID AND od.orderID = ? ";
                
                ps = cn.prepareStatement(sql);
                ps.setString(1, orderId);
                
                rs = ps.executeQuery();
                
                while (rs.next()) {                    
                    TblCarDTO car = new TblCarDTO();
                    car.setCarID(rs.getString("carID"));
                    car.setCarName(rs.getString("carName"));
                    car.setImage(rs.getString("image"));
                    car.setPrice(rs.getDouble("price"));
                    
                    CartItem item = new CartItem(car, rs.getInt("quantity"));
                    
                    if (listItem == null)
                        listItem = new ArrayList<>();
                    
                    listItem.add(item);
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
        return listItem;
    }
}
