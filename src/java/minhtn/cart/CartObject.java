/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.cart;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import minhtn.tblCar.TblCarDAO;
import minhtn.tblCar.TblCarDTO;

/**
 *
 * @author minhv
 */
public class CartObject {
    private Date rentalDate;
    private Date returnDate;
    private double totalDay;
    private String discountCode;
    private int discount;
    
    private List<CartItem> listItem;

    public List<CartItem> getListItem() {
        return listItem;
    }
    
    public int getTotalItem() {
        return getListItem().size();
    }
    
    public void addItemToCart(String carId) throws SQLException, NamingException {
        //check list item
        if (listItem == null)
            listItem = new ArrayList<>();
        
        //check item is exist in cart or not
        int quantity = 1;
        CartItem cartItem = findItem(carId);
        
        if (cartItem == null) {
            TblCarDAO dao = new TblCarDAO();
            TblCarDTO car = dao.getCarDetail(carId);
            cartItem = new CartItem(car, quantity);
            listItem.add(cartItem);
        } else {
            quantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(quantity);
            cartItem.updateTotal();
        }
    }
    
    private CartItem findItem(String carID) {
        for (CartItem cartItem : listItem) {
            if (cartItem.getCar().getCarID().equals(carID))
                return cartItem;
        }
        return null;
    }
    
    public void removeItem(String carID) {
        if (listItem == null)
            return;
        
        CartItem cartItem = findItem(carID);
        
        if (cartItem != null) {
            listItem.remove(cartItem);
            
            if (listItem.isEmpty()) {
                listItem = null;
            } //delete cart
        }
    }
    
    public void updateQuantityItem(String carID, int quantity) {
        CartItem cartItem = findItem(carID);
        cartItem.setQuantity(quantity);
        cartItem.updateTotal();
        
    }
    
    public double getTotalPriceOfCart() {
        double totalPrice = 0;
        for (CartItem cartItem : listItem) {
            totalPrice += cartItem.getTotal();
        }
        return totalPrice * totalDay;
    }
    
    public double getTotalPriceOfCartHasDiscounted() {
        double totalPrice = getTotalPriceOfCart();
        return totalPrice - (totalPrice * discount / 100);
    }

    /**
     * @return the rentalDate
     */
    public Date getRentalDate() {
        return rentalDate;
    }

    /**
     * @param rentalDate the rentalDate to set
     */
    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the totalDay
     */
    public double getTotalDay() {
        return totalDay;
    }

    /**
     * @param totalDay the totalDay to set
     */
    public void setTotalDay(double totalDay) {
        this.totalDay = totalDay;
    }

    /**
     * @return the discountCode
     */
    public String getDiscountCode() {
        return discountCode;
    }

    /**
     * @param discountCode the discountCode to set
     */
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    /**
     * @return the discount
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
