package items;

public class Wall extends Tile
{
	public Wall() {
		frontArtwork = "wall.png";
		name = "Wall";
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
		hasChanged = true;
	}

}
