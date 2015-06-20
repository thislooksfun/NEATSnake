package tlf.snake.main.game.helper;

import tlf.snake.main.game.graphics.Texture;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author thislooksfun
 */
public class Text
{
	private static Texture text;
	
	public static void init()
	{
		text = new Texture("resources/textures/ascii.png");
	}
	
	public static void draw(String s, int x, int y)
	{
		draw(s, x, y, 24);
	}
	public static void draw(String s, int x, int y, int scale)
	{
		text.bind();
		glPushMatrix();
		
		for (char c : s.toCharArray()) {
			drawChar(c, x, y, scale);
			x += scale;
		}
		
		glPopMatrix();
		text.unbind();
	}
	
	private static void drawChar(char c, int x, int y, int scale)
	{
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		{
			glVertex2d(0, 0);
			glVertex2d(0, scale);
			glVertex2d(scale, scale);
			glVertex2d(scale, 0);
		}
		glEnd();
	}
}