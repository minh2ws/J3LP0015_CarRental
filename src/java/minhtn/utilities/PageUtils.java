/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.utilities;

import java.io.Serializable;

/**
 *
 * @author minhv
 */
public class PageUtils implements Serializable {
    public static int caculatePageRequired(int totalCar, int carPerPage) {
        int totalPage;
        
        if (totalCar % carPerPage != 0)
            totalPage = (totalCar / carPerPage) + 1;
        else
            totalPage = totalCar / carPerPage;
        return totalPage;
    }
}
