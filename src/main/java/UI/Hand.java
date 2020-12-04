package UI;

import items.Board;
import items.Card;
import items.Tile;
import items.TileCard;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import misc.CardHandler;
import observers.GameObserver;
import observers.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hand extends Observer<Board>
{
	private List<Card> cards;
	public static int size = 0;
	private List<Button> hand;
	int cardSize = 0;
	public Hand() {
		getStylesheets().add(FileLoader.getResourceUrl("styles.css"));
	}
	
	public void supply(List<Card> cards) {
		this.cards = cards;
		size = cards.size();
		getChildren().clear();
		hand = new ArrayList<>();
		for(int i=0; i < cards.size(); i++) {
			Button button = new Button();
			String artwork = cards.get(i).getArtwork();
			Image image = new Image(FileLoader.getResourceAsStream(artwork));
			button.setGraphic(new ImageView(image));
			button.getStyleClass().add("gridButton");
			final int index = i;
			button.setOnAction(e -> {
				if(!GameObserver.Instance().running) { return; }
				if(CardHandler.Instance().select(cards.get(index))) {
					addBorder(index);
				} else {
					removeBorder(index);
				}
			});
			hand.add(button);
		}
		for(int i = 0; i < cards.size(); i+=2) {
			HBox rBox = new HBox();
			rBox.getChildren().add(hand.get(i));
			if(hand.size() > i+1) {
				rBox.getChildren().add(hand.get(i+1));
			}
			getChildren().add(rBox);
		}
	}
	
	public void resize(int cSize) {
		this.cardSize = cSize - 4;
		setMaxHeight((double)(cSize * (hand.size()+1)/2));
		setMinHeight((double)(cSize * (hand.size()+1)/2));
		setMinWidth(cSize * 2);
		setMaxWidth(cSize * 2);
		for(int i = 0; i< hand.size() ; i++) {
			hand.get(i).setMaxSize(cSize, cSize);
			hand.get(i).setMinSize(cSize, cSize);
			String artwork = cards.get(i).getArtwork();
			Image image = new Image(FileLoader.getResourceAsStream(artwork), cardSize, cardSize, false, true);
			hand.get(i).setGraphic(new ImageView(image));
		}
	}

	@Override
	public void onUpdate(Board b)
	{
		if(hand != null) {
			for (int i = 0; i < hand.size(); i++) {
				String artwork = cards.get(i).getArtwork();
				Image image = new Image(FileLoader.getResourceAsStream(artwork), cardSize, cardSize, false, true);
				hand.get(i).setDisable(!cards.get(i).canUse());
				hand.get(i).setGraphic(new ImageView(image));
				if (cards.get(i).getInUse()) {
					addBorder(i);
				} else {
					removeBorder(i);
				}
			}
		}
	}
	
	private void removeBorder(int pos) {
		hand.get(pos).getStyleClass().removeAll("gridButtonToggled");
		hand.get(pos).getStyleClass().add("gridButtonUntoggled");
	}
	
	private void addBorder(int pos) {
		hand.get(pos).getStyleClass().removeAll("gridButtonUntoggled");
		hand.get(pos).getStyleClass().add("gridButtonToggled");
	}

	public Optional<Card> getCard(Tile tile) {
		return  cards.stream().filter(card -> (card instanceof TileCard && ((TileCard)card).getTile().equals(tile))).findFirst();
	}
}
