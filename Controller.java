//Itamar Levi, Young Choi
package SongLib;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.*;

import javafx.scene.control.Alert.AlertType;
//import javafx.beans.value.ChangeListener;
//import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*; 

public class Controller {

    @FXML
    ListView<String> listView;
    
    private ObservableList<String> obsList;
    
    public Label songName = new Label();
    public Label artistName = new Label();
    public Label albumName = new Label();
    public Label songYear = new Label();
    public Button editButton = new Button();
    public TextField newSongName = new TextField();
    public TextField newArtistName = new TextField();
    public TextField newAlbumName = new TextField();
    public TextField newYearDate = new TextField();
    public Button saveSong = new Button();
    public Button deleteButton = new Button();
    public Button cancelButton = new Button();
    public Button addButton = new Button();
    //public Button saveNewSong = new Button();
    
  
    public static Object obj;
    JSONArray songList;
    JSONObject selectedSong =new JSONObject();
    
    public void initialize(Stage primaryStage) throws Exception{
    	

    	//get object from document
        obj = new JSONParser().parse(new FileReader("songs.json")); 
        
        //cast object as a json object
        JSONObject document= (JSONObject) obj;
        
        //get json array from object.
        songList = (JSONArray) document.get("songList");
        
        //iterator goes through the list. 
        Iterator songIterator=songList.iterator();
        
        ArrayList<String> songArrList = new ArrayList<String>();
        
        while(songIterator.hasNext()) {
        	JSONObject song = (JSONObject) songIterator.next();
        	songArrList.add((String) song.get("name") + " - " + song.get("artist"));
        }
        
        //Collections.sort(songArrList);

        
    	obsList=FXCollections.observableArrayList(songArrList);

    	listView.setItems(obsList);
    	
    	//listView.getSelectionModel().select(0);
    	
    		
		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> showItem(primaryStage)); 
		listView.getSelectionModel().select(0);
	    	
    	
    	
    	
    	editButton.setOnAction(this::editSongDetails);
    	deleteButton.setOnAction(this::deleteSong);
    	
