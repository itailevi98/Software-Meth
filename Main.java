package SongLib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;

import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*; 

public class Main extends Application {

	public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
//    	Parent root = FXMLLoader.load(getClass().getResource("hellofx.fxml"));
//    	FXMLLoader loader= new FXMLLoader();
    
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("SongLib.fxml"));
    	Parent root = (Parent) loader.load();
    	    	
        Controller controller = loader.getController();
        controller.initialize(primaryStage);
        
        Scene scene = new Scene(root,700, 500);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
       
        
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, arg0 -> {
			try {
				closeWindowEvent(arg0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

        
        
    }

    private void closeWindowEvent(WindowEvent event) throws Exception{
    	
    	JSONObject obj=(JSONObject) Controller.obj;
    	
        FileWriter file = new FileWriter("songs.json");
        file.write(obj.toJSONString());
        file.flush();

    	
    	
        //System.out.println("Window close request ...");
        

    }

    public static void main(String[] args) {
        launch(args);
    }
}
