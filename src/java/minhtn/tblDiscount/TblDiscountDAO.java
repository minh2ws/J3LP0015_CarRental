/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblDiscount;

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
public class TblDiscountDAO implements Serializable {
    
    public TblDiscountDTO getDiscount(String discountCode) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT expiryDate, discount \n" +
                    "FROM TblDiscount \n" +
                    "WHERE discountCode = ? ";
                ps = cn.prepareStatement(sql);
                ps.setString(1, discountCode);
                
                rs = ps.executeQuery();
                if (rs.next()) {
                    Date expiredDate = rs.getDate("expiryDate");
                    int discount = rs.getInt("discount");
                    TblDiscountDTO dto = new TblDiscountDTO(discountCode, expiredDate, discount);
                    return dto;
                } //end if discount is existed
            } //end if connection is existed
        } finally {
            if (rs != null)
                rs.close();
            
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
        return null;
    }
}
