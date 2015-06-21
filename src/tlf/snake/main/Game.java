package tlf.snake.main;

import tlf.snake.main.ai.base.SnakeAIBase;
import tlf.snake.main.ai.fixed.SnakeAI;
import tlf.snake.main.game.entity.Snake;
import tlf.snake.main.game.graphics.Symbols;
import tlf.snake.main.game.graphics.Text;
import tlf.snake.main.game.helper.Color;
import tlf.snake.main.game.helper.Pos;
import tlf.snake.main.game.input.Input;
import tlf.snake.main.game.tiles.Food;
import tlf.snake.main.game.tiles.SnakePart;
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
	
	private static final int tps = 15;
	private static final int aitps = 300;
	
	private long nextTick = -1;
	
	public final int gameWidth = 60;
	public final int gameHeight = 60;
	
	private final Tile[][] tiles = new Tile[gameWidth][gameHeight];
	
	private Snake snake;
	private SnakeAIBase ai;
	
	private Random rand = new Random();
	
	private boolean started = false;
	
	public Game()
	{
		instance = this;
		
		for (int x = 0; x < gameWidth; x++)
		{
			if (x == 0 || (x + 1) == gameWidth)
			{
				for (int y = 0; y < gameHeight; y++)
					new Wall(x, y);
			} else
			{
				new Wall(x, 0);
				new Wall(x, gameHeight - 1);
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
	
	public void init()
	{
		snake = new Snake();
	}
	
	public void start()
	{
//		genFood();
//		genFood();
	
		for (int i = 0; i < 500; i++)
			genFood();
		
		started = true;
	}
	
	private final boolean[] pressed = new boolean[4];
	public void tick()
	{
		long t = System.currentTimeMillis();
		if (t > nextTick)
		{
			snake.update();
			if (ai != null) ai.tick();
			
			pressed[0] = Input.isPressed(Input.Key.UP);
			pressed[1] = Input.isPressed(Input.Key.DOWN);
			pressed[2] = Input.isPressed(Input.Key.LEFT);
			pressed[3] = Input.isPressed(Input.Key.RIGHT);
			
			nextTick = t + (1000 / (ai == null ? tps : aitps));
		}
	}
	
	public void genFood()
	{
		Pos p = findRandomClearPos();
		if (p != null)
			new Food(p);
	}
	
	private Pos findRandomClearPos()
	{
		List<Pos> clear = new ArrayList<>();
		
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[x].length; y++)
				if (tiles[x][y] == null)
					clear.add(new Pos(x, y));
		
		return clear.size() > 0 ? clear.get(rand.nextInt(clear.size())) : null;
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
		if (started) {
			if (snake.deathState() >= 3) end();
		}
		
		for (Tile[] row : tiles)
			for (Tile t : row)
				if (t != null && (!(t instanceof SnakePart) || !snake.dead() || snake.deathState() % 2 == 1))
					t.draw();
		
		
		Text.draw("Score:", 5, -40);
		
		int score = snake.score();
		int ones = score % 10;
		score /= 10;
		int tens = score % 10;
		score /= 10;
		int hundreds = score % 10;
		score /= 10;
		int thousands = score % 10;
		
		Text.draw(" "+thousands+" ", 155, -40);
		Text.draw(" "+hundreds+" ", 182, -40);
		Text.draw(" "+tens+" ", 208, -40);
		Text.draw(" "+ones+" ", 234, -40);
		
		if (!started) {
			if (snake.length() == 1) {
				Text.drawCenter("Press any direction",  SnakeGame.instance().width() / 2, (SnakeGame.instance().height() / 2) - 150);
				Text.drawCenter("to begin",             SnakeGame.instance().width() / 2, (SnakeGame.instance().height() / 2) - 100);
				Text.drawCenter("Press 'A' anytime",    SnakeGame.instance().width() / 2, (SnakeGame.instance().height() / 2) + 150);
				Text.drawCenter("to start the AI",      SnakeGame.instance().width() / 2, (SnakeGame.instance().height() / 2) + 200);
			} else {
				Text.drawCenter("You lost!",            SnakeGame.instance().width() / 2, (SnakeGame.instance().height() / 2) - 120);
				Text.drawCenter("Press 'R' to restart", SnakeGame.instance().width() / 2, (SnakeGame.instance().height() / 2) - 70);
			}
		}
		
		int width = SnakeGame.instance().width();
		
		Text.draw("AI", width - 50, -40, ai != null ? Color.WHITE : Color.GRAYOUT);
		
		if (Input.mode()) {
			Symbols.drawArrow(width-85,  -38, 18, 9, Symbols.Direction.UP,    pressed[0] ? Color.WHITE : Color.GRAYOUT);
			Symbols.drawArrow(width-85,   -8, 18, 9, Symbols.Direction.DOWN,  pressed[1] ? Color.WHITE : Color.GRAYOUT);
		}
		Symbols.drawArrow(width-100, -23, 18, 9, Symbols.Direction.LEFT, pressed[2] ? Color.WHITE : Color.GRAYOUT);
		Symbols.drawArrow(width-70,  -23, 18, 9, Symbols.Direction.RIGHT, pressed[3] ? Color.WHITE : Color.GRAYOUT);
	}
	
	public void lose()
	{
		System.out.println("You lose!");
		snake.die();
	}
	
	public void end()
	{
		started = false;
		
		if (ai != null && ai.restart()) {
			restart();
			toggleAI();
			start();
		}
	}
	
	public void clear()
	{
		for (Tile[] row : tiles)
		{
			for (Tile t : row)
			{
				if (t != null && !(t instanceof Wall)) t.destroy();
			}
		}
		
		snake = null;
		ai = null;
	}
	
	public void restart()
	{
		started = false;
		
		clear();
		init();
	}
	
	public Tile tileAt(Pos p)
	{
		return tiles[p.x][p.y];
	}
	
	public boolean isOccupied(Pos p)
	{
		return tileAt(p) != null;
	}
	
	public void toggleAI()
	{
		if (ai == null)
			setAI(new SnakeAI(snake));
		else
			ai = null;
	}
	
	public void setAI(SnakeAIBase ai)
	{
		this.ai = ai;
	}
	public boolean started()
	{
		return started;
	}
	
	private static int clamp(int x, int min, int max)
	{
		if (x < min) return min;
		else if (x > max) return max;
		
		return x;
	}
}