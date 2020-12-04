package items;

import observers.BoardObserver;
import observers.GameObserver;

public class Monster extends Tile
{

	private boolean isAlive = true;
	Pattern pattern;
	public Monster(int x, int y) {
		pattern = new Pattern();
		spawn(x, y);
	}
	
	public Monster() {
		pattern = new Pattern();
	}
	
	@Override
	public void spawn(int x, int y)
	{
		this.x = x;
		this.y = y;
		hasChanged = true;
	}
	
	@Override
	public void stepOn(HeroPawn h)
	{
		h.setPos(x, y);
		reveal();
		if(isAlive && pattern.isActive(BoardObserver.Instance().getSurroundings(x,y)) && !BoardObserver.Instance().isShy(x,y)) {
			GameObserver.Instance().gameOver(true);
		}
		hasChanged = true;
	}

	@Override
	public boolean isMonster()
	{
		return true;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void kill() {
		isAlive = false;
	}
}
