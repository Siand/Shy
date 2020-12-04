package items;

public class DefaultTile extends Tile
{

	@Override
	public void stepOn(HeroPawn h)
	{
		System.err.println("STEPPED ON NO TILE");
	}

	@Override
	public void spawn(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public String getArtwork()
	{
		return "black.png";
	}
	
	@Override
	public boolean isSteppable() {
		return false;
	}

}
