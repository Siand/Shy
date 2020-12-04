package misc;

import items.MoveCard;
import moves.Move;
import moves.Position;

import java.util.ArrayList;

public class MoveBuilder
{
	private Move move;
	private static MoveBuilder instance;
	private MoveBuilder() {
		reset();
	}
	public static MoveBuilder Instance() {
		if(instance == null) {
			instance = new MoveBuilder();
		}
		return instance;
	}
	
	public Move get() {
		return move;
	}
	
	public void reset() {
		move = new Move();
	}
	
	public void apply(MoveCard m) {
		if(m.isAttack()) {
			move.isAttack = !move.isAttack;
		}
		if(m.isJump()) {
			move.isJump = !move.isJump;
		}
	}
	
	public boolean applyPositions(Position initial, ArrayList<Position> path) {
		if(move.isJump) {
			if(path.size() != 2) {
				return false;
			}
			if(path.get(0).isAdjacent(initial) && path.get(0).isAdjacent(path.get(1))) {
				move.from = initial;
				move.through = path.get(0);
				move.to = path.get(1);
				return true;
			} else if(path.get(1).isAdjacent(initial) && path.get(0).isAdjacent(path.get(1))) {
				move.from = initial;
				move.through = path.get(1);
				move.to = path.get(0);
				return true;
			}
			return false;
		} else {
			if(path.size() != 1) {
				return false;
			}
			if(path.get(0).isAdjacent(initial)) {
				move.from = initial;
				move.to = path.get(0);
				return true;
			}
			return false;
		}
		
	}
	
}
