package tlf.snake.main.game.tiles;

import tlf.snake.main.SnakeGame;
import tlf.snake.main.game.entity.Snake;
import tlf.snake.main.game.helper.Color;
import tlf.snake.main.game.helper.Pos;

/**
 * @author thislooksfun
 */
public class SnakePart extends Tile
{
	private final Snake.Vector inVec;
	private final Snake.Vector outVec;
	
	public SnakePart(int x, int y, Snake.Vector dir)
	{
		this(new Pos(x, y), dir.inverse(), dir);
	}
	public SnakePart(int x, int y, Snake.Vector in, Snake.Vector out)
	{
		this(new Pos(x, y), in, out);
	}
	public SnakePart(Pos p, Snake.Vector dir)
	{
		this(p, dir.inverse(), dir);
	}
	public SnakePart(Pos p, Snake.Vector in, Snake.Vector out)
	{
		super(p, Color.rgb(0, 0xcc, 0), Color.rgb(0, 0xee, 0));
		this.inVec = in == null ? Snake.Vector.NULL : in;
		this.outVec = out == null ? Snake.Vector.NULL : out;
	}
	
	@Override
	public void draw()
	{
		drawVec(inVec, innerColor);
		drawVec(outVec, innerColor);
	}
	
	private void drawVec(Snake.Vector v, Color c)
	{
		int size = SnakeGame.instance().tileSize();
		
		switch (v) {
			case NULL:
				drawRect((pos.x * size)+1, (pos.y * size)+1, size-2, size-2, c);
				break;
			case UP:
				drawRect((pos.x * size)+1, (pos.y * size), size-2, size-1, c);
				break;
			case DOWN:
				drawRect((pos.x * size)+1, (pos.y * size)+1, size-2, size-1, c);
				break;
			case LEFT:
				drawRect((pos.x * size), (pos.y * size)+1, size-1, size-2, c);
				break;
			case RIGHT:
				drawRect((pos.x * size)+1, (pos.y * size)+1, size-1, size-2, c);
				break;
		}
	}
}
