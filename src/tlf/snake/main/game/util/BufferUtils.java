package tlf.snake.main.game.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * @author thislooksfun
 */
public class BufferUtils
{
	public static IntBuffer createIntBuffer(int[] array)
	{
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();
		return result;
	}
}