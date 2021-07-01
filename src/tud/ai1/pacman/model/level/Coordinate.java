package tud.ai1.pacman.model.level;

/**
 * Coordinate x, y. 
 *
 * @author 
 */
public class Coordinate implements ICoordinate{

	// a)
	private int x;
	private int y;
	
	// b)
	/**
	 * @param x, y coordinates. 
	 * */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
		
	}

	
	
	// c)
	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(final int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(final int y) {
		this.y = y;
	}

}
