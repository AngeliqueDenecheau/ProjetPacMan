
public abstract class Agent {
	private int _x;
	private int _y;
	private int _direction;
	
	public Agent(int x, int y, int direction) {
		_x = x;
		_y = y;
		_direction = direction;
	}
	
	public void setX(int x) {
		_x = x;
	}
	
	public int getX() {
		return _x;
	}
	
	public void setY(int y) {
		_y = y;
	}
	
	public int getY() {
		return _y;
	}
	
	public void setDirection(int direction) {
		_direction = direction;
	}
	
	public int getDirection() {
		return _direction;
	}
}
