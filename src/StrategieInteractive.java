import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StrategieInteractive extends Strategie {
	private int _action = 4;

	@Override
	public AgentAction getAction(Agent agent, Maze maze) {
		if(agent instanceof Pacman) {
			if(isLegalMove(agent, new AgentAction(_action), maze)) return new AgentAction(_action);
			return new AgentAction(4);
		}
		return StrategieAleatoire.getActionAleatoire(agent, maze);
	}

	@Override
	protected void keyPressed(int code) {
		switch(code) {
		case KeyEvent.VK_UP:
			System.out.println("Up");
			_action = 0;
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("Down");
			_action = 1;
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("Right");
			_action = 2;
			break;
		case KeyEvent.VK_LEFT:
			System.out.println("Left");
			_action = 3;
			break;
		default:
			System.out.println("Default");
			_action = 4;
			break;
	}
	}
}
