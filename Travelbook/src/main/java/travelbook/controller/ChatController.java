package main.java.travelbook.controller;
import java.util.List;

import exception.DBException;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;

import main.java.travelbook.model.dao.DaoFactory;
import main.java.travelbook.model.dao.DaoType;
import main.java.travelbook.model.dao.PersistanceDAO;
import main.java.travelbook.model.dao.PredictableDAO;
import main.java.travelbook.model.dao.VisualDAO;
import main.java.travelbook.util.Chat;
import main.java.travelbook.util.DateComparator;
import main.java.travelbook.util.DateUtil;
import main.java.travelbook.view.MenuBar;
import main.java.travelbook.model.MessageEntity;
import main.java.travelbook.model.UserEntity;
import main.java.travelbook.model.bean.MessageBean;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.model.CityEntity;
import main.java.travelbook.model.Entity;

public class ChatController {
	
	
	public List<MessageBean> getMessages(List<MessageBean> msgR, List<MessageBean> msgS){
		List<MessageBean> msg = new ArrayList<>(msgR);
		msg.addAll(msgS);
		msg.sort(new DateComparator());
		return msg;
	}
	public List<UserBean> getContacts(List<Chat> c) throws DBException{
		VisualDAO cDao = DaoFactory.getInstance().createVisual(DaoType.S_USER);
		List<UserBean> ul = new ArrayList<>();
		for(Chat i: c) {
			UserEntity userE = new UserEntity(i.getIdUser());
			System.out.println(userE.getId());
			userE = (UserEntity)cDao.getData(userE).get(0);
			if(userE.getId()!=MenuBar.getInstance().getLoggedUser().getId()) {
				UserBean u = new UserBean(userE.getId());
				u.setName(userE.getName());
				u.setSurname(userE.getSurname());
				u.setPhoto(userE.getPhoto());
				ul.add(u);
			}
		}
		return ul;
	}
	public List<MessageEntity> getNewMessage( int idUser, Instant time) throws Exception {
		PersistanceDAO dao=DaoFactory.getInstance().create(DaoType.MESSAGE);
		MessageEntity nuovaEntity=new MessageEntity(0,idUser);
		if(time!=null)
			nuovaEntity.setLastTimeStamp(time);
		nuovaEntity.setSoloNuovi(true);
		List<Entity> entities=dao.getData(nuovaEntity);
		List<MessageEntity> messaggi=new ArrayList<>();
		for(Entity entity: entities) {
			MessageEntity messaggio=(MessageEntity)entity;
			messaggi.add(messaggio);
		}
		return messaggi;
	}
	public void setReadMex(MessageEntity mex) {
		PersistanceDAO dao=DaoFactory.getInstance().create(DaoType.MESSAGE);
		try {
			dao.update(mex);
		}  catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<UserBean> getUserPredictions(String text) {
		PredictableDAO dao= DaoFactory.getInstance().createPredictable(DaoType.USER);
		List<UserBean> results=new ArrayList<>();
		List<Entity> predictions=dao.getPredictions(text);
		UserBean singleResult;
		for(Entity user: predictions) {
			UserEntity entity=(UserEntity) user;
			singleResult=new UserBean(entity);
			results.add(singleResult);
		}
		return results;
	}
	public void sendMessage(MessageBean m) throws DBException {
		PersistanceDAO msgDao = DaoFactory.getInstance().create(DaoType.MESSAGE);
		MessageEntity myEntity = new MessageEntity(m.getIdMittente(),m.getIdDestinatario());
		myEntity.setText(m.getText());
		myEntity.setRead(false);
		myEntity.setTime(m.getTime());
		msgDao.setMyEntity(myEntity);
		msgDao.setData();
	}
}
