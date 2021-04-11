/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblDiscount;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author minhv
 */
public class TblDiscountDTO implements Serializable {
    private String discountCode;
    private Date expiredDate;
    private int discount;

    public TblDiscountDTO() {
    }

    public TblDiscountDTO(String discountCode, Date expiredDate, int discount) {
        this.discountCode = discountCode;
        this.expiredDate = expiredDate;
        this.discount = discount;
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
     * @return the expiredDate
     */
    public Date getExpiredDate() {
        return expiredDate;
    }

    /**
     * @param expiredDate the expiredDate to set
     */
    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
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
