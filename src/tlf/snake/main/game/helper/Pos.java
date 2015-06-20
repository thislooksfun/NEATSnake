package tlf.snake.main.game.helper;

/**
 * @author thislooksfun
 */
public class Pos
{
	public final int x;
	public final int y;
	
	public Pos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Pos translate(int x, int y)
	{
		return new Pos(this.x + x, this.y + y);
	}
	
	@Override
	public String toString()
	{
		return "x: "+x+", y: "+y;
	}
}
