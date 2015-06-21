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
	
	private Vector dir = Vector.NULL;
	private Vector nextDir = Vector.NULL;
	
	private int size = 4;
	
	private boolean dead = false;
	private int deathState = 0;
	
	public Snake()
	{
		parts.add(new SnakePart(Math.round(Game.game().gameHeight / 2), Math.round(Game.game().gameWidth / 2), dir));
	}
	
	@Override
	public void update()
	{
		if (dead) {
			if (deathState < 3)
				deathState++;
			return;
		}
		
		Vector oldDir = dir;
		if (nextDir != Vector.NULL) {
			
			if (!Game.game().started()) Game.game().start();
			
			dir = nextDir;
			nextDir = Vector.NULL;
		}
		
		Tile t = parts.remove(0);
		t.destroy();
		parts.add(0, new SnakePart(t.pos(), oldDir.inverse(), dir));
		
		if (dir == Vector.NULL) return;
		
		Pos headPos = parts.get(0).pos();
		Pos p = new Pos(headPos.x + dir.x, headPos.y + dir.y);
		
		Game g = Game.game();
		t = g.tileAt(p);
		boolean needsFoodGen = false;
		if (t != null) {
			if (t instanceof Food) {
				size += 3;
				foodCollected++;
				t.destroy();
				needsFoodGen = true;
			} else {
				g.lose();
				return;
			}
		}
		
		if (parts.size() >= size)
			parts.remove(parts.size()-1).destroy();
		parts.add(0, new SnakePart(p, dir.inverse(), Vector.NULL));
		
		if (needsFoodGen) g.genFood();
	}
	private int foodCollected = 0;
	public void setDir(Vector newDir)
	{
		if (dir != Vector.NULL && newDir != null && newDir != Vector.NULL && (dir.x + newDir.x == 0 || dir.y + newDir.y == 0)) return; // Can't go backwards
		nextDir = newDir;
	}
	public Vector dir()
	{
		return this.dir;
	}
	public Pos getHeadPos()
	{
		return this.parts.get(0).pos();
	}
	public void die()
	{
		System.out.println(this.foodCollected);
		this.dead = true;
	}
	public boolean dead()
	{
		return this.dead;
	}
	public int deathState()
	{
		return this.deathState;
	}
	public int length()
	{
		return this.parts.size();
	}
	public int score()
	{
		return Math.max((this.size - 4)/3, 0);
	}
	
	public enum Vector {
		NULL(0,0),
		LEFT(-1,0),
		RIGHT(1,0),
		UP(0,-1),
		DOWN(0,1);
		
		public final int x, y;
		
		Vector(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public Vector inverse() {
			switch (this) {
				case LEFT:
					return RIGHT;
				case RIGHT:
					return LEFT;
				case UP:
					return DOWN;
				case DOWN:
					return UP;
				default:
					return NULL;
			}
		}
	}
}
