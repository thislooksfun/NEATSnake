package tlf.snake.main;


import tlf.snake.main.game.helper.Pos;
import tlf.snake.main.game.helper.Text;
import tlf.snake.main.game.input.Input;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;
import static org.lwjgl.glfw.Callbacks.glfwSetCallback;
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
	private int HEIGHT = game.gameHeight*15;
	
	private static final int MINWIDTH = game.gameWidth*10;
	private static final int MINHEIGHT = game.gameHeight*10;
	
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
			//TODO handle
			return;
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		window = glfwCreateWindow(WIDTH, HEIGHT, "NEAT Snake", NULL, NULL);
		
		if (window == NULL) {
			System.out.println("Error creating window");
			//TODO handle
			return;
		}
		
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - WIDTH) / 2, (GLFWvidmode.height(vidmode) - HEIGHT) / 2);
		initCallbacks();
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		GLContext.createFromCurrent();
		
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glDepthFunc(GL_NEVER);
		glDisable(GL_DEPTH_TEST);
		
		setOrtho();
		glDisable(GL_TEXTURE_2D);
		
		running = true;
		resized = true;
		
		Text.init();
		game.start();
	}
	
	private void initCallbacks()
	{
		glfwSetCallback(window, GLFWWindowSizeCallback(new GLFWWindowSizeCallback.SAM()
		{
			@Override
			public void invoke(long window, int width, int height)
			{
				resized = true;
				WIDTH = width;
				HEIGHT = height;
				
				if (width < MINWIDTH && height >= MINHEIGHT) {
					glfwSetWindowSize(window, MINWIDTH, height);
					glfwSetWindowPos(window, lastPos.x-10, lastPos.y);
				} else if (width >= MINWIDTH && height < MINHEIGHT) {
					glfwSetWindowSize(window, width, MINHEIGHT);
					glfwSetWindowPos(window, lastPos.x-10, lastPos.y);
				} else if (width < MINWIDTH && height < MINHEIGHT) {
					glfwSetWindowSize(window, MINWIDTH, MINHEIGHT);
					glfwSetWindowPos(window, lastPos.x-10, lastPos.y);
				} else
					lastPos = getWindowPos();
				
				render();
				setOrtho();
			}
		}));
		
		glfwSetCallback(window, GLFWWindowPosCallback(new GLFWWindowPosCallback.SAM()
		{
			@Override
			public void invoke(long window, int xpos, int ypos)
			{
				lastPos = new Pos(xpos, ypos);
			}
		}));
		
		glfwSetKeyCallback(window, new Input());
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
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
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
	
	public void stop()
	{
		running = false;
	}
}