package observers;

import UI.Game;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GameObserver
{

	private static GameObserver instance;
	private static Game game;
	public boolean running = true;
	
	public static GameObserver Instance() {
		if(instance == null) {
			instance = new GameObserver();
		}
		return instance;
	}

	public void onChange()
	{
		game.update();
	}
	
	public void subscribe(Game g) {
		game = g;
	}
	
	public void gameOver(boolean killed) {
		running = false;
		game.gameOver(killed);
	}

	public void play(String data) {
		if(game != null) {
			game.play(data);
		}
	}
}
