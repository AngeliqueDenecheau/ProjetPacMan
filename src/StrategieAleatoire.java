//Classe représentant la stratégie de déplacement aléatoire des agents

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StrategieAleatoire extends Strategie {
	
	public AgentAction getAction(Agent agent, Maze maze) {
		return getActionAleatoire(agent, maze);
	}

	public static AgentAction getActionAleatoire(Agent agent, Maze maze) {
		List<Integer> directions = new ArrayList<Integer>();
		directions.add(0);
		directions.add(1);
		directions.add(2);
		directions.add(3);
		Collections.shuffle(directions);
		while(!directions.isEmpty()) {
			AgentAction action = new AgentAction(directions.get(0));
			if(isLegalMove(agent, action, maze)) {
				return action;
			}
			directions.remove(0);
		}
		return new AgentAction(4);
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
