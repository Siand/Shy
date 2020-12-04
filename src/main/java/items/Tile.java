package items;

import lombok.Data;

@Data
public abstract class Tile
{
	public String backArtwork = "cardback.png";
	public String frontArtwork = "";
	protected String name = "";
	protected int x;
	protected int y;
	protected boolean revealed;
	public boolean hasChanged = true;

	public Tile() {
		revealed = false;
	}


	public abstract void stepOn(HeroPawn h);
	public abstract void spawn(int x ,int y);
	public String getName() {
		return name;
	}
	public boolean isSteppable() {
		return true;
	}
	
	public void reveal() {
		revealed = true;
		hasChanged = true;
	}
	
	public String getArtwork()
	{
		return revealed?  frontArtwork : backArtwork;
	}
	public boolean isRevealed() {
		return revealed;
	}
	public boolean isMonster() {
		return false;
	}
	public void acceptChange() {
		hasChanged = false;
	}
}
