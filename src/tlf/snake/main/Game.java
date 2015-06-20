package tlf.snake.main;

import tlf.snake.main.game.entity.Snake;
import tlf.snake.main.game.helper.Pos;
import tlf.snake.main.game.tiles.Food;
import tlf.snake.main.game.tiles.Tile;
import tlf.snake.main.game.tiles.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author thislooksfun
 */
public class Game
{
	private static Game instance;
	
	private static final int tps = 10;
	private long nextTick = -1; 
	
	public final int gameWidth = 51;
	public final int gameHeight = 51;
	
	private final Tile[][] tiles = new Tile[gameWidth][gameHeight];
	
	private Snake snake;
	
	private Random rand = new Random();
	
	public Game()
	{
		instance = this;
		
		for (int x = 0; x < gameWidth; x++)
		{
			if (x == 0 || (x+1) == gameWidth) {
				for (int y = 0; y < gameHeight; y++)
					new Wall(x, y);
			} else {
				new Wall(x, 0);
				new Wall(x, gameHeight-1);
			}
		}
	}
	
	public static Game game()
	{
		return instance;
	}
	public Snake snake()
	{
		return snake;
	}
	
	public void start()
	{
		snake = new Snake();
		genFood();
	}
	
	public void tick()
	{
		long t = System.currentTimeMillis(); 
		if (t > nextTick)
		{
			snake.update();
			nextTick = t + (1000 / tps);
		}
	}
	
	public void genFood()
	{
		new Food(findRandomClearPos());
	}
	
	private Pos findRandomClearPos()
	{
		List<Pos> clear = new ArrayList<>();
		
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[x].length; y++)
				if (tiles[x][y] == null)
					clear.add(new Pos(x, y));
		
		return clear.get(rand.nextInt(clear.size()));
	}
	
	public boolean addTile(Tile t)
	{
		Pos p = t.pos();
		if (tiles[p.x][p.y] != null) return false;
		
		tiles[p.x][p.y] = t;
		return true;
	}
	public void removeTile(Tile t)
	{
		Pos p = t.pos();
		tiles[p.x][p.y] = null;
	}
	
	public void render()
	{
		for (Tile[] row : tiles)
			for (Tile t : row)
				if (t != null)
					t.draw();
		
		//Text.draw("Hi!", 10, 10);
	}
	
	public void lose()
	{
		System.out.println("You lose!");
		SnakeGame.instance().stop();
	}
	
	public Tile tileAt(Pos p)
	{
		return tiles[p.x][p.y];
	}
	
	public boolean isOccupied(Pos p)
	{
		return tileAt(p) != null;
	}
	
	private static int clamp(int x, int min, int max)
	{
		if (x < min) return min;
		else if (x > max) return max;
		
		return x;
	}
}