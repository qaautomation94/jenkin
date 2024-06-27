package com.mscs.emr.automation.functional;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class sendMail {

	public static void main(String[] args) throws EmailException {
		
		System.out.println("Test Started");
		
		Email email = new SimpleEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("ifti@gmail.com", "test!"));
		email.setSSLOnConnect(true);
		email.setFrom("test@test.com");
		email.setSubject("TestMail");
		email.setMsg("This is a test mail ... :-)");
		email.addTo("palash@test.com");
		email.send();
		
		System.out.println("Mail Sent");
	}

}
