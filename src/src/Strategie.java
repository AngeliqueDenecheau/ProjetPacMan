import java.awt.event.KeyEvent;

//Classe abstraite représentant les différentes stratégies de déplacement des agents

public abstract class Strategie {
	
	public Game _game;
	
	public abstract AgentAction getAction(Agent agent, Maze maze);
	
	public static boolean isLegalMove(Agent agent, AgentAction action, Maze maze) {
		return !maze.isWall(agent.getPosition().getX() + action.get_vx(), agent.getPosition().getY() + action.get_vy());
	}
	
	public void setGame(Game game) {
		this._game = game;
	}

	protected abstract void keyPressed(int code);
}
