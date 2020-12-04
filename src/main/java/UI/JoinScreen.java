package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.ClientFactory;

public class JoinScreen
{
	Scene scene;
	public JoinScreen() {
		VBox items = new VBox();
		items.setSpacing(20);
		Label label = new Label("Enter host address:");
		label.getStyleClass().add("whiteText");
		TextField hostTF = new TextField();
		hostTF.setMaxWidth(MainMenu.frameWidth /2);
		hostTF.getStyleClass().add("textField");
		hostTF.setAlignment(Pos.CENTER);
		Button connect = new Button("Connect");
		connect.getStyleClass().add("mainMenuButton");
		connect.setOnAction(e -> {
			ClientFactory.create(hostTF.getText());
			if(ClientFactory.hasConnected()) {
				connect.setText("Connected");
				connect.setDisable(true);
			}
		});
		items.getChildren().addAll(label,hostTF,connect);
		scene = new Scene(items,MainMenu.frameWidth, MainMenu.frameHeight);
		items.setBackground(MainMenu.bg);
		items.setAlignment(Pos.CENTER);
		scene.getStylesheets().add(FileLoader.getResourceUrl("styles.css"));
	}
	public Scene getScene() {
		return scene;
	}
}
