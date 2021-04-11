/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.utilities;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author minhv
 */
public class MailUtils implements Serializable {

    private static final String SMTP_EMAIL = "minhtn1709.activationmail@gmail.com"; //email use to send
    private static final String SMTP_PASSWORD = "minhtn1709";   //password for email
    private static final String SMTP_SERVER = "smtp.gmail.com"; //using smtp host provide by google
    private static final int SMTP_PORT = 587; //Port for send mail via google using TSL(Transport Layer Security)

    public static void sendMailActivation(String recieveMail, String code) 
            throws AddressException, MessagingException {
        //create Properties to setup email
        Properties prop = new Properties();
        prop.put("mail.smtp.host", SMTP_SERVER);
        prop.put("mail.smtp.port", SMTP_PORT);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_EMAIL, SMTP_PASSWORD);
            }
        });
        
        //create mail
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_EMAIL));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recieveMail));
        message.setSubject("Acitve account from car rental website");
        message.setText("Activation code: " + code);
        
        //send mail
        Transport.send(message);
    }
}
