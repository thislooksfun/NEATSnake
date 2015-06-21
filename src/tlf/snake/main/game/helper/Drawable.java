package tlf.snake.main.game.helper;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author thislooksfun
 */
public abstract class Drawable
{
	public abstract void draw();
	
	public static void drawRect(float x, float y, float width, float height, Color c)
	{
		glPushMatrix();
		{
			c.bind();
			glTranslatef(x, y, 0);
			
			glBegin(GL_QUADS);
			{
				glVertex2f(0, 0);
				glVertex2f(width, 0);
				glVertex2f(width, height);
				glVertex2f(0, height);
			}
			glEnd();
		}
		glPopMatrix();
	}
	
	public static void drawLine(float x1, float y1, float x2, float y2, Color c)
	{
		glPushMatrix();
		{
			c.bind();
			glBegin(GL_LINES);
			
			glVertex2d(x1, y1);
			glVertex2d(x2, y2);
			
			glEnd();
		}
		glPopMatrix();
	}
}
