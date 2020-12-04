package UI;

import items.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import misc.BoardSelector;
import misc.CardHandler;
import moves.Position;
import net.ClientFactory;
import observers.BoardObserver;
import observers.GameObserver;
import observers.Observer;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class DisplayGrid extends Observer<Board>
{
	public Button[][] grid;
	private int reveals = 0;
	private Board lastBoardState;

	public DisplayGrid(int reveals, Hand hand) {
		this.reveals = reveals;
		grid = new Button[Board.HEIGHT][Board.WIDTH];
		getStylesheets().add(FileLoader.getResourceUrl("styles.css"));
		for(int j = 0; j < Board.HEIGHT ; j++ ) {
			HBox rowBox = new HBox();
			for(int i = 0; i < Board.WIDTH ; i++ ) {
				grid[j][i] = new Button();
				grid[j][i].getStyleClass().add("gridButton");
				final int x = i;
				final int y = j;
				grid[j][i].setOnAction(e -> {
					if(!GameObserver.Instance().running) {
						return;
					}

					// DM
					Optional<Card> card = hand.getCard(BoardObserver.Instance().get(x,y));
					if(card.isPresent()) {
						card.get().replete();
						card.get().setInUse(false);
						BoardObserver.Instance().remove(x,y);
					}

					if(this.reveals > 0 && !(BoardObserver.Instance().get(x, y) instanceof DefaultTile) && !BoardObserver.Instance().isRevealed(x,y)) {
						BoardObserver.Instance().reveal(x, y);
						JSONObject reveal = new JSONObject();
						JSONObject pos = new JSONObject();
						pos.put("X", x);
						pos.put("Y", y);
						reveal.put("Reveal", pos);
						ClientFactory.getClient().send(reveal.toJSONString());
						this.reveals--;
						return;
					} else if (this.reveals > 0) {
						// We've tried to reveal a revealed tile or a default tile
						return;
					}
					ArrayList<Card> cards = CardHandler.Instance().get();
					if( cards.size() == 0)  {
						return;
					}
					if(cards.get(0) instanceof TileCard)
					{
						if(!BoardObserver.Instance().isSteppable(x,y)) {
							((TileCard)cards.get(0)).play(x, y);
							CardHandler.Instance().deselect();
						}
					} else {
						if(BoardObserver.Instance().isSteppable(x,y)) {
							BoardSelector.Instance().select(x, y);
							if(BoardSelector.Instance().isSelected(x, y)) {
								addBorder(x, y);
							} else {
								removeBorder(x, y);
							}
							onUpdate(lastBoardState);
						}
					}
				});
				rowBox.getChildren().add(grid[j][i]);
			}
			getChildren().add(rowBox);
		}
		resetView();
	}
	
	public void resize(int size) {
		setMaxSize(size, size);
		setMinSize(size, size);
		for(Button[] row : grid) {
			for(Button b : row ) {
				b.setMaxSize(size/Board.WIDTH, size/Board.HEIGHT);
				b.setMinSize(size/Board.WIDTH, size/Board.HEIGHT);
			}
		}
		onUpdate(lastBoardState);
	}

	@Override
	public void onUpdate(Board board) {
		int size = ((int)getMaxHeight() / Board.HEIGHT) - 4;
		if(size <= 0) {
			size = 1;
		}
		for(int i = 0; i < Board.WIDTH ; i++ ) {
			for(int j = 0; j < Board.HEIGHT ; j++ ) {
 				Tile tile = board.get(i, j);
 				if(BoardSelector.Instance().isSelected(i, j)){
					addBorder(i,j);
				} else {
					removeBorder(i,j);
				}
 				if(tile instanceof Monster) {
					if(!tile.hasChanged) continue;
				}

 				Image image;
 				if(tile instanceof Monster && !((Monster)tile).isAlive()) {
 					image = overlap(tile.getX(),tile.getY(),"dead.png",size);
 				} else {
 					String artwork = tile.getArtwork();
 					image = new Image(FileLoader.getResourceAsStream(artwork), size, size, false, true);
 				}
 				final Image im = image;
 				final int x = i;
 				final int y = j;
 				Platform.runLater(() -> grid[y][x].setGraphic(new ImageView(im)));
 				tile.acceptChange();
			}
		}
		placePawn(size);
		lastBoardState = board;
	}
	
	public void placePawn(int size) {
		Image image;
		Position pawnpos = BoardObserver.Instance().getPawnPos();
		if(pawnpos == null) { return; }
		Tile tile = BoardObserver.Instance().get(pawnpos.x, pawnpos.y);
		if(tile instanceof Monster && !((Monster)tile).isAlive()) {
			image = overlap(tile.getX(),tile.getY(),"deadWithPawn.png",size);
		} else {
			image = overlap(pawnpos.x, pawnpos.y, "pawn.png", size);
		}
		final Image im = image;

		Platform.runLater(() -> grid[pawnpos.y][pawnpos.x].setGraphic(new ImageView(im)));
		tile.acceptChange();
	}
	
	public void resetView() {
		DefaultTile dt = new DefaultTile();
		String artwork = dt.getArtwork();
		int size = (int)getMinHeight() / Board.HEIGHT;
		Image image = new Image(FileLoader.getResourceAsStream(artwork), size, size, false, true);
		for(Button[] row : grid) {
			for(Button b : row ) {
				Platform.runLater(() -> b.setGraphic(new ImageView(image)));
			}
		}
	}

	private void removeBorder(int x, int y) {
		grid[y][x].getStyleClass().removeAll("gridButtonToggled");
		grid[y][x].getStyleClass().add("gridButtonUntoggled");
	}
	
	private void addBorder(int x, int y) {
		grid[y][x].getStyleClass().removeAll("gridButtonUntoggled");
		grid[y][x].getStyleClass().add("gridButtonToggled");
	}
	
	private Image overlap(int xPos, int yPos, String foreground ,int size) {
		final BufferedImage finalImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		 Graphics2D g = finalImage.createGraphics();
		 BufferedImage foreg;
		try
		{
			foreg = ImageIO.read(FileLoader.getResourceAsStream(foreground));
			String artwork = BoardObserver.Instance().get(xPos, yPos).getArtwork();
			BufferedImage bg = ImageIO.read(FileLoader.getResourceAsStream(artwork));
			g.drawImage(bg,0,0,size,size,0,0,bg.getWidth(),bg.getHeight(), null);
			g.drawImage(foreg,0,0,size,size,0,0,foreg.getWidth(),foreg.getHeight(), null);
			g.dispose();
			Image oImg = convertToFxImage(finalImage);

			return oImg;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static Image convertToFxImage(BufferedImage image) {
		WritableImage wr = null;
		if (image != null) {
			wr = new WritableImage(image.getWidth(), image.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					pw.setArgb(x, y, image.getRGB(x, y));
				}
			}
		}

		return new ImageView(wr).getImage();
	}
}


