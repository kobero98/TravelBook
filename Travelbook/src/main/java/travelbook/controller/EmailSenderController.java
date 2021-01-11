package main.java.travelbook.controller;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;

import java.util.List;
import java.util.Properties;
public class EmailSenderController {
		public void sendMessage(String dest,String mex) throws MessagingException {
		// Create a mail session
		String mit = "travelbookispw@outlook.it";
		Properties props=new Properties();
	    props.put("mail.smtp.host", "smtp.office365.com");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.user", mit);
	    props.put("mail.smtp.starttls.enable","true");
	    props.put("mail.smtp.port",587);
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    message.setSubject("Casa");
	    message.setText(mex);
	    // add address of mit and dest
	    InternetAddress fromAddress = new InternetAddress(mit);
	    InternetAddress toAddress = new InternetAddress(dest);
	    message.setFrom(fromAddress);
	    message.setRecipient(Message.RecipientType.TO, toAddress);
	    // Finally send message
	    
	    Transport transport=session.getTransport("smtp");
	    //Authentication of mit
	    transport.connect("smtp.office365.com",mit,"ProgettoISPW2021");
	    transport.sendMessage(message, message.getAllRecipients());
		}
		public void sendMessage(List<String> destinatari, List<String> mexByDest) throws MessagingException{
			if(destinatari.size()!=mexByDest.size()) {
				//Lancia una eccezzione
			}
			else {
			for (int i=0;i<destinatari.size();i++) {
				sendMessage(destinatari.get(i),mexByDest.get(i));
			}
			}
		}
}
