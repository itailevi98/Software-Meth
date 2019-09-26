package SongLib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//    	Parent root = FXMLLoader.load(getClass().getResource("hellofx.fxml"));
//    	FXMLLoader loader= new FXMLLoader();
    
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("SongLib.fxml"));
    	Parent root = (Parent) loader.load();
    	    	
        Controller controller = loader.getController();
        controller.initialize();
        
        Scene scene = new Scene(root,400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
