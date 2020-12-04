package items;

public class PurpleMonster extends Monster
{
	public PurpleMonster(int x, int y) {
		super(x,y);
		init();
	}
	public PurpleMonster() {
		super();
		init();
	}
	
	public void init() {
		frontArtwork = "monsters/PurpleMonster.png";
		name = "Purple";
		pattern.addFilled(1, -1);
	}
}
