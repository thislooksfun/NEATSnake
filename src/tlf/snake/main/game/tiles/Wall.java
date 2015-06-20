package tlf.snake.main.game.tiles;

import tlf.snake.main.game.helper.Color;
import tlf.snake.main.game.helper.Pos;

/**
 * @author thislooksfun
 */
public class Wall extends Tile
{
	public Wall(int x, int y)
	{
		this(new Pos(x, y));
	}
	public Wall(Pos p)
	{
		super(p, Color.fromHex("555"), Color.fromHex("333"));
	}
}