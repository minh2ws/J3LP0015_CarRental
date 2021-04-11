/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblOrder;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author minhv
 */
public class TblOrderDTO implements Serializable {
    private String orderID;
    private Date bookingDate;
    private Date returnDate;
    private String status;
    private double totalAmount;
    private double totalAfterDiscount;
    private String email;

    public TblOrderDTO() {
    }

    public TblOrderDTO(String orderID, Date bookingDate, Date returnDate, String status, double totalAmount, double totalAfterDiscount, String email) {
        this.orderID = orderID;
        this.bookingDate = bookingDate;
        this.returnDate = returnDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.totalAfterDiscount = totalAfterDiscount;
        this.email = email;
    }

    /**
     * @return the orderID
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /**
     * @return the bookingDate
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * @param bookingDate the bookingDate to set
     */
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the totalAmount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return the totalAfterDiscount
     */
    public double getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    /**
     * @param totalAfterDiscount the totalAfterDiscount to set
     */
    public void setTotalAfterDiscount(double totalAfterDiscount) {
        this.totalAfterDiscount = totalAfterDiscount;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
