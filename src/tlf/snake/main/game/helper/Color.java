package tlf.snake.main.game.helper;

import tlf.snake.main.game.util.MathUtil;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author thislooksfun
 */
public class Color
{
	public static final Color BLACK =   gray(0);
	public static final Color WHITE =   gray(255);
	public static final Color RED =     rgb(255, 0, 0);
	public static final Color GREEN =   rgb(0, 255, 0);
	public static final Color BLUE =    rgb(0, 0, 255);
	public static final Color GRAYOUT = gray(70);
	
	public final int red;
	public final int green;
	public final int blue;
	public final int alpha;
	
	private Color(int r, int g, int b, int t)
	{
		this.red =    clampColor(r);
		this.green =  clampColor(g);
		this.blue =   clampColor(b);
		this.alpha = clampColor(t);
	}
	
	public static Color rgb(int r, int g, int b) { return rgba(r, g, b, 255); }
	public static Color rgba(int r, int g, int b, int a) { return new Color(r, g, b, a); }
	public static Color gray(int gray) { return gray(gray, 255); }
	public static Color gray(int gray, int a)  { return new Color(gray, gray, gray, a); }
	
	public static Color fromHex(String hex)
	{
		int r, g, b, t;
		switch (hex.length()) {
			case 3:
				r = Integer.parseInt(hex.substring(0,1)+hex.substring(0,1), 16);
				g = Integer.parseInt(hex.substring(1,2)+hex.substring(1,2), 16);
				b = Integer.parseInt(hex.substring(2,3)+hex.substring(2,3), 16);
				return new Color(r, g, b, 255);
			case 4:
				r = Integer.parseInt(hex.substring(0,1)+hex.substring(0,1), 16);
				g = Integer.parseInt(hex.substring(1,2)+hex.substring(1,2), 16);
				b = Integer.parseInt(hex.substring(2,3)+hex.substring(2,3), 16);
				t = Integer.parseInt(hex.substring(3,4)+hex.substring(3,4), 16);
				return new Color(r, g, b, t);
			case 6:
				r = Integer.parseInt(hex.substring(0,2), 16);
				g = Integer.parseInt(hex.substring(2,4), 16);
				b = Integer.parseInt(hex.substring(4,6), 16);
				return new Color(r, g, b, 255);
			case 8:
				r = Integer.parseInt(hex.substring(0,2), 16);
				g = Integer.parseInt(hex.substring(2,4), 16);
				b = Integer.parseInt(hex.substring(4,6), 16);
				t = Integer.parseInt(hex.substring(6,8), 16);
				return new Color(r, g, b, t);
			default:
				throw new IllegalArgumentException(hex+" is not a valid hex number");
		}
	}
	
	private static int clampColor(int c) { return MathUtil.clamp(c, 0, 255); }
	
	public Color adjustShade(int red, int green, int blue, int alpha) {
		return new Color(this.red+red, this.green+green, this.blue+blue, this.alpha+alpha);
	}
	
	/** Binds this color to the current GL context, enabling transparency as needed */
	public void bind()
	{
		if (this.alpha < 255) {
			//Enable transparency support
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
		glColor4f(this.red / 255.0f, this.green / 255.0f, this.blue / 255.0f, this.alpha / 255.0f); //Set color
	}
}
