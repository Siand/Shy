package misc;

import items.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardPile
{
	
	private static CardPile instance = null;
	private static ArrayList<Card> monsterPile;
	
	private CardPile() {
		monsterPile = new ArrayList<>();
		monsterPile.add(new TileCard(new BlueMonster()));
		monsterPile.add(new TileCard(new CyanMonster()));
		monsterPile.add(new TileCard(new GreenMonster()));
		monsterPile.add(new TileCard(new OrangeMonster()));
		monsterPile.add(new TileCard(new PurpleMonster()));
		monsterPile.add(new TileCard(new RedMonster()));
		Collections.shuffle(monsterPile);
	}
	
	public static CardPile Instance() {
		if(instance == null) {
			instance = new CardPile();
		}
		return instance;
	}
	
	public List<Card> drawHand() {
		List<Card> hand =  new ArrayList<>();
		for(int i = 0 ; i < 3; i++) {
			hand.add(new TileCard(new Wall()));
		}
		hand.add(new TileCard(new Entrance()));
		hand.add(new TileCard(new Exit()));
		TileCard m1 = (TileCard) monsterPile.remove(0);
		TileCard m2 = (TileCard) monsterPile.remove(0);
		hand.add(m1);
		hand.add(m2);
		return hand;
	}
	
}
