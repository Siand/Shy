package items;

import observers.GameObserver;

public class Exit extends Tile
{

	public Exit() {
		frontArtwork = "exit.png";
		name = "Exit";
	}
	
	@Override
	public void stepOn(HeroPawn h)
	{
		reveal();
		h.setPos(x, y);
		GameObserver.Instance().onChange();
	}

	@Override
	public void spawn(int x, int y)
	{
		this.x = x;
		this.y = y;
		hasChanged = true;
	}

}
