package items;

public class RedMonster extends Monster
{
	public RedMonster(int x, int y) {
		super(x,y);
		init();
	}
	
	public RedMonster() {
		super();
		init();
	}
	
	public void init() {
		frontArtwork = "monsters/RedMonster.png";
		name = "Red";
		pattern.addFilled(0,-1);
		pattern.addEmpty(1, -1);
		pattern.addEmpty(-1, 0);
		pattern.addFilled(1, 0);
		pattern.addEmpty(0, 1);
	}
}
