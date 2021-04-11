/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author minhv
 */
public class ReCaptchaVerify implements Serializable {
    private static final String SECRET_KEY = "6Lek0zoaAAAAAMn7O2SyvWxo3saj2qCLgDMtZJVW";
    private static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String USER_AGENT = "Mozilla/5.0";
    
    public static boolean verifyCaptcha (String googleRecaptcharResponse) 
        throws MalformedURLException, IOException{
        //check captchar response
        if (googleRecaptcharResponse == null || googleRecaptcharResponse.length() == 0) {
            return false;
        }
        
        URL verifyUrl = new URL(SITE_VERIFY_URL);
        
        //open connection to verify url
        HttpURLConnection connection = (HttpURLConnection) verifyUrl.openConnection();
        
        //add header information into request to send to server;
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        
        //set data send to server
        String postParams = "secret=" + SECRET_KEY + "&response=" + googleRecaptcharResponse;
        
        //send request
        connection.setDoOutput(true);
        
        //get output stream from server
        //and write data output stream (send data server).
        OutputStream outStream = connection.getOutputStream();
        outStream.write(postParams.getBytes());
        
        outStream.flush();
        outStream.close();
        
        //get input stream of connection
        //read data server.
        InputStream inputStream = connection.getInputStream();
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        
        //in json return by server
        boolean success = jsonObject.getBoolean("success");
        
        return success;
    }
}
