/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.cart;

import java.io.Serializable;

/**
 *
 * @author minhv
 */
public class CartError implements Serializable {
    private String invalidDiscountError;
    private String expiredDiscountError;
    private String quantityError;

    /**
     * @return the invalidDiscountError
     */
    public String getInvalidDiscountError() {
        return invalidDiscountError;
    }

    /**
     * @param invalidDiscountError the invalidDiscountError to set
     */
    public void setInvalidDiscountError(String invalidDiscountError) {
        this.invalidDiscountError = invalidDiscountError;
    }

    /**
     * @return the expiredDiscountError
     */
    public String getExpiredDiscountError() {
        return expiredDiscountError;
    }

    /**
     * @param expiredDiscountError the expiredDiscountError to set
     */
    public void setExpiredDiscountError(String expiredDiscountError) {
        this.expiredDiscountError = expiredDiscountError;
    }

    /**
     * @return the quantityError
     */
    public String getQuantityError() {
        return quantityError;
    }

    /**
     * @param quantityError the quantityError to set
     */
    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }
}
