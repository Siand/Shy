package UI;

import items.Entrance;
import javafx.application.Platform;
import javafx.scene.Scene;
import misc.BoardSelector;
import misc.Player;
import moves.Position;
import net.ClientFactory;
import observers.BoardObserver;
import util.MoveParser;

public class Game
{
	private final static int TURNS = 6;
	private boolean shouldPlayEvenMoves = false;
	private int currentTurn = 0;
	private int reveals = 2;
	private final Player role;
	private final MoveParser moveParser;


	public Game(Player role) {
		moveParser = new MoveParser();
		TextScene.Instance().Instantiate("Waiting for your opponent...");
		this.role = role;
		if(Player.DM.equals(role)) {
			shouldPlayEvenMoves = true;
		}
		update();
	}
	
	public void update() {
		if(TURNS == currentTurn) {
			gameOver(false); 
		}

		if((currentTurn % 2 != 0) == shouldPlayEvenMoves) {
			Scene scene = TextScene.Instance().getScene();
			if(role.equals(Player.DM)) {
				String boardState = BoardObserver.Instance().toJSONString();
				DisplayGrid dg = new DisplayGrid(0, new Hand());
				BoardObserver.Instance().subscribe(DisplayGrid.class, dg);
				for(Position pos : BoardSelector.Instance().get()) {
					if (BoardObserver.Instance().get(pos.x, pos.y) instanceof Entrance) {
						BoardObserver.Instance().createPawnAt(pos.x, pos.y);
					}
				}
				BoardSelector.Instance().reset();
				int size = (int)(MainMenu.frameHeight* 0.9);
				dg.resize(size);
				ClientFactory.getClient().send(boardState);
				ObserverScreen.createInstance(dg);
				scene = ObserverScreen.Instance().getScene();
			}

			final Scene finalScene = scene;
			Platform.runLater(() -> {
				MainMenu.mainStage.setScene(finalScene);
				BoardObserver.Instance().tagAll();
			});
		} else {
			Platform.runLater(() -> {
				MainMenu.mainStage.setScene(PlayScene.Instance().getScene(role, reveals--));
				if(role == Player.DM) {
					BoardObserver.Instance().reset();
				} else {
					BoardObserver.Instance().tagAll();
				}
			});
		}
		currentTurn++;
	}

	public void play(String data) {
		moveParser.parse(data);
	}
	
	public void gameOver(boolean killed) {

		if((role.equals(Player.DM) && killed) || (role.equals(Player.HERO) && !killed)) {
			TextScene.Instance().setText("YOU WON!");
		} else {
			TextScene.Instance().setText("YOU LOST!");
		}

		Platform.runLater(() -> MainMenu.mainStage.setScene(TextScene.Instance().getScene()));
	}
	
}
