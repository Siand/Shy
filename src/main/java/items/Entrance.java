package items;

public class Entrance extends Tile
{ 
	
	public Entrance() {
		frontArtwork = "entrance.png";
		reveal();
		name = "Entrance";
	}
	@Override
	public void stepOn(HeroPawn h)
	{
		h.setPos(x, y);
	}

	@Override
	public void spawn(int x, int y)
	{
		this.x = x;
		this.y = y;
		hasChanged = true;
	}
	

}
