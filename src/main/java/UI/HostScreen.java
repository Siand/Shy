package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import misc.Player;
import net.ClientFactory;
import net.HostClient;
import observers.GameObserver;


public class HostScreen
{
	private final Scene scene;
	static Player role = Player.DM;
	public HostScreen() {
		Label text = new Label("Select your role:");
		text.getStyleClass().add("title");
		ComboBox<String> roles = new ComboBox<String>();
		roles.getItems().addAll(Player.stringValues());
		roles.getSelectionModel().select(0);
		roles.getSelectionModel().selectedItemProperty().addListener(
				(options, oldValue, newValue) -> { 
					role = newValue.equals(Player.DM.name())? Player.DM : Player.HERO;
				}
		);
		roles.getStyleClass().add("comboBox");
		Button start = new Button("Start");
		start.getStyleClass().add("mainMenuButton");
		start.setOnAction(e -> {
			if(HostClient.hasConnection) {
				GameObserver.Instance().subscribe( new Game(role));
				ClientFactory.getClient().send("Start:" + (role == Player.DM? Player.HERO.name() : Player.DM.name()));
			}
		});
		VBox items = new VBox();
		items.setSpacing(20);
		items.getChildren().add(text);
		items.getChildren().add(roles);
		items.getChildren().add(start);
		items.setBackground(MainMenu.bg);
		items.setAlignment(Pos.CENTER);
		scene = new Scene(items, MainMenu.frameWidth, MainMenu.frameHeight);
		scene.getStylesheets().add(FileLoader.getResourceUrl("styles.css"));
		
		ClientFactory.create();
		start.setDisable(false);
	}
	
	
	
	public Scene getScene() {
		return scene;
	}
}
