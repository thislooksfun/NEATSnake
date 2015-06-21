package tlf.snake.main.game.tiles;

import tlf.snake.main.game.helper.Color;
import tlf.snake.main.game.helper.Pos;

/**
 * @author thislooksfun
 */
public class Food extends Tile
{
	public Food(int x, int y)
	{
		this(new Pos(x, y));
	}
	public Food(Pos p)
	{
		super(p, Color.rgb(0xcc, 0, 0), Color.rgb(0xee, 0, 0));
	}
}