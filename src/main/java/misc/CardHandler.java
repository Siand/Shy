package misc;

import items.Card;
import items.TileCard;

import java.util.ArrayList;

public class CardHandler
{
	private static ArrayList<Card> selected;
	private static CardHandler instance = null;
	
	public static CardHandler Instance() {
		if(instance == null) {
			instance = new CardHandler();
			selected  = new ArrayList<>();
		}
		return instance;
	}
	
	public void add(Card c) {
		if(c.isUnique()) {
			for(Card card : selected) {
				card.setInUse(false);
			}
			selected.clear();
		}
		if(selected.size() > 0 && selected.get(0).isUnique()) {
			// for move cards when we have walk
			selected.get(0).setInUse(false);
			selected.clear();
		}
		c.setInUse(true);
		selected.add(c);
	}
	
	public void depleteAll() {
		for(Card c : selected) {
			c.deplete();
		}
		deselectAll();
	}
	
	public void remove (Card c) {
		c.setInUse(false);
		if(c instanceof TileCard) {
			((TileCard)c).remove();
		}
		selected.remove(c);
	}
	
	public boolean select(Card c) {
		if(!c.getInUse() && c.canUse()) {
			add(c);
			return true;
		}
		remove(c);
		return false;
	}
	
	
	public void deselect() {
		// For use after placing a tile
		if(selected.isEmpty()) return;
		if(selected.get(0) instanceof TileCard) {
			selected.remove(0);
		}
	}

	public void deselectAll() {
		selected = new ArrayList<>();
	}
	
	public boolean hasCards() {
		return !selected.isEmpty();
	}
	
	public ArrayList<Card> get() {
		return selected;
	}
}
