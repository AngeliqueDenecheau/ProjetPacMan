public class StrategieMultijoueurs extends Strategie{
	private int _actionPacman = 4;
	private int _actionGhost = 4;
	private boolean _pacmanTurn = true;
	
	@Override
	public AgentAction getAction(Agent agent, Maze maze) {
		int action = (agent instanceof Pacman) ? _actionPacman : _actionGhost;
		if(action == 4) return new AgentAction(4);
		action = (isLegalMove(agent, new AgentAction(action), maze)) ? action : 4;
		_pacmanTurn = !_pacmanTurn;
		_actionPacman = 4;
		_actionGhost = 4;
		return new AgentAction(action);
	}
	
	public void setActionPacman(int code) {
		_actionPacman = code;
	}
	
	public void setActionGhost(int code) {
		_actionGhost = code;
	}
	
	public boolean getPacmanTurn() {return _pacmanTurn;};
	
	public void setPacmanTurn(boolean b) {_pacmanTurn = b;}
}
