package tlf.snake.main;


import tlf.snake.main.game.helper.Pos;
import tlf.snake.main.game.input.Input;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GLContext;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author thislooksfun
 */
public class SnakeGame
{
	private static final Game game = new Game();
	
	private int WIDTH = game.gameWidth*15;
	private int HEIGHT = (game.gameHeight*15) + 45;
	
	private static GLFWKeyCallback keyCallback;
	
	private Pos lastPos;
	
	private int size = 10;
	private boolean resized = false;
	
	private boolean running = false;
	
	private long window;
	
	private static SnakeGame instance;
	
	public SnakeGame()
	{
		instance = this;
	}
	
	private void init()
	{
		if (glfwInit() != GL_TRUE) {
			System.out.println("Error initializing");
			return;
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		window = glfwCreateWindow(WIDTH, HEIGHT, "NEAT Snake", NULL, NULL);
		
		if (window == NULL) {
			System.out.println("Error creating window");
			return;
		}
		
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - WIDTH) / 2, (GLFWvidmode.height(vidmode) - HEIGHT) / 2);
		initCallbacks();
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		GLContext.createFromCurrent();
		
		glClearColor(0f, 0f, 0f, 1f);
		glDepthFunc(GL_NEVER);
		glDisable(GL_DEPTH_TEST);
		
		setOrtho();
		glDisable(GL_TEXTURE_2D);
		
		running = true;
		resized = true;
		
		game.init();
	}
	
	private void initCallbacks()
	{
		glfwSetKeyCallback(window, keyCallback = new Input());
	}
	
	private Pos getWindowPos()
	{
		IntBuffer xbuf = BufferUtils.createIntBuffer(4);
		IntBuffer ybuf = BufferUtils.createIntBuffer(4);
		glfwGetWindowPos(window, xbuf, ybuf);
		
		return new Pos(xbuf.get(0), ybuf.get(0));
	}
	
	private void setOrtho()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT-45, -45, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public void start()
	{
		init();
		while (running)
		{
			update();
			render();
			if (glfwWindowShouldClose(window) == GL_TRUE) {
				running = false;
			}
		}
	}
	
	private void exit()
	{
		
		glfwDestroyWindow(window);
	}
	
	private void update()
	{
		glfwPollEvents();
		game.tick();
	}
	
	private void render()
	{
		if (resized) {
			setOrtho();
			calcTileSize();
			resized = false;
		}
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		game.render();
		
		glfwSwapBuffers(window);
	}
	
	public static SnakeGame instance()
	{
		return instance;
	}
	
	private void calcTileSize()
	{
		int sh, sw;
		sh = (int)Math.floor((1.0f*HEIGHT)/game.gameHeight);
		sw = (int)Math.floor((1.0f*WIDTH)/game.gameWidth);
		
		size = sh > sw ? sw : sh;
	}
	
	public int tileSize()
	{
		return size;
	}
	public int width()
	{
		return WIDTH;
	}
	public int height()
	{
		return HEIGHT;
	}
	
	public void stop()
	{
		running = false;
	}
}