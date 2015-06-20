package tlf.snake.main.game.helper;

/**
 * @author thislooksfun
 */
public class Color
{
	public static final Color BLACK = new Color(0,   0,   0);
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color RED =   new Color(255, 0,   0);
	public static final Color GREEN = new Color(0,   255, 0);
	public static final Color BLUE =  new Color(0,   0,   255);
	
	public final int red;
	public final int green;
	public final int blue;
	public final int transp;
	
	public Color(int r, int g, int b)
	{
		this.red =    clampColor(r);
		this.green =  clampColor(g);
		this.blue =   clampColor(b);
		this.transp = 255;
	}
	
	public Color(int r, int g, int b, int t)
	{
		this.red =    clampColor(r);
		this.green =  clampColor(g);
		this.blue =   clampColor(b);
		this.transp = clampColor(t);
	}
	
	public Color(int color)
	{
		this.transp = clampColor(color & 0xFF000000);
		this.red =    clampColor(color & 0x00FF0000);
		this.green =  clampColor(color & 0x0000FF00);
		this.blue =   clampColor(color & 0x000000FF);
	}
	
	public static Color fromHex(String hex)
	{
		int r, g, b, t;
		switch (hex.length()) {
			case 3:
				r = Integer.parseInt(hex.substring(0,1)+hex.substring(0,1), 16);
				g = Integer.parseInt(hex.substring(1,2)+hex.substring(1,2), 16);
				b = Integer.parseInt(hex.substring(2,3)+hex.substring(2,3), 16);
				return new Color(r, g, b);
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
				return new Color(r, g, b);
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
	
	private static int clampColor(int c)
	{
		return clamp(c, 0, 255);
	}
	
	private static int clamp(int x, int min, int max)
	{
		if (x < min) return min;
		else if (x > max) return max;
		
		return x;
	}
	
	public Color adjustShade(int red, int green, int blue, int transp)
	{
		return new Color(this.red+red, this.green+green, this.blue+blue, this.transp+transp);
	}
}
