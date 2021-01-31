package main.java.travelbook.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class SetImage {
	private Pane myPane = new Pane();
	private boolean travel;
	private static final String PROFILE_CSS = "profile-pic";
	private static final String TRAVEL_CSS= "travel-pic";
	private static final String DEFAULT_IMG ="src/main/resources/ProfilePageImages/travelers.png";
	private static final String DEFAULT_IMG_COLOR="rgb(255, 162, 134)"; 
	
	public SetImage(Pane myPane, Image myPhoto, boolean travel) {
		this.myPane=myPane;
		this.travel=travel;
		set(myPhoto);
	}
	
	private void set(Image photo) {
		if(travel)
			myPane.getStyleClass().add(TRAVEL_CSS);
		else
			myPane.getStyleClass().add(PROFILE_CSS);
		Background bg;
		try {
			
			BackgroundImage bgpic = new BackgroundImage(photo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
			bg = new Background(bgpic);
		}catch(NullPointerException | IllegalArgumentException e) {
			URL url=null;
			if(travel) 
				bg = colorBackground();
			else {
				try {
					url = new File(DEFAULT_IMG).toURI().toURL();
					if(url!=null) photo = new Image(url.toString());
					BackgroundImage bgpic = new BackgroundImage(photo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, true));
					bg = new Background(bgpic);
				} catch (MalformedURLException | NullPointerException e1) {
					bg = colorBackground();
				}
			}
		}
		myPane.setBackground(bg);
	}
	private Background colorBackground() {
		BackgroundFill fill = new BackgroundFill(Paint.valueOf(DEFAULT_IMG_COLOR), null, null);
		return new Background(fill);
	}
}
