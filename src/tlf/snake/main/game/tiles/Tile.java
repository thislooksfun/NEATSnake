package tlf.snake.main.game.tiles;

import tlf.snake.main.Game;
import tlf.snake.main.SnakeGame;
import tlf.snake.main.game.helper.Color;
import tlf.snake.main.game.helper.Drawable;
import tlf.snake.main.game.helper.Pos;

/**
 * @author thislooksfun
 */
public class Tile extends Drawable
{
	/** The position of this tile */
	protected Pos pos;
	
	private final Color outerColor;
	private final Color innerColor;
	
	public Tile(Pos p, Color outer, Color inner)
	{
		this.pos = p;
		this.outerColor = outer;
		this.innerColor = inner;
		
		Game.game().addTile(this);
	}
	
	@Override
	public void draw()
	{
		drawTileWithOutline(outerColor, innerColor);
	}
	
	/** Removes this tile */
	public void destroy()
	{
		Game.game().removeTile(this);
	}
	
	/** Moves this tile by the specified amount */
	public void moveBy(int x, int y)
	{
		this.pos = this.pos.translate(x, y);
	}
	/** Moves this tile to the specified position */
	public void moveTo(int x, int y)
	{
		this.pos = new Pos(x, y);
	}
	
	protected void drawTile(Color color)
	{
		int size = SnakeGame.instance().tileSize();
		
		drawRect(this.pos.x * size, this.pos.y * size, size, size, color);
	}
	
	protected void drawTileWithOutline(Color out, Color in)
	{
		int size = SnakeGame.instance().tileSize();
		drawRect(this.pos.x * size, this.pos.y * size, size, size, out);
		drawRect((this.pos.x * size)+2, (this.pos.y * size)+2, size-4, size-4, in);
	}
	
	public Pos pos()
	{
		return pos;
	}
}