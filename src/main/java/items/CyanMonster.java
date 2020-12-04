package items;

public class CyanMonster extends Monster
{
	public CyanMonster(int x, int y) {
		super(x,y);
		init();
	}
	
	public CyanMonster() {
		super();
		init();
	}
	
	public void init() {
		frontArtwork = "monsters/CyanMonster.png";
		name = "Cyan";
		pattern.addEmpty(0,-1);
		pattern.addEmpty(1, -1);
		pattern.addFilled(1, 0);
	}
}
