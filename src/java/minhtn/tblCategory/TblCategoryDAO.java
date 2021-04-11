/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblCategory;

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
public class TblCategoryDAO implements Serializable {
    private List<TblCategoryDTO> listCategory;

    public List<TblCategoryDTO> getListCategory() {
        return listCategory;
    }
    
    public void getAllCategory() throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "SELECT categoryID, categoryName "
			+ "FROM tblCategory ";
                ps = cn.prepareStatement(sql);
                
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    String categoryID = rs.getString("categoryID");
                    String categoryName = rs.getString("categoryName");
                   
                    if (listCategory == null)
                       listCategory = new ArrayList<>();
                    
                    TblCategoryDTO dto = new TblCategoryDTO(categoryID, categoryName);
                    listCategory.add(dto);
                }
            } //end if connection is connected
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
    }
}
