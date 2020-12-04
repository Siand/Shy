package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class TextScene
{
	private static TextScene instance = null;
	protected String text = "";
	
	public void Instantiate(String s) {
		text = s;
	}
	
	public static TextScene Instance() {
		if(instance == null) {
			instance = new TextScene();
		}
		return instance;
	}
	
	public Scene getScene() {
		Label label = new Label(text);
		label.getStyleClass().add("title");
		StackPane root = new StackPane();
		root.getChildren().add(label);
		root.setBackground(MainMenu.bg);
		StackPane.setAlignment(label, Pos.CENTER);
		Scene scene = new Scene(root, MainMenu.frameWidth, MainMenu.frameHeight);
		scene.getStylesheets().add(FileLoader.getResourceUrl("styles.css"));
		return scene;
	}
	
	public void setText(String s) {
		text = s;
	}
	
}
