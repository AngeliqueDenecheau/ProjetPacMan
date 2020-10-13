import java.util.ArrayList;

public class StrategieAvoid extends Strategie{ //strategy that get the PacMan pos and moves away
		
	@Override
	public AgentAction getAction(Agent agent, Maze maze) { 
		
		return getAvoidAction(agent, maze);
	}
	
	public AgentAction getAvoidAction(Agent agent, Maze maze) {
		
		PositionAgent pacManPos = getPacManPos();
		AgentAction action;

		
		if(pacManPos.getX() > agent.getPosition().getX()) { //pacMan is east of agent
			action = new AgentAction(3); //go west
			if(isLegalMove(agent, action, maze)) {return action;}
		}
		else if (pacManPos.getX() < agent.getPosition().getX()) { //pacMan is west of agent
			action = new AgentAction(2); //go east
			if(isLegalMove(agent, action, maze)) {return action;}
		}
		else { //pacMan is at same X coord as agent
			action = new AgentAction(3); //go west
			if(isLegalMove(agent, action, maze)) {return action;}
			action = new AgentAction(2); //go east
			if(isLegalMove(agent, action, maze)) {return action;}
		}
		
		if(pacManPos.getY() > agent.getPosition().getY()) { //pacMan is south of agent
			action = new AgentAction(0); //go north
			if(isLegalMove(agent, action, maze)) {return action;}
		}
		else if (pacManPos.getY() < agent.getPosition().getY()) { //pacMan is north of agent
			action = new AgentAction(1); //go south
			if(isLegalMove(agent, action, maze)) {return action;}
		}
		else {
			action = new AgentAction(0); //go north
			if(isLegalMove(agent, action, maze)) {return action;}
			action = new AgentAction(1); //go south
			if(isLegalMove(agent, action, maze)) {return action;}
		}
			
		
		return new AgentAction(4);
	}
	
	public PositionAgent getPacManPos() {
		ArrayList<Agent> agents= this._game.getAgents();
		for (Agent agent : agents) {
			if(agent instanceof Pacman) {
				return agent.getPosition();
			}
		}
		return new PositionAgent(0, 0, 0);
	}
	
	

	@Override
	protected void keyPressed(int code) {}
	
	

}
