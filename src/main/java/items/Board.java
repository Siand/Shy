package items;

import misc.BoardSelector;
import moves.Move;
import moves.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Board
{
	private Tile[][] board;
	public static final int WIDTH = 7, HEIGHT = 7;
	private HeroPawn pawn;

	public Board() {
		board = new Tile[7][7];
		reset();
	}
	
	public Tile get(int x, int y) {
		return board[y][x];
	}
	
	public Position getPawnPos() {
		if(pawn == null) return null;
		return pawn.getPos();
	}
	
	public void reveal(int x ,int y) {
		board[y][x].reveal();
	}
	
	public boolean add(int x, int y, TileCard t) {
		if(!t.canUse()) return false;
		if(board[y][x] instanceof DefaultTile) {
			t.deplete();
			board[y][x] = t.getTile();
			board[y][x].hasChanged = true;
			BoardSelector.Instance().select(x,y);
			return true;
		}
		return false;
	}
	
	public void reset() {
		for(int i = 0; i < HEIGHT; i++ ) {
			for(int j = 0; j < WIDTH; j++ ) {
				board[i][j] = new DefaultTile();
			}
		}
		pawn = null;
	}
	
	public void remove(int x, int y) {
		if(x < 0 || y < 0) return;
		board[y][x] = new DefaultTile();
		board[y][x].hasChanged = true;
		BoardSelector.Instance().select(x,y);
	}
	
	public void play(Move m) {
		Position nextMove;
		Position pawnPos = pawn.getPos();
		board[pawnPos.y][pawnPos.x].hasChanged = true;
		if(m.isJump) {
			nextMove = m.through;
			pawn.jumpOver(board[nextMove.y][nextMove.x]);
		}
		nextMove = m.to;
		if(m.isAttack) {
			pawn.attack(board[nextMove.y][nextMove.x]);
		} else {
			pawn.move(board[nextMove.y][nextMove.x]);
		}
		BoardSelector.Instance().reset();
	}
	
	public ArrayList<Position> getSurroundings(int x, int y) {
		int sX, sY, eX, eY;
		ArrayList<Position> list = new ArrayList<>();
		sX = x > 0? x-1: 0;
		sY = y > 0? y-1: 0;
		eX = x < WIDTH-1? x+1: WIDTH-1;  
		eY = y < HEIGHT-1? y+1: HEIGHT-1;
		for(int i = sX ; i <= eX; i++ )
			for(int j = sY; j <= eY; j++) {
				if(i == x && j == y) { continue; }
				if(!(board[j][i] instanceof DefaultTile)) {
					list.add(new Position(i - x, j - y));
				}
			}
		return list;
	}
	
	public boolean isShy(int x, int y) {
		boolean left = x != 0 && (board[y][x - 1].isMonster() && board[y][x - 1].isRevealed());

		boolean right = x != WIDTH - 1 && (board[y][x + 1].isMonster() && board[y][x + 1].isRevealed());

		boolean up = y != 0 && (board[y - 1][x].isMonster() && board[y - 1][x].isRevealed());

		boolean down = y != HEIGHT && (board[y + 1][x].isMonster() && board[y + 1][x].isRevealed());

		return left || right || up || down;
	}

	public void createPawnAt(int x, int y)
	{
		pawn = new HeroPawn(x,y);		
	}


	public boolean isSteppable(int x, int y)
	{
		return board[y][x].isSteppable();
	}
	
	public String toJSONString() {
		JSONObject boardObj = new JSONObject();
		JSONArray tileArr = new JSONArray();
		
		for(Tile[] row : board) {
			for(Tile tile : row) {
				if(tile instanceof DefaultTile) continue;
				JSONObject arrelement = new JSONObject();
				arrelement.put("Name", tile.getName());
				arrelement.put("X", tile.x);
				arrelement.put("Y", tile.y);
				tileArr.add(arrelement);
			}
		}
		
		boardObj.put("Tiles", tileArr);
		return boardObj.toJSONString();
	}


	public boolean isRevealed(int x, int y)
	{
		return board[y][x].isRevealed();
	}


	public void tagAll()
	{
		for(Tile[] row : board) {
			for(Tile t : row) {
				t.hasChanged = true;
			}
		}
	}
}
