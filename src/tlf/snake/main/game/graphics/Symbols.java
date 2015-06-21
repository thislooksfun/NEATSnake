package tlf.snake.main.game.graphics;

import tlf.snake.main.game.helper.Color;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author thislooksfun
 */
public class Symbols
{
	public static void drawArrow(float x, float y, float base, float height, Direction dir) { drawArrow(x, y, base, height, dir, Color.WHITE); }
	public static void drawArrow(float x, float y, float base, float height, Direction dir, Color c)
	{
		glPushMatrix();
		{
			c.bind();
			glTranslatef(x, y, 0);
			
			
			switch (dir) {
				case DOWN:
					glRotatef(180, 1, 0, 0);
				case UP:
					glTranslatef(-base/2, -height/2, 0);
					break;
				case LEFT:
					glRotatef(180, 0, 1, 0);
				case RIGHT:
					glTranslatef(-height/2, -base/2, 0);
					break;
			}
			
			glBegin(GL_TRIANGLES);
			{
				switch (dir) {
					case DOWN:
					case UP:
						glVertex2d(0, height);
						glVertex2d(base/2, 0);
						glVertex2d(base, height);
						break;
					case RIGHT:
					case LEFT:
						glVertex2d(0, 0);
						//noinspection SuspiciousNameCombination
						glVertex2d(height, base/2);
						glVertex2d(0, base);
						break;
				}
			}
			glEnd();
		}
		glPopMatrix();
	}
	
	public enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
}