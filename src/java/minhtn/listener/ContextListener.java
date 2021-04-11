/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author minhv
 */
public class ContextListener implements ServletContextListener {
    
    private final String FUNCTION_MAPPING_FILE = "/WEB-INF/functions.txt";
    private final String FRONTEND_RESOURCE_FILE = "/WEB-INF/frontend-resource.txt";
    private final String MEMBER_FUNCTION_FILE = "/WEB-INF/member-functions.txt";
    private final String ADMIN_FUNCTION_FILE = "/WEB-INF/admin-functions.txt";
    private final String GUEST_FUNCTION_FILE = "/WEB-INF/guest-functions.txt";



    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String path = context.getRealPath("/");
        
        //set function map to context map
        Map<String, String> functionMap = loadFunctionMap(path + FUNCTION_MAPPING_FILE, context);
        if(functionMap != null) context.setAttribute("FUNCTION_MAP", functionMap);
        
        //set front-end resource to context map
        List<String> frontendResource = loadList(path + FRONTEND_RESOURCE_FILE, context);
        if (frontendResource != null) context.setAttribute("FRONTEND_RESOURCES", frontendResource);
        
        //set admin function list to context map
        List<String> adminFunction = loadList(path + ADMIN_FUNCTION_FILE, context);
        if (adminFunction != null) context.setAttribute("ADMIN_FUNCTION_LIST", adminFunction);
        
        //set member function list to context map
        List<String> memberFunction = loadList(path + MEMBER_FUNCTION_FILE, context);
        if (memberFunction != null) context.setAttribute("MEMBER_FUNCTION_LIST", memberFunction);
        
        //set guest function list to context map
        List<String> guestFunction = loadList(path + GUEST_FUNCTION_FILE, context);
        if (guestFunction != null) context.setAttribute("GUEST_FUNCTION_LIST", guestFunction);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
    
    private Map<String, String> loadFunctionMap(String filename, ServletContext context) {
        FileReader file = null;
        BufferedReader bfReader = null;
        Map<String, String> functionMap = null;
        
        try {
            file = new FileReader(filename);
            bfReader = new BufferedReader(file);
            
            while (bfReader.ready()) {      
                //read string
                String row = bfReader.readLine();
                //split string
                String[] splitted = row.split("=");
                //get key and value
                String key = splitted[0];
                String value = splitted[1];
                
                //check map is null or not
                if (functionMap == null) functionMap = new HashMap<>();
                //add to map
                functionMap.put(key, value);
            }
        } catch (FileNotFoundException e) {
            context.log("ContextListener _ FileNotFoundException: " + e.getMessage());
        } catch(IOException e) {
            context.log("ContextListener _ IOException: " + e.getMessage());
        } finally {
            try {
                if(bfReader != null) bfReader.close();
                if(file != null) file.close();
            } catch (IOException e) {
                context.log("ContextListener _ Close BufferedReader-FileReader IOException: " + e.getMessage());
            }
        }
        return functionMap;
    }
    
    private List<String> loadList(String filename, ServletContext context) {
        FileReader file = null;
        BufferedReader bfReader = null;
        List<String> list = null;
        
        try {
            file = new FileReader(filename);
            bfReader = new BufferedReader(file);
            
            while (bfReader.ready()) { 
                //read each line in file
                String row = bfReader.readLine();
                
                if (list == null) {
                    list = new ArrayList<>();
                } //end if list is null
                
                list.add(row);
            }
        } catch (FileNotFoundException e) {
            context.log("ContextListener _ FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            context.log("ContextListener _ IOException: " + e.getMessage());
        }finally {
            try {
                if (bfReader != null) bfReader.close();
                if (file != null) file.close();
            } catch (IOException e) {
                context.log("ContextListener _ Close BufferedReader-FileReader IOException: " + e.getMessage());
            }
        }
        return list;
    }
}
