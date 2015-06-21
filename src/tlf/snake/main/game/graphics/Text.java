package tlf.snake.main.game.graphics;

import tlf.snake.main.game.helper.Color;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBEasyFont.*;

/**
 * @author thislooksfun
 */
public class Text
{
	public static void drawCenter(String s, int centX, int centY) { drawCenter(s, centX, centY, 10, Color.WHITE); }
	public static void drawCenter(String s, int centX, int centY, int scale) { drawCenter(s, centX, centY, scale, Color.WHITE); }
	public static void drawCenter(String s, int centX, int centY, Color c) { drawCenter(s, centX, centY, 10, c); }
	public static void drawCenter(String s, int centX, int centY, int scale, Color c) { draw(s, centX + scale - (stb_easy_font_width(s) * scale) / 4, centY, scale, c); }
	
	public static void draw(String s, int x, int y) { draw(s, x, y, 10, Color.WHITE); }
	public static void draw(String s, int x, int y, int scale) { draw(s, x, y, scale, Color.WHITE); }
	public static void draw(String s, int x, int y, Color c) { draw(s, x, y, 10, c); }
	public static void draw(String s, int x, int y, int scale, Color c)
	{
		scale /= 2;
		
		glEnableClientState(GL_VERTEX_ARRAY);
		ByteBuffer charBuffer = BufferUtils.createByteBuffer(s.length() * 270);
		int quads = stb_easy_font_print(0, 0, s, null, charBuffer);
		glVertexPointer(2, GL_FLOAT, 16, charBuffer);
		
		glPushMatrix();
		{
			c.bind();
			glTranslatef(x, y, 0);
			glScalef(scale, scale, 1f);
			glDrawArrays(GL_QUADS, 0, quads * 4);
		}
		glPopMatrix();
		glDisableClientState(GL_VERTEX_ARRAY);
	}
}