import java.awt.event.KeyEvent;

//Classe abstraite représentant les différentes stratégies de déplacement des agents

public abstract class Strategie {
	
	public abstract AgentAction getAction(Agent agent, Maze maze);
	
	public static boolean isLegalMove(Agent agent, AgentAction action, Maze maze) {
		return !maze.isWall(agent.getPosition().getX() + action.get_vx(), agent.getPosition().getY() + action.get_vy());
	}

	protected abstract void keyPressed(int code);
	
	public abstract Turn getTurn();
	public abstract void setTurn(Turn turn);
}
