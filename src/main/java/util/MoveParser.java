package util;


import items.Tile;
import items.TileCard;
import lombok.NoArgsConstructor;
import misc.BoardSelector;
import moves.Move;
import moves.Position;
import observers.BoardObserver;
import observers.GameObserver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@NoArgsConstructor
public class MoveParser
{
	public void parse(String raw) {
		try
		{
			JSONObject data = (JSONObject)((new JSONParser()).parse(raw));
			if(data == null) return;
			if(data.containsKey("Tiles")) {
				GameObserver.Instance().onChange();
				setBoard(data);
			} else if(data.containsKey("Move")) {
				updateBoard(data);
			} else if(data.containsKey("Reveal")) {
				revealAtPos(data);
			}
		} catch (ParseException e) {
			System.err.println("Invalid JSON");
		}

	}
	
	private void revealAtPos(JSONObject data) {
		JSONObject tile = (JSONObject)data.get("Reveal");
		int x = ((Long)tile.get("X")).intValue();
		int y = ((Long)tile.get("Y")).intValue();
		BoardObserver.Instance().reveal(x, y);
	}
	
	
	public void setBoard(JSONObject data) {
		BoardObserver.Instance().reset();
		TileFactory tileFactory = new TileFactory();
		JSONArray tiles = (JSONArray)data.get("Tiles");
		for(Object o : tiles) {
			JSONObject tileObject = (JSONObject)o;
			String type = (String)tileObject.get("Name");
			int x = ((Long)tileObject.get("X")).intValue();
			int y = ((Long)tileObject.get("Y")).intValue();
			Tile t = tileFactory.create(type,x,y);
			BoardObserver.Instance().add(x,y,new TileCard(t,false));
		}
		// Adding tiles selects them by default, so reset 
		BoardSelector.Instance().reset();
	}
	
	private void updateBoard(JSONObject data) {
		JSONObject move = (JSONObject)data.get("Move");
		Move m = new Move();
		if(move.containsKey("Through")) {
			JSONObject through =  (JSONObject)move.get("Through");
			int x = ((Long)through.get("X")).intValue();
			int y = ((Long)through.get("Y")).intValue();
			m.through = new Position(x, y);
			m.isJump = true;
		}
		//From
		JSONObject from =  (JSONObject)move.get("From");
		int fromX = ((Long)from.get("X")).intValue();
		int fromY = ((Long)from.get("Y")).intValue();
		m.from = new Position(fromX, fromY);
		// To
		JSONObject to =  (JSONObject)move.get("To");
		int toX = ((Long)to.get("X")).intValue();
		int toY = ((Long)to.get("Y")).intValue();
		m.to = new Position(toX, toY);
		if(move.containsKey("isAttack")) {
			m.isAttack = true;
		}
		// play the move
		BoardObserver.Instance().play(m);
	}
	
}
