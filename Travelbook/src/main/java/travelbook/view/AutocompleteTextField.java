package main.java.travelbook.view;
import java.util.LinkedList;
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
import java.util.List;
import java.util.ArrayList;
/*This class define an autocompleteTextField
 * Sostituire S con un qualsiasi tipo di dato
 * Il textField conterra' sempre una stringa 
 * S rappresenta il tipo di dato memorizzato nel contextmenu
 * ad esempio nella autocomplete per i posti ho sostituito S con la classe PlacePrediction
 */
public abstract class AutocompleteTextField<S>{
		//The text field that we modify with an autocomplete feature
		private Side pos;
		private final TextField textField;
		private  boolean blocked;
		private final ObjectProperty<S> lastSelectedItem=new SimpleObjectProperty<>();
		//The entries of the popUp
		private final List<S> entries;
		private int characterLowerBound;
		private boolean enable=true;
		private ObservableList<S> filteredEntries=FXCollections.observableArrayList();
		private ContextMenu entriesPopup;
		private boolean popupHidden=false;
		//the number of max entries showed in popup
		private int maxEntries=10;
		protected AutocompleteTextField() {
			this(new TextField());
		}
		protected AutocompleteTextField(TextField textField) {
			this.textField=textField;
			this.entries=new ArrayList<>();
			this.filteredEntries.addAll(this.entries);
			//At the start all of the entries are considered filtered
			entriesPopup=new ContextMenu();
			this.textField.textProperty().addListener((observableValue, oldValue,  newValue)->{
				if(this.textField.getText()==null||this.textField.getText().length()==0) {
					filteredEntries.clear();
					filteredEntries.addAll(this.entries);
					entriesPopup.hide();
					entries.clear();
				}
				else {
					if(this.blocked)
						return;
					this.textField.setText(this.textField.getText().substring(0,1).toUpperCase()+this.textField.getText().substring(1));
					if(enable && this.textField.getText().length()>characterLowerBound) {
					new Thread(()->{
						List<S> predictions=getPredictions(this.textField.getText());
						Platform.runLater(()->
							this.setUpPopUp(predictions)
							);
					}).start();
					
				}
				}
				});
			this.textField.focusedProperty().addListener((observable,oldValue,newValue)->{
				if(this.textField.isFocused()) {
				    this.enable=true;
					
				}
				else {
					this.enable=false;
					this.entries.clear();
					this.entriesPopup.hide();
				}
			});
			
		}
		protected final void setUpPopUp(List<S> predictions) {
			this.entries.clear();
			for(S predict: predictions) {
				entries.add(predict);
			}
				LinkedList<S> searchResult= new LinkedList<>();
				String text1=this.textField.getText();
				
				Pattern pattern;
				
				pattern=Pattern.compile(".*"+text1+".*",Pattern.CASE_INSENSITIVE);
				for(S entry: this.entries) {
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
							entriesPopup.show(this.textField,pos,0,0);
							
						}
					}
				}
				else {
					//togli il popup
					entriesPopup.hide();
				}
		}

		//Definisci qui la logica di recupero delle predictions. La classe 
		//si aspetta di prendere le prediction relative ad un text input da qualche parte
		//il metodo deve ritornare una List<S> dove S e' il tipo di dato scelto per memorizzare le prediction
		//text e' il dato che devi cercare
		//Se il metodo ritorna null non garantisco il risultato.
		protected abstract List<S> getPredictions(String text);
		
		private void populate(LinkedList<S> searchResult, String text1) {
			LinkedList<CustomMenuItem> menuItems= new LinkedList<>();
			int count=Math.min(searchResult.size(),maxEntries);
			for(int i=0;i<count;i++) {
				final String result=searchResult.get(i).toString();
				final S object=searchResult.get(i);
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
					//disable autocomplete search
					//evito di effettuare una nuova chiamata al controller delle prediction.
					this.enable=false;
					this.textField.setText(lastSelectedItem.get().toString());
					searchResult.clear();
					entriesPopup.hide();
				});
				menuItems.add(item);
			}
			entriesPopup.getItems().clear();
			entriesPopup.getItems().addAll(menuItems);
		}
		public ObservableList<S> getFilteredEntries() {
			return filteredEntries;
		}
		public void setFilteredEntries(ObservableList<S> filteredEntries) {
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
		public ObjectProperty<S> getLastSelectedItem() {
			return lastSelectedItem;
		}
		public List<S> getEntries() {
			return entries;
		}
		protected int getCharacterLowerBound() {
			return this.characterLowerBound;
		}
		protected void setCharacterLowerBound(int lowerBound) {
			this.characterLowerBound=lowerBound;
		}
		protected TextField getTextField() {
			return this.textField;
		}
		public boolean isBlocked() {
			return blocked;
		}
		public void setBlocked(boolean val) {
			this.blocked=val;
		}
		protected void setPos(Side pos) {
			this.pos=pos;
		}
}
