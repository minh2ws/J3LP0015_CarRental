/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblCar;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import minhtn.utilities.DBUtils;
import minhtn.utilities.DateUtils;

/**
 *
 * @author minhv
 */
public class TblCarDAO implements Serializable {
    private List<TblCarDTO> listCar;
    private List<String> listCarID;
    private Map<String, TblCarDTO> mapCar;
    private Map<String, Integer> mapCarRented;

    public List<TblCarDTO> getListCar() {
        return listCar;
    }

    public List<String> getListCarID() {
        return listCarID;
    }

    public Map<String, TblCarDTO> getMapCar() {
        return mapCar;
    }

    public Map<String, Integer> getMapCarRented() {
        return mapCarRented;
    }
    
    public void getAllCar(String role) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "SELECT carID, carName, image, color, year, price, quantity, description, ca.categoryName\n" +
                        "FROM TblCar c, TblCategory ca\n" +
                        "WHERE c.categoryId = ca.categoryID AND quantity > ?\n" +
                        "GROUP BY carID, carName, image, color, year, price, quantity, description, ca.categoryName\n" +
                        "ORDER BY year DESC\n" +
                        "OFFSET 0 ROWS\n" +
                        "FETCH NEXT 20 ROWS ONLY";
                ps = cn.prepareStatement(sql);
                if (role.equals("ADMIN")) 
                    ps.setInt(1, -1);
                else 
                    ps.setInt(1, 0);
                
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String image = rs.getString("image");	
                    String color = rs.getString("color");
                    Date year = DateUtils.fixDate(rs.getDate("year"));
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String description = rs.getString("description");
                    String categoryId = rs.getString("categoryName");
                    
                    if (listCar == null)
                        listCar = new ArrayList<>();
                    
                    TblCarDTO car = new TblCarDTO(carID, carName, image, color, year, price, quantity, description, categoryId);
                    listCar.add(car);
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
    
    public int getTotalCar(String role) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "SELECT count(carID) AS totalCar \n" +
                    "FROM TblCar\n" +
                    "WHERE quantity > ?";
                ps = cn.prepareStatement(sql);
                if (role.equals("ADMIN")) 
                    ps.setInt(1, -1);
                else 
                    ps.setInt(1, 0);
                
                rs = ps.executeQuery();
                if (rs.next())           
                   return rs.getInt("totalCar");
            } //end if connection is connected
        } finally {
            if (rs != null)
                rs.close();
            
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
        return 0;
    }
    
    public void searchCar(String searchValue, String categoryID
            , int page, int amount) throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "SELECT carID, carName, image, color, year, price, quantity, description, ca.categoryName\n" +
                        "FROM TblCar c, TblCategory ca\n" +
                        "WHERE c.categoryId = ca.categoryID AND quantity >= ?\n";
                
                //check searchValue is have or not for search with car name
                if (!searchValue.trim().isEmpty()) 
                    sql += "AND carName like N'%" + searchValue + "%'\n";
                
                //check categoryId is have or not for search with category
                if (!categoryID.trim().isEmpty())
                    sql += "AND ca.categoryID LIKE '" + categoryID + "'\n";
                
                sql += "GROUP BY carID, carName, image, color, year, price, quantity, description, ca.categoryName\n" +
                        "ORDER BY year DESC\n" +
                        "OFFSET ? ROWS\n" +
                        "FETCH NEXT 20 ROWS ONLY";
                
                ps = cn.prepareStatement(sql);
                
                //set amount == quantity
                ps.setInt(1, amount);
                //set page
                ps.setInt(2, (page - 1) * 20);
                
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String image = rs.getString("image");	
                    String color = rs.getString("color");
                    Date year = DateUtils.fixDate(rs.getDate("year"));
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String description = rs.getString("description");
                    String categoryId = rs.getString("categoryName");
                    
                    if (mapCar == null)
                        mapCar = new HashMap<>();
                    
                    TblCarDTO car = new TblCarDTO(carID, carName, image, color, year, price, quantity, description, categoryId);
                    mapCar.put(carID ,car);
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
    
    public int getTotalCarSearched(String searchValue, String categoryID, 
            int amount) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "SELECT count(carID) AS totalCar \n" +
                    "FROM TblCar c, TblCategory ca\n" +
                    "WHERE c.categoryId = ca.categoryID AND quantity >= ? \n";
                
                //check searchValue is have or not for search with car name
                if (!searchValue.trim().isEmpty()) 
                    sql += "AND carName LIKE N'%" + searchValue + "%'\n";
                
                //check categoryId is have or not for search with category
                if (!categoryID.trim().isEmpty())
                    sql += "AND ca.categoryID LIKE '" + categoryID + "'\n";
                
                ps = cn.prepareStatement(sql);
                
                //set amount == quantity
                ps.setInt(1, amount);
                
                rs = ps.executeQuery();
                if (rs.next())              
                   return rs.getInt("totalCar");
            } //end if connection is connected
        } finally {
           if (rs != null)
                rs.close();
            
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
        return 0;
    }
    
    public void getCarRented(String rentalDate, String returnDate) 
        throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "SELECT c.carID, SUM(od.quantity) AS quantity\n" +
                    "FROM TblCar c, TblOrderDetail od, TblOrder o\n" +
                    "WHERE c.carID = od.carID AND od.orderID = o.orderID "
                  + "AND ((bookingDate BETWEEN ? AND ?) "
                  + "OR (returnDate BETWEEN ? AND ?))\n" +
                    "GROUP BY c.carID";
                
                ps = cn.prepareStatement(sql);
                ps.setString(1, rentalDate);
                ps.setString(2, returnDate);
                ps.setString(3, rentalDate);
                ps.setString(4, returnDate);
                
                rs = ps.executeQuery();
                
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    int quatity = rs.getInt("quantity");
                    
                    if (mapCarRented == null)
                        mapCarRented = new HashMap<>();
                    
                    mapCarRented.put(carID, quatity);
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
    
    public TblCarDTO getCarDetail(String carID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = DBUtils.makeConnection();
            
            if (cn != null) {
                String sql = "SELECT carID, image, carName, color, year, price, quantity, ca.categoryName, description \n" +
                    "FROM TblCar c, TblCategory ca \n" +
                    "WHERE c.categoryId = ca.categoryId AND carID = ?";
                
                ps = cn.prepareStatement(sql);
                ps.setString(1, carID);
                
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    String carName = rs.getString("carName");
                    String image = rs.getString("image");	
                    String color = rs.getString("color");
                    Date year = DateUtils.fixDate(rs.getDate("year"));
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String description = rs.getString("description");
                    String categoryId = rs.getString("categoryName");
                    
                    TblCarDTO dto = new TblCarDTO(carID, carName, image, color, year, price, quantity, description, categoryId);
                    
                    return dto;
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
        return null;
    }
}