    	addButton.setOnAction(this::addSong);
    	
    	
    
    }
    

    private void showItem(Stage primaryStage) {
    	cancelButton.setVisible(false);
    	songName.setVisible(true);
    	artistName.setVisible(true);
    	albumName.setVisible(true);
    	songYear.setVisible(true);
    	listView.setItems(obsList);
    	System.out.println(listView.getItems().size());
    	if(listView.getItems().size() == 0) { System.out.println("returning"); return; }
    	//if(listView.getItems().size() == 1) { System.out.println("one song"); listView.getSelectionModel().select(0);}
    	String content[] = new String[2];
    	
    		
    	
    	try {
    	content = listView.getSelectionModel().getSelectedItem().split(" - ");
    	}catch(NullPointerException f) {
    	}    	
    	System.out.println("Artist: " + content[1]);
    	System.out.println("Name: " + content[0]);
    	
    	//if(listView.getSelectionModel().getSelectedIndex() == 0) selectedSong = (JSONObject) songList.get(0);
    	
    	//iterate through JSON Array to find the song. Then set selected Song to the object.
    	
		for(Object songObject : songList) {
			
    		JSONObject song= (JSONObject) songObject;
    		//if(songList.size() == 1) selectedSong = song;
    		if(song.get("name").equals(content[0]) && song.get("artist").equals(content[1])) {
    			selectedSong=song;
    		}
		}
    	
    	
    	songName.setText((String)selectedSong.get("name"));
    	artistName.setText((String)selectedSong.get("artist"));
    	albumName.setText((String)selectedSong.get("album"));
    	songYear.setText((String)selectedSong.get("year"));

    	newSongName.setVisible(false);
		newAlbumName.setVisible(false);
		newArtistName.setVisible(false);
		newYearDate.setVisible(false);
		
		saveSong.setVisible(false);
		
		

    }
    
    
    
    
    public void addSong(ActionEvent event) {

    	songName.setVisible(false);
    	artistName.setVisible(false);
    	albumName.setVisible(false);
    	songYear.setVisible(false);
    	
    	newSongName.clear();
    	newArtistName.clear();
    	newAlbumName.clear();
    	newYearDate.clear();
    	
    	
    	newSongName.setVisible(true);
    	newArtistName.setVisible(true);
    	newAlbumName.setVisible(true);
    	newYearDate.setVisible(true);
    	
    	
    	saveSong.setVisible(true);
    	cancelButton.setVisible(true);
    	
    	
    	
    	saveSong.setOnAction(new EventHandler<ActionEvent>(){
    		
			@SuppressWarnings("unchecked")
			public void handle(ActionEvent e) {
    			if(newSongName.getText().isEmpty() || newArtistName.getText().isEmpty()) {
    				Alert alert = new Alert(AlertType.INFORMATION);
    				alert.initOwner(Main.primaryStage);
    				alert.setTitle("Empty Song/Artist");
    				alert.setHeaderText("You cannot leave a song or artist name empty. Please input a name for the song/artist.");
    				alert.showAndWait();
    				addSong(event);
    				return;
    			}
    			
    	        Iterator songIterator=songList.iterator();
    	        
    	        
    	        while(songIterator.hasNext()) {
    	        	JSONObject song = (JSONObject) songIterator.next();
    	        	if(song.get("name").equals(newSongName.getText())) {
    	        		if(song.get("artist").equals(newArtistName.getText())) {
    	    				Alert alert = new Alert(AlertType.INFORMATION);
    	    				alert.initOwner(Main.primaryStage);
    	    				alert.setTitle("Duplicate Song");
    	    				alert.setHeaderText("You cannot add a duplicate song. Please try again");
    	    				alert.showAndWait();
    	    				addSong(event);
    	    				return;
    	        		}
    	        	}
    	        }
    	        
    	        
    			Alert confirmation = new Alert(AlertType.CONFIRMATION);
    			confirmation.initOwner(Main.primaryStage);
    			confirmation.setTitle("Confirm?");
    			confirmation.setHeaderText("Are you sure you want to add this song");
    			
    			ButtonType confirmEditSong = new ButtonType("Save Song");
    			ButtonType confirmCancelEdit = new ButtonType("Cancel");
    			confirmation.getButtonTypes().setAll(confirmEditSong, confirmCancelEdit);
    			
    			Optional<ButtonType> result = confirmation.showAndWait();
    			
    			if(result.get() == confirmCancelEdit) {
    				
    				songName.setVisible(true);
    				artistName.setVisible(true);
    				albumName.setVisible(true);
    				songYear.setVisible(true);

    				
    				newSongName.setVisible(false);
        			newArtistName.setVisible(false);
        			newAlbumName.setVisible(false);
        			newYearDate.setVisible(false);
        			saveSong.setVisible(false);
        			cancelButton.setVisible(false);
        			return;
    				
    			}
    			

    			
    			obsList.add(newSongName.getText() + " - " + newArtistName.getText());
    			
    			JSONObject newSong= new JSONObject();
    			
    			newSong.put("name",newSongName.getText());
    			newSong.put("artist",newArtistName.getText());
    			newSong.put("album",newAlbumName.getText());
    			newSong.put("year",newYearDate.getText());
    			
    			songList.add(newSong);
    			
    			//Collections.sort(obsList);
    			
    			
				int index = listView.getItems().indexOf(newSongName.getText() + " - " + newArtistName.getText());
				System.out.println("index: " + index);
				listView.getSelectionModel().select(index);
    			if(listView.getItems().size() == 1) {
    				showItem(Main.primaryStage);
    			}
			
    	    	
    			
    			newSongName.setVisible(false);
    			newAlbumName.setVisible(false);
    			newArtistName.setVisible(false);
    			newYearDate.setVisible(false);
    			
    			songName.setVisible(true);
				artistName.setVisible(true);
				albumName.setVisible(true);
				songYear.setVisible(true);
				
				newSongName.clear();
		    	newArtistName.clear();
		    	newAlbumName.clear();
		    	newYearDate.clear();
    			
    			
    			
    			saveSong.setVisible(false);
    			cancelButton.setVisible(false);
    			
    		}
    	});
    	
    	cancelButton.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent e) {
    			newSongName.setVisible(false);
    			newArtistName.setVisible(false);
    			newAlbumName.setVisible(false);
    			newYearDate.setVisible(false);
    			
    			songName.setVisible(true);
				artistName.setVisible(true);
				albumName.setVisible(true);
				songYear.setVisible(true);
    			
    			saveSong.setVisible(false);
    			cancelButton.setVisible(false);
    		}
    	});
    	
    	

    	
    	
    }
    
    
    
    
    public void editSongDetails(ActionEvent event){
    	if(listView.getItems().size() == 0) {
    		Alert noSongAlert = new Alert(AlertType.INFORMATION);
    		noSongAlert.initOwner(Main.primaryStage);
    		noSongAlert.setTitle("Unable to Perform Function");
    		noSongAlert.setHeaderText("There are no songs selected to edit.");
    		noSongAlert.showAndWait();
    		return;
    	}
    	
    	songName.setVisible(true);
    	artistName.setVisible(true);
    	albumName.setVisible(true);
    	songYear.setVisible(true);
    	
    	
    	int index = listView.getSelectionModel().getSelectedIndex();
    	newSongName.setText(songName.getText());
    	newSongName.setVisible(true);
    	
    	newArtistName.setText(artistName.getText());
    	newArtistName.setVisible(true);
    	
    	newAlbumName.setText(albumName.getText());
    	newAlbumName.setVisible(true);
    	
    	newYearDate.setText(songYear.getText());
    	newYearDate.setVisible(true);
    	
    	
    	
    	saveSong.setVisible(true);
    	cancelButton.setVisible(true);
    	
    	
    	
    	saveSong.setOnAction(new EventHandler<ActionEvent>(){
    		@SuppressWarnings("unchecked")
    		
			public void handle(ActionEvent e) {
    			if(newSongName.getText().isEmpty() || newArtistName.getText().isEmpty()) {
    				Alert alert = new Alert(AlertType.INFORMATION);
    				alert.initOwner(Main.primaryStage);
    				alert.setTitle("Empty Song/Artist");
    				alert.setHeaderText("You cannot leave a song or artist name empty. Please input a name for the song/artist.");
    				alert.showAndWait();
    				editSongDetails(event);
    				return;
    			}
    			
    			Iterator songIterator=songList.iterator();
    	        
    	        
    	        while(songIterator.hasNext()) {
    	        	JSONObject song = (JSONObject) songIterator.next();
    	        	if(song.get("name").equals(newSongName.getText()) && song.get("artist").equals(newArtistName.getText())) {
	    				if(index != songList.indexOf(song)) {
	    	        		Alert alert = new Alert(AlertType.INFORMATION);
		    				alert.initOwner(Main.primaryStage);
		    				alert.setTitle("Duplicate Song");
		    				alert.setHeaderText("You cannot edit a song with the same name/artist as another song. Please try again.");
		    				alert.setContentText("(If you are editing the same song and you are not changing the name or artist, make sure the song is selected)");
		    				alert.showAndWait();
		    				editSongDetails(event);
		    				return;
	    				}
    	        		
    	        	}
    	        }

    			

    			Alert confirmation = new Alert(AlertType.CONFIRMATION);
    			confirmation.initOwner(Main.primaryStage);
    			confirmation.setTitle("Confirm?");
    			confirmation.setHeaderText("Are you sure you want to edit the song's details?");
    			
    			ButtonType confirmEditSong = new ButtonType("Save Song Edits");
    			ButtonType confirmCancelEdit = new ButtonType("Cancel Changes");
    			confirmation.getButtonTypes().setAll(confirmEditSong, confirmCancelEdit);
    			
    			Optional<ButtonType> result = confirmation.showAndWait();
    			
    			if(result.get() == confirmCancelEdit) {
    				newSongName.setVisible(false);
        			newArtistName.setVisible(false);
        			newAlbumName.setVisible(false);
        			newYearDate.setVisible(false);
        			
        			saveSong.setVisible(false);
        			cancelButton.setVisible(false);
        			return;
    				
    			}
    			
    			
    			
    			
    			songName.setText(newSongName.getText());
    			artistName.setText(newArtistName.getText());
    			albumName.setText(newAlbumName.getText());
    			songYear.setText(newYearDate.getText());
    			
    			selectedSong.put("name",newSongName.getText());
    			selectedSong.put("artist",newArtistName.getText());
    			selectedSong.put("album",newAlbumName.getText());
    			selectedSong.put("year",newYearDate.getText());
    			
    			System.out.println("SIZE: " + obsList.size());
    			
    			
    			try {
    				obsList.set(index, selectedSong.get("name") + " - " + selectedSong.get("artist"));
    			} catch(NullPointerException f){
    				
    			}
    				
    			
    			//Collections.sort(obsList);
    			
    			int newIndex = listView.getItems().indexOf(songName.getText() + " - " + artistName.getText());
    			listView.getSelectionModel().select(newIndex);
    			
    			newSongName.setVisible(false);
    			newAlbumName.setVisible(false);
    			newArtistName.setVisible(false);
    			newYearDate.setVisible(false);
    			
    			
    			
    			saveSong.setVisible(false);
    			cancelButton.setVisible(false);
    			
    		}
    	});
    	
    	
    	cancelButton.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent e) {
    			newSongName.setVisible(false);
    			newArtistName.setVisible(false);
    			newAlbumName.setVisible(false);
    			newYearDate.setVisible(false);
    			
    			saveSong.setVisible(false);
    			cancelButton.setVisible(false);
    		}
    	});
    	
    	
    	
    	
    	
    	

    }
    
    
    
    
	public void deleteSong(ActionEvent event) {
    	if(listView.getItems().size() == 0) {
    		Alert noSongAlert = new Alert(AlertType.INFORMATION);
    		noSongAlert.initOwner(Main.primaryStage);
    		noSongAlert.setTitle("Unable to Perform Function");
    		noSongAlert.setHeaderText("There are no songs selected to delete.");
    		noSongAlert.showAndWait();
    		return;
    	}
    	
    	Alert confirmation = new Alert(AlertType.CONFIRMATION);
    	confirmation.initOwner(Main.primaryStage);
    	confirmation.setTitle("Delete Song?");
    	confirmation.setHeaderText("Are you sure you want to delete this song?");
    	
    	ButtonType yesDelete = new ButtonType("Yes");
    	ButtonType noDelete = new ButtonType("No");
    	
    	confirmation.getButtonTypes().setAll(yesDelete, noDelete);
    	
    	Optional<ButtonType> result = confirmation.showAndWait();
    	if(result.get() == noDelete) return;
    	
    		
    	int index = listView.getSelectionModel().getSelectedIndex();
    	if(index == 0) {
    		if(listView.getItems().size() != 1) {
    			listView.getSelectionModel().selectNext();
        		songList.remove(index);
        		listView.getItems().remove(index);
        		
    		
    		}
    		else {
    			songList.clear();
    			listView.getItems().clear();
    			songName.setText("");
    			albumName.setText("");
    			artistName.setText("");
    			songYear.setText("");
    		}
    	}
    	
    	
    	else if(index == listView.getItems().size() - 1) {
    		
    		
    		songList.remove(index);
    		listView.getItems().remove(index);
    		listView.getSelectionModel().select(index);
    		
    		
    		
    	}
    	else {
    		
    		songList.remove(index);
    		listView.getItems().remove(index);
    		listView.getSelectionModel().select(index);
    		
    	}
    	
    	
    	newSongName.setVisible(false);
    	newSongName.clear();
    	newArtistName.setVisible(false);
    	newArtistName.clear();
    	newAlbumName.setVisible(false);
    	newAlbumName.clear();
    	newYearDate.setVisible(false);
    	newYearDate.clear();
    	
    	
    	saveSong.setVisible(false);
    	
    	
        	
        	
        	
    		
    		
    	
    	
    	
    	
    	
    }
    
   
    
}	
