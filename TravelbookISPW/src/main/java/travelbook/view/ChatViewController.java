package main.java.travelbook.view;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import main.java.travelbook.util.Observer;
import main.java.travelbook.view.autocomplete.SearchUserTextField;
import main.java.travelbook.view.cell.ChatContactView;
import main.java.travelbook.view.cell.MessageCell;
import main.java.travelbook.view.cell.ChatContactView.MyItem;
import main.java.travelbook.util.Observable;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import main.java.exception.DBException;
import main.java.exception.MissingPageException;
import main.java.exception.TriggerAlert;
import main.java.travelbook.controller.ChatController;
import main.java.travelbook.model.bean.MessageBean;
import main.java.travelbook.model.bean.UserBean;
import main.java.travelbook.util.Chat;


public class ChatViewController implements Observer{
	private Object[] array1=new Object[15];
	private Button button;
	private BorderPane mainPane;
	@FXML
	private Button send;
	@FXML
	private TextArea write;
	@FXML
	private AnchorPane mainAnchor;
	@FXML
	private AnchorPane chatAnchor;
	@FXML
	private StackPane writeBar;
	private MessageCell sentList;
	private ChatContactView contactList;
	@FXML
	private ButtonBar menuBar;
	@FXML
	private StackPane search;
	@FXML
	private Button searchButton;
	@FXML
	private TextField searchField;
	private UserBean searchedUser;
	private List<Chat> myChats = MenuBar.getInstance().getMyChat();
	private ChatController myController = new ChatController();
	private SearchUserTextField searchFieldAuto;
	private Chat current = null;
	private List<MyItem> contacts;
	private String changed = "changed";

	
	public void initialize() {
	this.contactList=new ChatContactView(this.mainAnchor);
	MenuBar.getInstance().addObserver(this);

	sentList=new MessageCell(this.chatAnchor,this.mainAnchor,this.mainPane);
	searchFieldAuto = new SearchUserTextField(searchField);
	searchFieldAuto.getLastSelectedItem().addListener((observable,oldValue,newValue)->{
		if(searchFieldAuto.getLastSelectedItem().get()!=null) 
			searchedUser = searchFieldAuto.getLastSelectedItem().get();
	});
	 List<UserBean> tryContacts;
	try {
		tryContacts = myController.getContacts(myChats,MenuBar.getInstance().getLoggedUser().getId());
		List<MyItem> contacts1=new ArrayList<>();
	 for(UserBean u: tryContacts) {
		 contacts1.add(contactList.new MyItem(u));
		 
	 }
	contactList.setItems(contacts1);
	 contacts=contactList.getItems();
		if(MenuBar.getInstance().getNotified())
			this.update(MenuBar.getInstance());
		MenuBar.getInstance().setNotified();
	 for(Chat c:myChats)
		 if(c.isChanged())
			 notified(c);
    

	
	} catch (DBException e) {
		new TriggerAlert().triggerAlertCreate(e.getLocalizedMessage(), "warn").showAndWait();
	}
	contactList.getSelectedItem().addListener((observable, oldValue, newValue) -> { 
		
			MyItem user = contactList.getSelectedItem().getValue();
            select(user);
			changeChat();
		
    });

	 
	 
	
	}
	
