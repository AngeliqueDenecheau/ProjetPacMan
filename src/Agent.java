//Classe abstraite représentant les différents agents : Pacman ou Fantôme

public abstract class Agent {
	private PositionAgent _position;
	
	public Agent(PositionAgent position) {
		_position = position;
	}
	
	public PositionAgent getPosition() {
		return _position;
	}
	
	public void setPosition(PositionAgent position) {
		_position = position;
	}
	
}
