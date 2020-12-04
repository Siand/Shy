package items;

import misc.BoardSelector;
import observers.BoardObserver;

public class TileCard implements Card
{
	private Tile t;
	private boolean inUse = false;
	protected int uses = 1;
	private String artwork = "";
	private int x = -1 ,y = -1;
	public TileCard(Tile t) {
		this.t = t;
		this.t.reveal();
		artwork = t.getArtwork();
	}
	
	public TileCard(Tile t, boolean reveal) {
		this.t = t;
		if(reveal) {
			this.t.reveal();
		}
		artwork = t.getArtwork();
	}
	public void play(int x, int y) {
		if(BoardObserver.Instance().add(x, y, this)) {
			this.x = x;
			this.y = y;
			t.spawn(x, y);
		}
	}
	
	public Tile getTile() {
		return t;
	}
	
	public void remove() {
		if(BoardSelector.Instance().isSelected(x, y)) {
			uses = 1;
			BoardObserver.Instance().remove(x, y);
		}
	}
	
	public String getArtwork() {
		return t.getArtwork();
	}

	@Override
	public boolean isUnique()
	{
		return true;
	}

	@Override
	public void setInUse(boolean iU)
	{
		inUse = iU;
	}

	@Override
	public boolean getInUse()
	{
		return inUse;
	}

	@Override
	public void deplete()
	{
		setInUse(false);
		uses--;
	}
	
	@Override
	public void replete() {
		uses = 1;
	}

	@Override
	public boolean canUse()
	{
		return uses > 0;
	}

}
