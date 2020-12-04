package items;

public class GreenMonster extends Monster
{
	public GreenMonster(int x, int y) {
		super(x,y);
		init();
	}
	
	public GreenMonster() {
		super();
		init();
	}
	
	public void init() {
		frontArtwork = "monsters/GreenMonster.png";
		name = "Green";
		pattern.addEmpty(0,-1);
		pattern.addEmpty(0, 1);
		pattern.addFilled(-1, 0);
		pattern.addFilled(1, 0);
	}
}

