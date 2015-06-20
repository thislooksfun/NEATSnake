package tlf.snake.main.game.helper;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author thislooksfun
 */
public abstract class Drawable
{
	public abstract void draw();
	
	protected void drawRect(int x, int y, int height, int width, Color c)
	{
		glPushMatrix();
		if (c.transp > 0) glEnable(GL_BLEND);
		
		glColor4f(c.red / 255.0f, c.green / 255.0f, c.blue / 255.0f, c.transp / 255.0f);
		
		glTranslatef(x, y, 0);
		
		glBegin(GL_QUADS);
		{
			glVertex2i(0, 0);
			glVertex2i(height, 0);
			glVertex2i(height, width);
			glVertex2i(0, width);
		}
		glEnd();
		
		if (c.transp > 0) glDisable(GL_BLEND);
		glPopMatrix();
	}
	
	protected void drawLine(int x1, int y1, int x2, int y2, Color c)
	{
		glPushMatrix();
		if (c.transp > 0) glEnable(GL_BLEND);
		
		glColor4f(c.red, c.green, c.blue, c.transp);
		glBegin(GL_LINES);
		
		glVertex2d(x1, y1);
		glVertex2d(x2, y2);
		
		glEnd();
		
		if (c.transp > 0) glDisable(GL_BLEND);
			glPopMatrix();
	}
}
