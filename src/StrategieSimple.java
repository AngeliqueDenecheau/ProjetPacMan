import java.awt.event.KeyEvent;
import java.util.ArrayList;

//Classe représentant une stratégie simple de déplacement des agents :
//Le Pacman se déplace sur la première case adjacente contenant une unité de nourriture s'il en trouve une, sinon -> déplacement aléatoire
//Le Fantôme se déplace aléatoirement

public class StrategieSimple extends Strategie {

	@Override
	public AgentAction getAction(Agent agent, Maze maze) {
		if(agent instanceof Pacman) {
			int[] directions = {0, 1, 2, 3};
			for(int i : directions) {
				AgentAction action = new AgentAction(directions[i]);
				if(isLegalMove(agent, action, maze) && maze.isFood(agent.getPosition().getX() + action.get_vx(), agent.getPosition().getY() + action.get_vy())) return new AgentAction(i);
			}
		}
		return StrategieAleatoire.getActionAleatoire(agent, maze);
	}

	@Override
	protected void keyPressed(int code) {}

	@Override
	public Turn getTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTurn(Turn turn) {
		// TODO Auto-generated method stub
		
	}
}
