/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.feedback;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import minhtn.utilities.DBUtils;

/**
 *
 * @author minhv
 */
public class FeedBackDAO implements Serializable {
    private List<FeedBackDTO> listFeedback;

    public List<FeedBackDTO> getListFeedback() {
        return listFeedback;
    }
    
    public void getAllFeedback(String carID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "SELECT u.name, feedback, point\n" +
                    "FROM TblCar c, TblOrderDetail od, TblOrder o, TblUsers u \n" +
                    "WHERE c.carID = od.carID AND od.orderID = o.orderID AND o.email = u.email \n" +
                    "AND c.carID = ?";
                ps = cn.prepareStatement(sql);
                ps.setString(1, carID);
                
                rs = ps.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    String content = rs.getString("feedback");
                    int point = rs.getInt("point");
                    
                    if (listFeedback == null) {
                        listFeedback = new ArrayList<>();
                    }
                    
                    FeedBackDTO feedback = new FeedBackDTO(name, content, point);
                    listFeedback.add(feedback);
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
    }
    
    public boolean insertFeedback(String orderId, String carId, String feedback, int point) 
        throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "UPDATE TblOrderDetail\n" +
                    "SET feedback = ?, point = ? \n" +
                    "FROM TblCar c, TblOrderDetail od, TblOrder o \n" +
                    "WHERE c.carID = od.carID AND od.orderID = o.orderID AND od.carID = ? AND od.orderID = ? ";
                ps = cn.prepareStatement(sql);
                ps.setString(1, feedback);
                ps.setInt(2, point);
                ps.setString(3, carId);
                ps.setString(4, orderId);
                
                int row = ps.executeUpdate();
                if (row > 0) {
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
}
