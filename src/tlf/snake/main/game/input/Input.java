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
	private static final long[] keys = new long[4];
	private static boolean mode = true; 
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods)
	{
		if (action != GLFW.GLFW_RELEASE) {
			if (key == GLFW.GLFW_KEY_A)
				Game.game().toggleAI();
			else if (key == GLFW.GLFW_KEY_R)
				Game.game().restart();
			else if (key == GLFW.GLFW_KEY_M)
				toggleMode();
			else
				keyPress(Key.fromCode(key));
		}
	}
	
	public static void keyPress(Key k)
	{
		if (k == null) return;
		
		Snake snake = Game.game().snake();
		if (mode)
			snake.setDir(k.toVector());
		else {
			if (k == Key.LEFT) {
				switch (snake.dir()) {
					case NULL:
					case UP:
						snake.setDir(Snake.Vector.LEFT);
						break;
					case LEFT:
						snake.setDir(Snake.Vector.DOWN);
						break;
					case DOWN:
						snake.setDir(Snake.Vector.RIGHT);
						break;
					case RIGHT:
						snake.setDir(Snake.Vector.UP);
						break;
				}
			} else if (k == Key.RIGHT) {
				switch (snake.dir()) {
					case NULL:
					case UP:
						snake.setDir(Snake.Vector.RIGHT);
						break;
					case RIGHT:
						snake.setDir(Snake.Vector.DOWN);
						break;
					case DOWN:
						snake.setDir(Snake.Vector.LEFT);
						break;
					case LEFT:
						snake.setDir(Snake.Vector.UP);
						break;
				}
			}
		}
		
		keys[k.ordinal()] = System.currentTimeMillis() + 250;
	}
	
	public static boolean isPressed(Key k) {
		return k != null && k.ordinal() < 4 && keys[k.ordinal()] > System.currentTimeMillis();
	}
	
	public static boolean mode() { return mode; }
	public static void toggleMode() { mode = !mode; }
	
	public enum Key {
		UP(GLFW.GLFW_KEY_UP),
		DOWN(GLFW.GLFW_KEY_DOWN),
		LEFT(GLFW.GLFW_KEY_LEFT),
		RIGHT(GLFW.GLFW_KEY_RIGHT);
		
		public final int keycode;
		Key(int code) {
			this.keycode = code;
		}
		
		public static Key fromVector(Snake.Vector v) {
			switch (v) {
				case UP:
					return UP;
				case DOWN:
					return DOWN;
				case LEFT:
					return LEFT;
				case RIGHT:
					return RIGHT;
				default:
					return null;
			}
		}
		
		public Snake.Vector toVector() {
			switch (this) {
				case UP:
					return Snake.Vector.UP;
				case DOWN:
					return Snake.Vector.DOWN;
				case LEFT:
					return Snake.Vector.LEFT;
				case RIGHT:
					return Snake.Vector.RIGHT;
				default:
					return Snake.Vector.NULL;
			}
		}
		
		public static Key fromCode(int code) {
			switch (code) {
				case GLFW.GLFW_KEY_UP:
					return UP;
				case GLFW.GLFW_KEY_DOWN:
					return DOWN;
				case GLFW.GLFW_KEY_LEFT:
					return LEFT;
				case GLFW.GLFW_KEY_RIGHT:
					return RIGHT;
				default:
					return null;
			}
		}
	}
}