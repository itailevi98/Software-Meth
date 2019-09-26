package SongLib;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Alert.AlertType;

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
    public Button deleteSong = new Button();
    public Button cancelButton = new Button();
   
  
    Object obj;
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
        	songArrList.add((String) song.get("name"));
        }

        
    	obsList=FXCollections.observableArrayList(songArrList);

    	listView.setItems(obsList);
    	
    	
    	listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> showItem(primaryStage)); 
    	
    	//select first item by default
    	listView.getSelectionModel().select(0);
    	
    	
    	editButton.setOnAction(this::editSongDetails);
    	
    	
    	
    
    }
    
    private void showItem(Stage primaryStage) {
    	
    	String content = listView.getSelectionModel().getSelectedItem();
    	
    	//iterate through JSON Array to find the song. Then set selected Song to the object.
    	
    	
    	for(Object songObject : songList) {
    		JSONObject song= (JSONObject) songObject;
    		if(song.get("name").equals(content)) {
    			selectedSong=song;
    		}
    	}
    	
    	
    	
    	songName.setText(content);
    	artistName.setText((String)selectedSong.get("artist"));
    	albumName.setText((String)selectedSong.get("album"));
    	songYear.setText((String)selectedSong.get("year"));

    	newSongName.setVisible(false);
		newAlbumName.setVisible(false);
		newArtistName.setVisible(false);
		newYearDate.setVisible(false);
		
		saveSong.setVisible(false);
    }
    

    
    public void editSongDetails(ActionEvent event){
    	int index = listView.getSelectionModel().getSelectedIndex();
    	newSongName.setVisible(true);
    	newSongName.setText(songName.getText());
    	
    	newArtistName.setVisible(true);
    	newArtistName.setText(artistName.getText());
    	
    	newAlbumName.setVisible(true);
    	newAlbumName.setText(albumName.getText());
    	
    	newYearDate.setVisible(true);
    	newYearDate.setText(songYear.getText());
    	
    	
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
    			
    			System.out.println("sure");
    			songName.setText(newSongName.getText());
    			artistName.setText(newArtistName.getText());
    			albumName.setText(newAlbumName.getText());
    			songYear.setText(newYearDate.getText());
    			
    			selectedSong.put("name",newSongName.getText());
    			selectedSong.put("artist",newArtistName.getText());
    			selectedSong.put("album",newAlbumName.getText());
    			selectedSong.put("year",newYearDate.getText());
    			
    			obsList.set(index, songName.getText());
    			
    			
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
    	
    	
    	
    }
    
   
    
}	
