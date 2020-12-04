package misc;

import UI.Hand;
import moves.Position;

import java.util.ArrayList;

public class BoardSelector
{

	private static BoardSelector instance = null;
	private static ArrayList<Position> positions;
	
	public static BoardSelector Instance() {
		if(instance == null) {
			instance = new BoardSelector();
			positions = new ArrayList<>();
		}
		return instance;
	}
	
	public void select(int x, int y) {
		//Add the element if not in the list, otherwise remove
		Position newPos = new Position(x, y);
		if(!positions.remove(newPos)) {
			positions.add(newPos);
		}
	}
	
	public boolean isSelected(int x, int y) {
		Position newPos = new Position(x, y);
		return positions.contains(newPos);
	}
	
	public void reset() {
		positions.clear();
	}
	
	public ArrayList<Position> get() { 
		return positions;
	}
	
	public boolean isValid() {
		return explore() == Hand.size;
	}
	
	
	public int explore() {
		if(positions.isEmpty()) {
			return 0;
		}
		ArrayList<Position> explored = new ArrayList<>();
		explored.add(positions.get(0));
		while(true) {
			int eSize = explored.size();
			for(Position position : positions) {
				if(explored.contains(position)) continue;
				for(int i=0 ; i < eSize; i++) { 
					if(explored.get(i).isAdjacent(position)) {
						explored.add(position);
						break;
					}
				}
			}
			if(explored.size() == eSize) break;
		}
		return explored.size();
	}
	
	
}

