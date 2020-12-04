package misc;

import java.util.Arrays;

public enum Player
{
	DM,
	HERO;

	public static String[] stringValues() {
		return Arrays.stream(Player.values()).map(Enum::name).toArray(String[]::new);
	}
}
