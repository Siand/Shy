package UI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.ClientFactory;

public class MainMenu extends Application
{
	public static Stage mainStage;
	public static void main(String[] args){
		launch(args);
	}
	static double frameWidth = 468, frameHeight = 360;
	static Background bg;
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		mainStage.setResizable(false);
		mainStage.sizeToScene(); // Prevent extra area in the border area
		//primaryStage.getIcons().add(new Image(DMClient.class.getResourceAsStream("logo.png")));
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		bg = new Background(new BackgroundFill(Color.BLACK,new CornerRadii(0),new Insets(0)));

		mainStage.setX(bounds.getMinX());
		mainStage.setY(bounds.getMinY());
		frameWidth = bounds.getWidth();
		frameHeight = bounds.getHeight();
		mainStage.setWidth(frameWidth);
		mainStage.setHeight(frameHeight);
		
		// Items
		Button host = new Button("HOST");
		host.getStyleClass().add("mainMenuButton");
		Button join = new Button("JOIN");
		join.getStyleClass().add("mainMenuButton");
		Label title  = new Label("Shy Monsters");
		title.getStyleClass().add("title");
		
		
		VBox scenePanel = new VBox();
		scenePanel.setAlignment(Pos.CENTER);
		scenePanel.setSpacing(20);
		scenePanel.setBackground(bg);
		Scene mainScene = new Scene(scenePanel, frameWidth, frameHeight);

		mainScene.getStylesheets().add(FileLoader.getResourceUrl("styles.css"));
		
		
		//Add items to panel
		scenePanel.getChildren().add(title);
		scenePanel.getChildren().add(host);
		scenePanel.getChildren().add(join);
		
		host.setOnAction(e -> {
			HostScreen hs = new HostScreen();
			mainStage.setScene(hs.getScene());
		});
		
		join.setOnAction(e -> {
			JoinScreen js = new JoinScreen(); 
			mainStage.setScene(js.getScene());
		});
		
		
		//DisplayGrid grid = new DisplayGrid();
		//grid.resize((int)(frameHeight* 0.9));
		//scenePanel.getChildren().add(grid);
		//mainStage.setScene(PlayScene.Instance().getScene(Constants.DM,2));
		mainStage.setScene(mainScene);
		mainStage.setMaximized(true);
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  if(ClientFactory.getClient() != null)
	        		  ClientFactory.getClient().close();
	          }
	      });    
		mainStage.show();
	}

}
