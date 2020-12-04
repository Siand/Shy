package util;

import items.*;
import observers.BoardObserver;

public class TileFactory
{
	public Tile create(String className, int x, int y) {
		Tile t;
		switch (className) {
			case "Blue" -> t = new BlueMonster();
			case "Cyan" -> t = new CyanMonster();
			case "Green" -> t = new GreenMonster();
			case "Orange" -> t = new OrangeMonster();
			case "Purple" -> t = new PurpleMonster();
			case "Red" -> t = new RedMonster();
			case "Wall" -> t = new Wall();
			case "Entrance" -> {
				BoardObserver.Instance().createPawnAt(x, y);
				t = new Entrance();
			}
			case "Exit" -> t = new Exit();
			default -> t = new Wall();
		}
		t.spawn(x,y);
		return t;
	}

}
