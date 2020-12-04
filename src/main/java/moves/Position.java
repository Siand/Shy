package moves;

public class Position
{
	public int x;
	public int y;
	public Position(int xPos, int yPos) {
		x = xPos;
		y = yPos;
	}
	@Override
	public boolean equals(Object p) {
		if(!(p instanceof Position)) return false;
		Position pos = (Position)p; 
		return pos.x == x && pos.y == y;
	}
	
	public boolean isAdjacent(Position p) {
		int xDif = Math.abs(x - p.x);
		int yDif = Math.abs(y - p.y);
		return xDif * yDif == 0 && xDif+yDif == 1;
	}
}
