/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblUsers;

import java.io.Serializable;

/**
 *
 * @author minhv
 */
public class TblUsersRegisterErrors implements Serializable{
    private String emailFormatError;
    private String passwordLengthErorr;
    private String passwordConfirmNotMatched;
    private String fullnameLengthError;
    private String phoneFormatError;
    private String phoneLengthError;
    private String addressLengthError;
    private String emailExisted;
    private String activeCodeError;

    /**
     * @return the emailFormatError
     */
    public String getEmailFormatError() {
        return emailFormatError;
    }

    /**
     * @param emailFormatError the emailFormatError to set
     */
    public void setEmailFormatError(String emailFormatError) {
        this.emailFormatError = emailFormatError;
    }

    /**
     * @return the passwordLengthErorr
     */
    public String getPasswordLengthErorr() {
        return passwordLengthErorr;
    }

    /**
     * @param passwordLengthErorr the passwordLengthErorr to set
     */
    public void setPasswordLengthErorr(String passwordLengthErorr) {
        this.passwordLengthErorr = passwordLengthErorr;
    }

    /**
     * @return the passwordConfirmNotMatched
     */
    public String getPasswordConfirmNotMatched() {
        return passwordConfirmNotMatched;
    }

    /**
     * @param passwordConfirmNotMatched the passwordConfirmNotMatched to set
     */
    public void setPasswordConfirmNotMatched(String passwordConfirmNotMatched) {
        this.passwordConfirmNotMatched = passwordConfirmNotMatched;
    }

    /**
     * @return the fullnameLengthError
     */
    public String getFullnameLengthError() {
        return fullnameLengthError;
    }

    /**
     * @param fullnameLengthError the fullnameLengthError to set
     */
    public void setFullnameLengthError(String fullnameLengthError) {
        this.fullnameLengthError = fullnameLengthError;
    }

    /**
     * @return the phoneFormatError
     */
    public String getPhoneFormatError() {
        return phoneFormatError;
    }

    /**
     * @param phoneFormatError the phoneFormatError to set
     */
    public void setPhoneFormatError(String phoneFormatError) {
        this.phoneFormatError = phoneFormatError;
    }

    /**
     * @return the phoneLengthError
     */
    public String getPhoneLengthError() {
        return phoneLengthError;
    }

    /**
     * @param phoneLengthError the phoneLengthError to set
     */
    public void setPhoneLengthError(String phoneLengthError) {
        this.phoneLengthError = phoneLengthError;
    }

    /**
     * @return the addressLengthError
     */
    public String getAddressLengthError() {
        return addressLengthError;
    }

    /**
     * @param addressLengthError the addressLengthError to set
     */
    public void setAddressLengthError(String addressLengthError) {
        this.addressLengthError = addressLengthError;
    }

    /**
     * @return the emailExisted
     */
    public String getEmailExisted() {
        return emailExisted;
    }

    /**
     * @param emailExisted the emailExisted to set
     */
    public void setEmailExisted(String emailExisted) {
        this.emailExisted = emailExisted;
    }

    /**
     * @return the activeCodeError
     */
    public String getActiveCodeError() {
        return activeCodeError;
    }

    /**
     * @param activeCodeError the activeCodeError to set
     */
    public void setActiveCodeError(String activeCodeError) {
        this.activeCodeError = activeCodeError;
    }
}
