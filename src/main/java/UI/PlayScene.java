package UI;

import items.Board;
import items.Card;
import items.MoveCard;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import misc.*;
import moves.Move;
import net.ClientFactory;
import observers.BoardObserver;
import observers.GameObserver;

import java.util.ArrayList;

public class PlayScene
{
	private static PlayScene instance;
	private PlayScene() {
	}
	
	public static PlayScene Instance() {
		if(instance == null) { 
			instance = new PlayScene();
		}
		return instance;
	}
	
	public Scene getScene(Player role, int reveals) {
		int size = (int)(MainMenu.frameHeight* 0.9);
		StackPane root = new StackPane();
		
		// Grid
		DisplayGrid grid;

		Hand hand = new Hand();
		Button lockIn = new Button("Lock In");
		lockIn.getStyleClass().add("UIButton");
		
		// DM specifics
		if(role.equals(Player.DM)) {
			grid = new DisplayGrid(0, hand);
			hand.supply(CardPile.Instance().drawHand());
			lockIn.setOnAction(e -> {
				if(BoardSelector.Instance().isValid()) {
					GameObserver.Instance().onChange();
				}
			});
		}
		// Hero specifics
		else {
			grid = new DisplayGrid(reveals, hand);
			ArrayList<Card> cards = new ArrayList<>();
			cards.add(new MoveCard(true, false));
			cards.add(new MoveCard(false, true));
			cards.add(new MoveCard(false, false));
			hand.supply(cards);
			lockIn.setOnAction(e -> {
				if(!CardHandler.Instance().hasCards()) { return; }
				MoveBuilder.Instance().reset();
				for(Card c : CardHandler.Instance().get()) {
					MoveBuilder.Instance().apply((MoveCard) c);
				}
				if(MoveBuilder.Instance().applyPositions(BoardObserver.Instance().getPawnPos(), BoardSelector.Instance().get())) {
					CardHandler.Instance().depleteAll();
					Move m = MoveBuilder.Instance().get();
					ClientFactory.getClient().send(m.toJSONString());
					BoardObserver.Instance().play(m);
				}
				
			});
		}
		// Add subscriptions
		BoardObserver.Instance().subscribe(Hand.class, hand);
		BoardObserver.Instance().subscribe(DisplayGrid.class, grid);

		// 
		hand.resize( size / Board.HEIGHT);
		grid.resize(size);
		root.getChildren().add(grid);
		StackPane.setAlignment(grid, Pos.CENTER_LEFT);

		root.getChildren().add(hand);
		StackPane.setAlignment(hand, Pos.TOP_RIGHT);
		VBox infoCorner = new VBox();
		Image im = new Image(FileLoader.getResourceAsStream("rules.jpg"), size / 4, size /4, false, true);
		ImageView rulesView = new ImageView(im);
		Label image = new Label("", rulesView);
		Label tilesToReveal = new Label(reveals +" Reveals this turn.");
		tilesToReveal.getStyleClass().add("whiteText");
		lockIn.setAlignment(Pos.CENTER_RIGHT);
		tilesToReveal.setAlignment(Pos.CENTER_RIGHT);
		image.setAlignment(Pos.CENTER_RIGHT);
		infoCorner.getChildren().addAll(lockIn,tilesToReveal,image);
		infoCorner.setMaxSize(size/3, size/3);
		root.getChildren().add(infoCorner);
		StackPane.setAlignment(infoCorner, Pos.BOTTOM_RIGHT);
		Scene scene = new Scene(root, MainMenu.frameWidth,  MainMenu.frameHeight);
		root.setBackground(MainMenu.bg);
		scene.getStylesheets().add(FileLoader.getResourceUrl("styles.css"));
		return scene;
	}
}
