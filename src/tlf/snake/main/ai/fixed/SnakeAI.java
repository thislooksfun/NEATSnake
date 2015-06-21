package tlf.snake.main.ai.fixed;

import tlf.snake.main.Game;
import tlf.snake.main.ai.base.SnakeAIBase;
import tlf.snake.main.game.entity.Snake;
import tlf.snake.main.game.helper.Pos;

/**
 * @author thislooksfun
 */
public class SnakeAI extends SnakeAIBase
{
	public SnakeAI(Snake snake)
	{
		super(snake);
	}
	
	@Override
	public void tick()
	{
		Snake.Vector v = this.snake.dir();
		Pos p = this.snake.getHeadPos();
		int w = Game.game().gameWidth-2;
		int h = Game.game().gameHeight-2;
		
		switch (v) {
			case NULL:
				this.setDirection(Snake.Vector.UP);
				break;
			case DOWN:
				if (p.y == h) this.setDirection(Snake.Vector.RIGHT);
				break;
			case RIGHT:
				if (p.x == w) this.setDirection(Snake.Vector.UP);
				break;
			case LEFT:
				if (p.x == 1)
					this.setDirection(Snake.Vector.DOWN);
				else if (p.y > 1 && p.x == 2)
					this.setDirection(Snake.Vector.UP);
				break;
			case UP:
				this.setDirection(p.y % 2 == 0 ? Snake.Vector.RIGHT : Snake.Vector.LEFT);
		}
	}
	
	@Override
	public boolean restart() {
		return true;
	}
}