	private void select(MyItem user) {
		user.setSpecialIndicator("selected");
		for(MyItem u: contactList.getItems()) {
			if(u!=user && !(u.getSpecialIndicator().equalsIgnoreCase(changed)))
				u.setSpecialIndicator("");
		}
		contactList.refresh();
		int i = 0;
		current = null;
		while(i<myChats.size() && current == null) {
			if(myChats.get(i).getIdUser()==user.getUser().getId())
				current = myChats.get(i);
			i++;
		}
		if(current == null) {
			current = new Chat(user.getUser().getId());
			myChats.add(current);
		}
	}



	
	private void changeChat() {
		List<MessageBean>  myMessages;
		if(current.getReceive()!=null) {
			for(MessageBean m:current.getReceive()) {
				if(!m.getRead()) {
					m.setRead(true);
					try {
						myController.setReadMex(m);
					} catch (DBException e) {
						new TriggerAlert().triggerAlertCreate(e.getMessage(), "warn").showAndWait();
					}
					current.setRead();
				}
			}
		}
		myMessages = myController.getMessages(current.getReceive(), current.getSend());
		if(myMessages!=null) {
			ObservableList<MessageBean> data = FXCollections.observableArrayList(myMessages);
			sentList.setItems(data); 
			sentList.getScroll().setVvalue(1);
		}
	}
	public void setMainPane(BorderPane main) {
		this.mainPane=main;
		//then define the resize logic
		this.mainPane.getScene().getWindow().heightProperty().addListener((observable,oldValue,newValue)->
			mainPane.setPrefHeight(this.mainPane.getScene().getWindow().getHeight()));
		this.mainPane.getScene().getWindow().widthProperty().addListener((observable,oldValue,newValue)->
			mainPane.setPrefWidth(mainPane.getScene().getWindow().getWidth())); 
		this.mainPane.heightProperty().addListener((observable,oldValue,newValue)->{
			StackPane title=(StackPane)mainPane.getTop();
			title.setPrefHeight(mainPane.getHeight()*94/720);
			mainAnchor.setPrefHeight(mainPane.getHeight()*625/720);
			
		});
		this.mainPane.widthProperty().addListener((observable,oldValue,newValue)->
			mainAnchor.setPrefWidth(mainPane.getWidth())
		);
		this.mainAnchor.heightProperty().addListener((observable,oldValue,newValue)->{
	
			chatAnchor.setPrefHeight(mainAnchor.getPrefHeight()*517/625);
			chatAnchor.setLayoutY(mainAnchor.getPrefHeight()*96/625);
			sentList.getScroll().setPrefHeight(mainAnchor.getPrefHeight()*433/625);
			sentList.resize();
			writeBar.setPrefHeight(mainAnchor.getPrefHeight()*85/625);
			writeBar.setLayoutY(mainAnchor.getPrefHeight()*432/625);
			write.setPrefHeight(mainAnchor.getPrefHeight()*70/625);
			send.setPrefHeight(mainAnchor.getHeight()*30/625);
			contactList.getScroll().setPrefHeight(mainAnchor.getHeight()*450/625);
			contactList.getScroll().setLayoutY(mainAnchor.getHeight()*107/625);
			search.setPrefHeight(mainAnchor.getHeight()*50/625);
			search.setLayoutY(mainAnchor.getHeight()*557/625);
			searchButton.setPrefHeight(mainAnchor.getHeight()*40/625);
			searchField.setPrefHeight(mainAnchor.getHeight()*40/625);
			menuBar.setPrefHeight(mainAnchor.getHeight()*85/625);
			menuBar.setLayoutY(0);
			array1=menuBar.getButtons().toArray();
			for(int i=0;i<4;i++) {
				button=(Button)array1[i];
				button.setPrefHeight(mainAnchor.getHeight()*56/625);
			}
		});	
		this.mainAnchor.widthProperty().addListener((observable,oldValue,newValue)->{
			chatAnchor.setPrefWidth(mainAnchor.getPrefWidth()*904/1280);
			chatAnchor.setLayoutX(mainAnchor.getPrefWidth()*350/1280);
			sentList.getScroll().setPrefWidth(mainAnchor.getPrefWidth()*904/1280);
			
			writeBar.setPrefWidth(mainAnchor.getPrefWidth()*904/1280);
			write.setPrefWidth(mainAnchor.getPrefWidth()*750/1280);
			send.setPrefWidth(mainAnchor.getWidth()*40/1280);
			contactList.getScroll().setPrefWidth(mainAnchor.getWidth()*300/1280);
			contactList.getScroll().setLayoutX(mainAnchor.getWidth()*33/1280);
			search.setPrefWidth(mainAnchor.getWidth()*300/1280);
			search.setLayoutX(mainAnchor.getWidth()*33/1280);
			searchButton.setPrefWidth(mainAnchor.getWidth()*40/1280);
			searchField.setPrefWidth(mainAnchor.getWidth()*250/1280);
			menuBar.setPrefWidth(mainAnchor.getWidth()*592/1280);
			menuBar.setLayoutX(0);
			array1=menuBar.getButtons().toArray();
			for(int i=0;i<4;i++) {
				button=(Button)array1[i];
				button.setPrefWidth(mainAnchor.getWidth()*147/1280);
			}
		});
		
		
		this.mainAnchor.setPrefHeight(mainPane.getHeight()*625/720);
		this.mainAnchor.setPrefWidth(mainPane.getWidth());
		
		write.setOnKeyPressed(this::keyPressed);
	}
	private void keyPressed(KeyEvent evt) {
		//per qualche motivo va anche a capo dopo aver inviato, risolvere
		KeyCode ch = evt.getCode();
		if(ch.equals(KeyCode.ENTER)){
			sendHandler();
		}
	}
	
