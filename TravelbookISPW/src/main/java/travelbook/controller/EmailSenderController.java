package main.java.travelbook.controller;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import main.java.exception.DBException;
import main.java.exception.FewParametersException;
import main.java.exception.MalformedEmailException;
import main.java.travelbook.model.EmailAccount;
import main.java.travelbook.model.dao.EmailDao;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Properties;
public class EmailSenderController {
	
		private EmailAccount a;
		public EmailSenderController() throws MessagingException {
			EmailDao dao=new EmailDao();
			try {
				this.a=dao.getData();
			} catch (DBException e) {
				throw new MessagingException("errore");
			}
		}
	
		public void sendMessage(String dest,String mex, String subj) throws MessagingException,MalformedEmailException {
		// Create a mail session
Properties props=new Properties();
	    props.put("mail.smtp.host", "smtp.office365.com");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.user", this.a.getEmail());
	    props.put("mail.smtp.starttls.enable","true");
	    props.put("mail.smtp.port",587);
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    message.setSubject(subj);
	    message.setText(mex);
	    if(!dest.contains("@") || !dest.contains(".")) {
	    	throw new MalformedEmailException("L'email destinatario non ha la sintassi corretta");
	    }
	    // add address of mit and dest
	    InternetAddress fromAddress = new InternetAddress(this.a.getEmail());
	    InternetAddress toAddress = new InternetAddress(dest);
	    message.setFrom(fromAddress);
	    message.setRecipient(Message.RecipientType.TO, toAddress);
	    // Finally send message
	    
	    Transport transport=session.getTransport("smtp");
	    //Authentication of mit
	    transport.connect("smtp.office365.com",this.a.getEmail(),this.a.getPswd());
	    transport.sendMessage(message, message.getAllRecipients());
		}
	
		public void sendMessage(List<String> destinatari, List<String> mexByDest, List<String> subj) throws MalformedEmailException,MessagingException,FewParametersException{
			if(destinatari.size()!=mexByDest.size() || destinatari.size()!=subj.size()) {
				throw new FewParametersException("Insert the same size of dest, mex and subj");
			}
			else {
			for (int i=0;i<destinatari.size();i++) {
				sendMessage(destinatari.get(i),mexByDest.get(i),subj.get(i));
			}
			}
		}
}
