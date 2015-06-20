package tlf.snake.main.game.entity;

import tlf.snake.main.Game;
import tlf.snake.main.game.helper.Pos;
import tlf.snake.main.game.tiles.Food;
import tlf.snake.main.game.tiles.SnakePart;
import tlf.snake.main.game.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thislooksfun
 */
public class Snake implements Entity
{
	public final List<SnakePart> parts = new ArrayList<>();
	
	private Vector dir = null;
	private Vector nextDir = null;
	
	private int size = 3;
	
	public Snake()
	{
		parts.add(new SnakePart(Math.round(Game.game().gameHeight/2), Math.round(Game.game().gameWidth/2)));
	}
	
	public void setDir(Vector newDir)
	{
		if (dir != null && newDir != null && (dir.x + newDir.x == 0 || dir.y + newDir.y == 0)) return; // Can't go backwards
		else nextDir = newDir;
	}
	
	@Override
	public void update()
	{
		if (nextDir != null) {
			dir = nextDir;
			nextDir = null;
		}
		if (dir == null) return;
		
		Pos headPos = parts.get(0).pos();
		Pos p = new Pos(headPos.x + dir.x, headPos.y + dir.y);
		
		Game g = Game.game();
		Tile t = g.tileAt(p);
		if (t != null) {
			if (t instanceof Food) {
				size++;
				t.destroy();
				g.genFood();
			} else {
				g.lose();
				return;
			}
		}
		
		if (parts.size() >= size)
			parts.remove(parts.size()-1).destroy();
		parts.add(0, new SnakePart(p));
	}
	
	public Pos getHeadPos()
	{
		return this.parts.get(0).pos();
	}
	
	public enum Vector {
		LEFT(-1,0),
		RIGHT(1,0),
		UP(0,-1),
		DOWN(0,1);
		
		public final int x, y;
		Vector(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
}