	@FXML
    private void profileHandler(){
    	try {
    	MenuBar.getInstance().deleteObserver(this);
    	MenuBar.getInstance().moveToProfile(mainPane);
    	}catch(MissingPageException e) {
    		e.exit();
    	}
    }
    @FXML
    private void exploreHandler() {
    	try {
    		MenuBar.getInstance().deleteObserver(this);
    		MenuBar.getInstance().moveToExplore(mainPane);
    	}catch(MissingPageException e) {
    		e.exit();
    	}
    }
    @FXML
    private void addHandler() {
    	try {
    		MenuBar.getInstance().moveToAdd(mainPane);
    		MenuBar.getInstance().deleteObserver(this);
    	}catch(MissingPageException e) {
    		e.exit();
    	}
    }

    @FXML
    private void sendHandler() {
    	try {
	    	MessageBean newMsg = new MessageBean(current.getIdUser(), MenuBar.getInstance().getLoggedUser().getId());
	    	newMsg.setText(write.getText());
	    	newMsg.setTime(Instant.now());
	    	newMsg.setRead(false);
	    	sentList.getObservableItems().add(newMsg);
	    	current.getSend().add(newMsg);
			myController.sendMessage(newMsg);
			write.clear();
	    	sentList.getScroll().setVvalue(1);
    	} catch (DBException e) {
			new TriggerAlert().triggerAlertCreate(e.getMessage(), "warn").showAndWait();
		}catch(NullPointerException e){
    		new TriggerAlert().triggerAlertCreate("Select a Contact before sending any message", "err").showAndWait();
    	}
    }
  @FXML
    private void searchHandler() {
	  	  try{
	  		  	if(searchFieldAuto.getTextField().getText()!= null) {
		
		  		MyItem i = contactList.new MyItem(searchedUser);
		  
			  	searchFieldAuto.getTextField().setText(null);
			  	int j=0;
			  	boolean found = false;
			  	while(j<contactList.getItems().size() && !found) {
			  		if(contactList.getItems().get(j).getUser().getId()==i.getUser().getId()) {
			  			contactList.getSelectedItem().set(contactList.getItems().get(j));
			  			
			  			found = true;
			  		}
			  		j++;
			  	}
			  	if(!found) {
			  		contactList.getItems().add(i);
			  		contactList.setItems(contactList.getItems());
			  		contactList.getSelectedItem().set(i);
			  		
			  	}	
		  	}
	  	}catch(NullPointerException e) {
	  		new TriggerAlert().triggerAlertCreate("Select a valid User from Autocomplete", "warn").showAndWait();
	  	}
    	
    }

@Override
public void update(Observable bar, Object notify) {
	boolean value=(Boolean)notify;
	if(value) {
		Platform.runLater(()->{
			for(Chat c: myChats) {
				if(c.isChanged()) {
					notified(c);
					if(current == c){
						this.sentList.getObservableItems().add(c.getReceive().get(c.getReceive().size()-1));
						MenuBar.getInstance().setNotified();
					}
				}
			}
		});
		
	}
	
}
public void notified(Chat c) {

	for(MyItem u: contacts) {
		if(u.getUser().getId()==c.getIdUser()) 
			u.setSpecialIndicator("changed");
	}
}

@Override
public void update(Observable bar) {
	this.update(bar,true);
	
}


}
