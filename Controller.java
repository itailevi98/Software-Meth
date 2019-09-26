package SongLib;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
//import javafx.beans.value.ChangeListener;
import java.util.Optional;


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
   
  
    public void initialize(Stage primaryStage) {
    	

    	obsList=FXCollections.observableArrayList(
    			"Dead Presidents", 
    			"Thought you wuz nice", 
    			"Blackout", 
    			"Dancing in the Rain",
    			"Thieves in the Night"
    	);
    	listView.setItems(obsList);
    	
    	
    	listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> showItem(primaryStage)); 
    	
    	//select first item by default
    	listView.getSelectionModel().select(0);
    	
    	
    	
    	editButton.setOnAction(this::editSongDetails);
    	deleteButton.setOnAction(this::deleteSong);
    	
    	
    	
    	
    
    }
    
    private void showItem(Stage primaryStage) {
    	
    	String content = listView.getSelectionModel().getSelectedItem();
    	songName.setText(content);
    	
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
    	
    	
    	
    	saveSong.setOnAction(new EventHandler<ActionEvent>() {
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
    	Alert confirmation = new Alert(AlertType.CONFIRMATION);
    	confirmation.initOwner(Main.primaryStage);
    	confirmation.setTitle("Delete Song?");
    	confirmation.setHeaderText("Are you sure you want to delete this song?");
    	
    	ButtonType yesDelete = new ButtonType("Yes");
    	ButtonType noDelete = new ButtonType("No");
    	
    	confirmation.getButtonTypes().setAll(yesDelete, noDelete);
    	
    	Optional<ButtonType> result = confirmation.showAndWait();
    	if(result.get() == noDelete) return;
    	else {
    		
        	int index = listView.getSelectionModel().getSelectedIndex();
        	if(index == 0) {
        		listView.getItems().remove(index);
        		listView.getSelectionModel().select(0);
        		showItem(Main.primaryStage);
        	}
        	
        	
        	else if(index == listView.getItems().size() - 1) {
        		listView.getSelectionModel().selectPrevious();
        		listView.getItems().remove(index);
        		showItem(Main.primaryStage);
        		
        	}
        	
        	
    		
    		
    	}
    	
    }
    
   
    
}	