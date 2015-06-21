package tlf.snake.main.ai.base;

import tlf.snake.main.game.entity.Snake;
import tlf.snake.main.game.input.Input;

/**
 * @author thislooksfun
 */
public abstract class SnakeAIBase
{
	protected Snake snake;
	
	public SnakeAIBase(Snake snake)
	{
		this.snake = snake;
	}
	
	public abstract void tick();
	public abstract boolean restart();
	
	protected void setDirection(Snake.Vector v)
	{
		if (!Input.mode()) Input.toggleMode();
		Input.keyPress(Input.Key.fromVector(v));
	}
}