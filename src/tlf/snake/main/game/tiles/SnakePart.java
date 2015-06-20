package tlf.snake.main.game.tiles;

import tlf.snake.main.game.helper.Color;
import tlf.snake.main.game.helper.Pos;

/**
 * @author thislooksfun
 */
public class SnakePart extends Tile
{
	public SnakePart(int x, int y)
	{
		this(new Pos(x, y));
	}
	public SnakePart(Pos p)
	{
		super(p, new Color(0, 0xcc, 0), new Color(0, 0xee, 0));
	}
}
