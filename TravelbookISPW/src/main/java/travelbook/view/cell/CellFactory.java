package main.java.travelbook.view.cell;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class CellFactory {
	private static CellFactory instance=new CellFactory();
	public static CellFactory getInstance() {
		return instance;
	}
	public Cell create(CellType type,AnchorPane anchor,BorderPane pane) {
		Cell ret=null;
		if(type==CellType.FOLLOWER) {
			ret=new UserCell(anchor,pane);
		}
		if(type==CellType.TRAVEL) {
			ret=new TravelCell(anchor,pane);
		}
		if(type==CellType.FAVORITE) {
			ret=new FavoriteCell(anchor,pane);
		}
		if(type==CellType.SHARE) {
			ret=new SharedCell(anchor,pane);
		}
		if(type==CellType.SEARCH) {
			ret=new TravelCell(anchor,pane,false,835,2);
		}
		if(type==CellType.OTHERTRAVEL) {
			ret=new TravelCell(anchor,pane,true,530,3);
		}
		if(type==CellType.SHAREABLE) {
			ret=new ShareableContactCell(anchor,pane);
		}
		return ret;
	}
}
