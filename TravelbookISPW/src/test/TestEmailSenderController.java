package test;
import org.junit.Test;
import java.util.List;

import javax.mail.MessagingException;

import java.util.ArrayList;
import static org.junit.Assert.*;

import main.java.exception.FewParametersException;
import main.java.exception.MalformedEmailException;
import main.java.travelbook.controller.EmailSenderController;
/*
 * @author Matteo Ciccaglione
 */
public class TestEmailSenderController {
	@Test
	public void testSendMessageByBadEmail() throws MessagingException {
		//Test if the method throw a correct exception when i try to send an email to a bad structured address
		EmailSenderController controller=new EmailSenderController();
		String email="pippo.it";
		assertThrows(MalformedEmailException.class,()-> controller.sendMessage(email,"test Eccezzione Email","test"));
	}
	@Test
	public void testSendMessageMultipleDest() throws MessagingException {
		//Test if multiple send message throw a correct exception when i try to send an email without specify 
		//the correct format of parameters
		EmailSenderController controller=new EmailSenderController();
		List<String> dest=new ArrayList<>();
		List<String> subj=new ArrayList<>();
		List<String> mex=new ArrayList<>();
		dest.add("matteoc8@live.com");
		dest.add("matteociccaglione@gmail.com");
		subj.add("Test Email");
		mex.add("Questo è un email test");
		mex.add("Questo è un email test");
		assertThrows(FewParametersException.class,()-> controller.sendMessage(dest, mex, subj));
	}
}
