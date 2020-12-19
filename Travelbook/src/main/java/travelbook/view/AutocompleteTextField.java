package main.java.travelbook.view;
import java.util.LinkedList;
import main.java.travelbook.util.PlacePrediction;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.scene.control.TextField;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import main.java.travelbook.controller.PredictionController;
import java.util.List;
import java.util.ArrayList;
/*This class define an autocompleteTextField
 * Sostituire S con un qualsiasi tipo di dato
 * Il textField conterrà sempre una stringa 
 * S rappresenta il tipo di dato memorizzato nel contextmenu
 */
public class AutocompleteTextField extends TextField{
		private final ObjectProperty<PlacePrediction> lastSelectedItem=new SimpleObjectProperty<>();
		//The entries of the popUp
		private final List<PlacePrediction> entries;
		private boolean enable=true;
		private boolean blocked=true;
		private ObservableList<PlacePrediction> filteredEntries=FXCollections.observableArrayList();
		private ContextMenu entriesPopup;
		private boolean popupHidden=false;
		//the number of max entries showed in popup
		private int maxEntries=10;
		public AutocompleteTextField() {
			super();
			this.entries=new ArrayList<PlacePrediction>();
			this.filteredEntries.addAll(this.entries);
			//At the start all of the entries are considered filtered
			entriesPopup=new ContextMenu();
			textProperty().addListener((observableValue, oldValue,  newValue)->{
				if(blocked) {
					this.setText("");
				}
				else {
				if(getText()==null||getText().length()==0) {
					//il testo è stato svuotato
					filteredEntries.clear();
					filteredEntries.addAll(this.entries);
					entries.clear();
				}
				else {
					this.setText(this.getText().substring(0,1).toUpperCase()+this.getText().substring(1));
					if(enable) {
					new Thread(()->{
						PredictionController controller=new PredictionController();
						List<PlacePrediction> predictions=(List<PlacePrediction>)controller.getPredictions(getText());
						Platform.runLater(()->{
							this.entries.clear();
						for(PlacePrediction predict: predictions) {
							entries.add(predict);
							System.out.println("Ho aggiunto: "+predict.toString());
						}
						});
					}).start();
					LinkedList<PlacePrediction> searchResult= new LinkedList<>();
					String text1=getText();
					System.out.println(text1);
					Pattern pattern;
					for(int i=0;i<this.entries.size();i++) {
						PlacePrediction pred= this.entries.get(i);
						System.out.println(pred.toString());
					}
					pattern=Pattern.compile(".*"+text1+".*",Pattern.CASE_INSENSITIVE);
					for(PlacePrediction entry: this.entries) {
						Matcher matcher=pattern.matcher(entry.toString());
						if(matcher.matches()) {
							searchResult.add(entry);
						}
					}
					if(!this.entries.isEmpty()) {
						filteredEntries.clear();
						filteredEntries.addAll(searchResult);
						if(!popupHidden) {
							populate(searchResult,text1);
							if(!entriesPopup.isShowing()) {
								entriesPopup.show(AutocompleteTextField.this,Side.BOTTOM,0,0);
								
							}
						}
					}
					else {
						//togli il popup
						entriesPopup.hide();
					}
				}}
				}});
			this.focusedProperty().addListener((observable,oldValue,newValue)->{
				if(this.isFocused()) {
				    this.enable=true;
				}
				else {
					this.enable=false;
					this.entries.clear();
				}
			});
			
		}
		public void block() {
			this.blocked=true;
		}
		public void unblock() {
			this.blocked=false;
		}
		private void populate(LinkedList<PlacePrediction> searchResult, String text1) {
			LinkedList<CustomMenuItem> menuItems= new LinkedList<>();
			int count=Math.min(searchResult.size(),maxEntries);
			for(int i=0;i<count;i++) {
				final String result=searchResult.get(i).toString();
				final PlacePrediction object=searchResult.get(i);
				int occurence;
				
				occurence=result.toLowerCase().indexOf(text1.toLowerCase());
				
				if(occurence<0)
					continue;
				
				Text pre= new Text(result.substring(0,occurence));
				Text occ=new Text(result.substring(occurence,occurence + text1.length()));
				occ.setStyle("-fx-font-weight: bold;"
						+ "-fx-fill: rgb(66,139,202);");
				
				Text post=new Text(result.substring(occurence+text1.length()));
				TextFlow entryFlow= new TextFlow(pre,occ,post);
				CustomMenuItem item=new CustomMenuItem(entryFlow,true);
				item.setOnAction((ActionEvent e)->{
					lastSelectedItem.set(object);
					this.setText(lastSelectedItem.get().toString());
					searchResult.clear();
					entriesPopup.hide();
				});
				menuItems.add(item);
			}
			entriesPopup.getItems().clear();
			entriesPopup.getItems().addAll(menuItems);
		}
		public ObservableList<PlacePrediction> getFilteredEntries() {
			return filteredEntries;
		}
		public void setFilteredEntries(ObservableList<PlacePrediction> filteredEntries) {
			this.filteredEntries = filteredEntries;
		}
		public boolean isPopupHidden() {
			return popupHidden;
		}
		public void setPopupHidden(boolean popupHidden) {
			this.popupHidden = popupHidden;
		}
		public int getMaxEntries() {
			return maxEntries;
		}
		public void setMaxEntries(int maxEntries) {
			this.maxEntries = maxEntries;
		}
		public ObjectProperty<PlacePrediction> getLastSelectedItem() {
			return lastSelectedItem;
		}
		public List<PlacePrediction> getEntries() {
			return entries;
		}
		
}
