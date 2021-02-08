package main.java.travelbook.controller;

import java.sql.Date;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;
import javax.mail.MessagingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import exception.DBException;
import exception.LoginPageException;
import exception.TriggerAlert;
import javafx.application.Platform;
import main.java.travelbook.model.Entity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.RegistrationBean;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.FacebookDao;
import main.java.travelbook.model.dao.PersistanceDAO;

public class ControllerLogin {
	private RegistrationBean createRegisterBeanFromFacebook(String accessToken,String id) throws IOException, ParseException
	{
		RegistrationBean user=new RegistrationBean();
		String url = "https://graph.facebook.com/v8.0/"+id+"?fields=name,first_name,last_name,email,gender,birthday&access_token="+accessToken;
		HttpClient client=HttpClientBuilder.create().build();
		HttpGet request=new HttpGet(url);
		HttpResponse response = client.execute(request);
		String json = EntityUtils.toString(response.getEntity(),StandardCharsets.UTF_8);
		JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);
        JSONObject obj=(JSONObject)resultObject;
        user.setEmail(obj.get("email").toString());
        user.setName(obj.get("first_name").toString());
        user.setSurname(obj.get("last_name").toString());
        user.setGender(obj.get("gender").toString().substring(0,1));
        String date=obj.get("birthday").toString();
        String data= date.substring(6)+"-"+date.substring(0,2)+"-"+date.substring(3,5);
        user.setBirtdate(Date.valueOf(data));
        user.setUsername(obj.get("first_name").toString()+obj.get("last_name").toString()+numberGenerator());
		user.setPassword("p"+numberGenerator()+numberGenerator()+numberGenerator());
        return user;
	}
	private void controlloAutorizzazioni(String accessToken,String id) throws LoginPageException, IOException, ParseException
	{
		String url = "https://graph.facebook.com/v8.0/"+id+"/permissions?access_token="+accessToken;
		HttpClient client=HttpClientBuilder.create().build();
		HttpGet request=new HttpGet(url);
		HttpResponse response = client.execute(request);
		String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);
        JSONObject o= (JSONObject) resultObject;
        JSONArray array=(JSONArray) o.get("data");
        for(Object a:array)
        {
        	JSONObject obj=(JSONObject) a;
        	if(obj.get("status").toString().compareTo("granted")!=0) throw new LoginPageException("Autorizzazioni Negate");
        	
        }
        
			
	}
	public UserBean facebookLogin(String string) throws LoginPageException
	{
		try {
			int i=string.indexOf("&");
			String accessToken= string.substring(14,i);
			String url = "https://graph.facebook.com/v9.0/me?&access_token="+accessToken;
			HttpClient client=HttpClientBuilder.create().build();
			HttpGet request=new HttpGet(url);
			HttpResponse response = client.execute(request);
			String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(json);
            JSONObject obj=null;
            if (resultObject instanceof JSONObject) {
                obj =(JSONObject)resultObject;
                }  
            String id="";
            if(obj!=null)
	        {
            	id=obj.get("id").toString();
	            FacebookDao dao=(FacebookDao) DaoFactory.getInstance().create(DaoType.FACEBOOK);
				UserEntity user=dao.getData(id);
				controlloAutorizzazioni(accessToken, id);
				if(user==null)
				{
					url = "https://graph.facebook.com/v9.0/"+id+"?fields=email&access_token="+accessToken;
					client=HttpClientBuilder.create().build();
					request=new HttpGet(url);
					response = client.execute(request);
					json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
					parser = new JSONParser();
		            resultObject = parser.parse(json);
		            obj=(JSONObject) resultObject;
		            String email= obj.get("email").toString();
		            int idUtente=AllQuery.getInstance().getVerifiedEmail(email);
		            if(idUtente!=0) {
		            	user=dao.setData(id,idUtente);
		            	PersistanceDAO dao1=DaoFactory.getInstance().create(DaoType.USER);
						List<Entity>l=dao1.getData(user);
						return new UserBean((UserEntity) l.get(0));
		            }
		            RegistrationBean u=createRegisterBeanFromFacebook(accessToken,id);
		            signUp(u);
		            idUtente=AllQuery.getInstance().getVerifiedEmail(email);
		            dao.setData(id,idUtente);
		            EmailSenderController s=new EmailSenderController();
		            String mex="il suo Username: "+u.getUsername()+"\n la susa Password: "+u.getPassword();
		            s.sendMessage(email,mex, "nuovo account travelbook");
		            UserBean user1=signIn(u.getUsername(),u.getPassword());
		            user1.setFirstTime(true);
		            return user1;
				}
				else {
					PersistanceDAO dao1=DaoFactory.getInstance().create(DaoType.USER);
					List<Entity>l=dao1.getData(user);
					return new UserBean((UserEntity) l.get(0));
				}
            }
		}catch (LoginPageException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new LoginPageException("errore pagina di Login");
		}
		return null;
	}
	private String passwordHash(String pswd) throws GeneralSecurityException{
		MessageDigest hasher=MessageDigest.getInstance("SHA-256");
		hasher.update(pswd.getBytes(StandardCharsets.UTF_8));
		return toHex(hasher.digest());
	}
	private String toHex(byte[] data)  {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			String digit = Integer.toString(b & 0xFF, 16);
 
			if (digit.length() == 1) {
				sb.append("0");
			}
			sb.append(digit);
		}
		return sb.toString();
	}
	public UserBean signIn(String username,String password) throws LoginPageException{
		try {
			UserBean user=null;
			PersistanceDAO userDao=DaoFactory.getInstance().create(DaoType.USER);
			UserEntity userE=new UserEntity();
			userE.setUsername(username);
			userE.setPassword(this.passwordHash(password)); 
			List<Entity> list = userDao.getData(userE);
			user=new UserBean((UserEntity) list.get(0));
			return user;
		}catch(LoginPageException e) {
			throw e;
		}
		catch(Exception e) {
			throw new LoginPageException(e.getMessage());
		}
	}
	
	private int numberGenerator(){
		int x=0;
		for(int i=0;i<=5;i++)
		{
			int rand =  new SecureRandom().nextInt(9);
			x=(int) (x+rand*Math.pow(10, i));
		}
		return x;
	}
	
	public String calcoloRegistration(String email) {
		EmailSenderController s=new EmailSenderController();
		String code= Integer.toString(numberGenerator());
		if(code.length()<6)
			{
				int j=6-code.length();
				for(int i=0;i<j;i++) code="0".concat(code);
			}
		try {
			s.sendMessage(email,code, "Codice Registrazione TravelBoook");
		} catch (MessagingException e) {
			Platform.runLater(()->{
				new TriggerAlert().triggerAlertCreate("Send failed, try asking for a new code","warn").showAndWait();
			});
		}
		return code;
	}
	
	public void signUp(RegistrationBean user) throws DBException{
		PersistanceDAO userDao= DaoFactory.getInstance().create(DaoType.USER);
		UserEntity newUser= new UserEntity(user);
		try {
			newUser.setPassword(this.passwordHash(user.getPassword()));
		}catch(Exception e) {
			e.getStackTrace();
		}
		userDao.setMyEntity(newUser);
		userDao.setData();
	}

}