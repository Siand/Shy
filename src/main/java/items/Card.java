package items;

public interface Card
{
	boolean isUnique();
	void setInUse(boolean iU);
	boolean getInUse();
	void deplete();
	void replete();
	boolean canUse();
	String getArtwork();
}
