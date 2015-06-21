package tlf.snake.main.game.util;

/**
 * @author thislooksfun
 */
public class MathUtil
{
	public static int clamp(int x, int min, int max)
	{
		if (x < min) return min;
		else if (x > max) return max;
		
		return x;
	}
}