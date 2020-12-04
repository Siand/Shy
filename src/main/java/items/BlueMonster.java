package items;

public class BlueMonster extends Monster
{
	public BlueMonster(int x, int y) {
		super(x,y);
		init();
	}
	
	public BlueMonster() {
		super();
		init();
	}
	
	public void init() {
		frontArtwork = "monsters/BlueMonster.png";
		name = "Blue";
		pattern.addFilled(0,-1);
		pattern.addFilled(-1, 0);
		pattern.addFilled(1, 0);
	}
}
