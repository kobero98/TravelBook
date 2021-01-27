package main.java.travelbook.controller;
import javax.mail.Transport;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Properties;
public class EmailSenderController {
		public void sendMessage(String dest,String mex, String subj) throws MessagingException {
		// Create a mail session
		JSONParser parser=new JSONParser();
		JSONObject jsonObject;
		try {
			Reader reader=new FileReader("src/main/java/travelbook/controller/configuration.json");
			jsonObject=(JSONObject)parser.parse(reader);
			
		}catch(Exception e){
			throw new MessagingException(e.getMessage());
		}
		String mit = jsonObject.get("email").toString();
		String pswd= jsonObject.get("password").toString();
		Properties props=new Properties();
	    props.put("mail.smtp.host", "smtp.office365.com");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.user", mit);
	    props.put("mail.smtp.starttls.enable","true");
	    props.put("mail.smtp.port",587);
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    message.setSubject(subj);
	    message.setText(mex);
	    // add address of mit and dest
	    InternetAddress fromAddress = new InternetAddress(mit);
	    InternetAddress toAddress = new InternetAddress(dest);
	    message.setFrom(fromAddress);
	    message.setRecipient(Message.RecipientType.TO, toAddress);
	    // Finally send message
	    
	    Transport transport=session.getTransport("smtp");
	    //Authentication of mit
	    transport.connect("smtp.office365.com",mit,pswd);
	    transport.sendMessage(message, message.getAllRecipients());
		}
		public void sendMessage(List<String> destinatari, List<String> mexByDest, List<String> subj) throws MessagingException{
			if(destinatari.size()!=mexByDest.size() || destinatari.size()!=subj.size()) {
				//Lancia una eccezzione
			}
			else {
			for (int i=0;i<destinatari.size();i++) {
				sendMessage(destinatari.get(i),mexByDest.get(i),subj.get(i));
			}
			}
		}
}
