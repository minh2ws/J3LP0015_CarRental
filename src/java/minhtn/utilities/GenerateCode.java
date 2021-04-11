/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.utilities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 *
 * @author minhv
 */
public class GenerateCode implements Serializable {
    public static String generateCodeValidation(int codeLength) {
        String code = "";
        Random rd = new Random();
        for (int i = 0; i < codeLength; i++) {
            code += rd.nextInt(10);
        }
        return code;
    }
    
    //create OrderId with format YYYYMMdd-hhmmss-xxxx (x is digit with random)
    public static String generateCartID() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd-hhmmss");
        java.util.Date date = new java.util.Date();
        
        String dateGetted = dateFormat.format(date);
        
        String randomString = "";
        for (int i = 0; i < 4; i++) {
            int randomNum = new Random().nextInt(10);
            randomString += randomNum;
        }
        
        return dateGetted + "-" + randomString;
    }
}
