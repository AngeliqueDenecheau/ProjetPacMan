import java.awt.event.KeyEvent;

enum Turn {
	PACMAN,
	GHOST
}

public class StrategieMultijoueurs extends Strategie{
	private int _actionPacman = 4;
	private int _actionGhost = 4;
	private Turn _turn = Turn.PACMAN;
	
	@Override
	public AgentAction getAction(Agent agent, Maze maze) {
		System.out.println("Test");
		if((_turn == Turn.PACMAN && agent instanceof Pacman && _actionPacman != 4) || (_turn == Turn.GHOST && agent instanceof Ghost && _actionGhost != 4)) {
			/*int actionNum = (_turn == Turn.PACMAN) ? _actionPacman : _actionGhost;
			AgentAction action = new AgentAction((isLegalMove(agent, new AgentAction(actionNum), maze)) ? actionNum : 4);
			if(action.get_direction() != 4) _turn = (_turn == Turn.PACMAN) ? Turn.GHOST : Turn.PACMAN;
			_actionGhost = 4;
			_actionPacman = 4;
			return action;*/
			int action = 4;
			if(_turn == Turn.PACMAN) {
				action = _actionPacman;
				_turn = Turn.GHOST;
			}else {
				action = _actionGhost;
				_turn = Turn.PACMAN;
			}
			_actionPacman = 4;
			_actionGhost = 4;
			if(isLegalMove(agent, new AgentAction(action), maze)) return new AgentAction(action);
			return new AgentAction(4);
		}
		return new AgentAction(4);
	}
	
	@Override
	protected void keyPressed(int code) {
		switch(code) {
			case KeyEvent.VK_UP:
				System.out.println("Up");
				_actionPacman = 0;
				break;
			case KeyEvent.VK_DOWN:
				System.out.println("Down");
				_actionPacman = 1;
				break;
			case KeyEvent.VK_RIGHT:
				System.out.println("Right");
				_actionPacman = 2;
				break;
			case KeyEvent.VK_LEFT:
				System.out.println("Left");
				_actionPacman = 3;
				break;
			case KeyEvent.VK_Z:
				System.out.println("Z");
				_actionGhost = 0;
				break;
			case KeyEvent.VK_S:
				System.out.println("S");
				_actionGhost = 1;
				break;
			case KeyEvent.VK_D:
				System.out.println("D");
				_actionGhost = 2;
				break;
			case KeyEvent.VK_Q:
				System.out.println("Q");
				_actionGhost = 3;
				break;
			default:
				System.out.println("Default");
				_actionGhost = 4;
				_actionPacman = 4;
				break;
		}
	}
	
	public Turn getTurn() {return _turn;};
	public void setTurn(Turn turn) {_turn = turn;};
	
}
