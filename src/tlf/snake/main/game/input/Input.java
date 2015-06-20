package tlf.snake.main.game.input;

import tlf.snake.main.Game;
import tlf.snake.main.game.entity.Snake;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

/**
 * @author thislooksfun
 */
public class Input extends GLFWKeyCallback
{
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods)
	{
		if (action != GLFW.GLFW_RELEASE) {
			switch (key) {
				case GLFW.GLFW_KEY_LEFT:
					Game.game().snake().setDir(Snake.Vector.LEFT);
					break;
				case GLFW.GLFW_KEY_RIGHT:
					Game.game().snake().setDir(Snake.Vector.RIGHT);
					break;
				case GLFW.GLFW_KEY_UP:
					Game.game().snake().setDir(Snake.Vector.UP);
					break;
				case GLFW.GLFW_KEY_DOWN:
					Game.game().snake().setDir(Snake.Vector.DOWN);
					break;
			}
		}
	}
}