package items;

public class OrangeMonster extends Monster
{
	public OrangeMonster(int x, int y) {
		super(x,y);
		init();
	}
	public OrangeMonster() {
		super();
		init();
	}
	
	public void init() {
		frontArtwork = "monsters/OrangeMonster.png";
		name = "Orange";
		pattern.addEmpty(0,-1);
		pattern.addFilled(-1, 0);
		pattern.addEmpty(1, 0);
		pattern.addEmpty(0, 1);
	}
}

	