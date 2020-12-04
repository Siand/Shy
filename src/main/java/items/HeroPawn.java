package items;

import moves.Position;

public class HeroPawn
{
	private int x,y;
	public boolean isAlive = true;
	public HeroPawn(int x, int y) {
		setPos(x,y);
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position getPos() {
		return new Position(x, y);
	}
	
	public void move(Tile tile) {
		tile.reveal();
		tile.stepOn(this);
	}
	
	public void attack(Tile tile) {
		tile.reveal();
		if(tile.isMonster()) {
			((Monster)tile).kill();
		}
		tile.stepOn(this);
	}
	public void jumpOver(Tile tile) {
		tile.reveal();
	}

	public void Die()
	{
		isAlive = false;
	}

}
