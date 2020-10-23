import java.util.ArrayList;

public class StrategieSimple extends Strategie {

	@Override
	public AgentAction getAction(Agent agent, Maze maze) {
		if(agent instanceof Pacman) {
			//Le Pacman regarde ce qui se trouve sur les 4 cases adjacentes : capsule, food et/ou ghost
			ArrayList<ArrayList<String>> casesAdjacentes = new ArrayList<>();
			for(int i = 0; i < 4; i++) {
				AgentAction action = new AgentAction(i);
				ArrayList<String> caseAdjacente = new ArrayList<>();
				int x = agent.getPosition().getX() + action.get_vx();
				int y = agent.getPosition().getY() + action.get_vy();
				if(!maze.isWall(x, y)) {
					if(maze.isCapsule(x, y)) caseAdjacente.add("capsule");
					if(maze.isFood(x, y)) caseAdjacente.add("food");
					if(isGhost(x, y)) caseAdjacente.add("ghost");				
				}else {
					caseAdjacente.add("wall");
				}
				casesAdjacentes.add(caseAdjacente);
			}
			// Il se déplace dans cet ordre de priorité :
			// - sur une case contenant un fantôme s'il a mangé une capsule auparavent
			if(_game.getTimerCapsule() > 0) {
				for(int i = 0; i < 4; i++) {
					if(casesAdjacentes.get(i).contains("ghost")) return new AgentAction(i);
				}
			}
			// - sur une case contenant une capsule s'il n'y a pas de ghost sur cette case
			for(int i = 0; i < 4; i++) {
				if(casesAdjacentes.get(i).contains("capsule") && !casesAdjacentes.get(i).contains("ghost")) return new AgentAction(i);
			}
			// - sur une case contenant une unité de nourriture s'il n'y a pas de ghost sur cette case
			for(int i = 0; i < 4; i++) {
				if(casesAdjacentes.get(i).contains("food") && !casesAdjacentes.get(i).contains("ghost")) return new AgentAction(i);
			}
			// - sur la case opposée à un ghost s'il n'y a pas de ghost sur cette case
			for(int i = 0; i < 4; i++) {
				if(casesAdjacentes.get(i).contains("ghost")) {
					if(i == 0 && !casesAdjacentes.get(1).contains("ghost") && !casesAdjacentes.get(1).contains("wall")) return new AgentAction(1);
					if(i == 1 && !casesAdjacentes.get(0).contains("ghost") && !casesAdjacentes.get(0).contains("wall")) return new AgentAction(0);
					if(i == 2 && !casesAdjacentes.get(3).contains("ghost") && !casesAdjacentes.get(3).contains("wall")) return new AgentAction(3);
					if(i == 3 && !casesAdjacentes.get(2).contains("ghost") && !casesAdjacentes.get(2).contains("wall")) return new AgentAction(2);
				}
			}
			// - si ghost sur toutes les cases adjacentes : ne bouge pas
			if((casesAdjacentes.get(0).contains("ghost") || casesAdjacentes.get(0).contains("wall")) && (casesAdjacentes.get(1).contains("ghost") || casesAdjacentes.get(1).contains("wall")) && (casesAdjacentes.get(2).contains("ghost") || casesAdjacentes.get(2).contains("wall")) && (casesAdjacentes.get(3).contains("ghost") || casesAdjacentes.get(3).contains("wall"))) return new AgentAction(4);
			// - dans la même direction que son déplacement précédent si pas un mur
			if(agent.getPosition().getDir() != 4 && !casesAdjacentes.get(agent.getPosition().getDir()).contains("wall")) return new AgentAction(agent.getPosition().getDir());
			// - déplacement aléatoire
			return StrategieAleatoire.getActionAleatoire(agent, maze);
		}else {
			// Le Fantôme se rapproche du Pacman si aucune caspule n'a été mangée, s'éloigne sinon
			return (_game.getTimerCapsule() > 0) ? avoid(agent, maze) : attack(agent, maze);
		}
	}
	
	public AgentAction attack(Agent agent, Maze maze) {
		PositionAgent pacManPos = getPacManPos();
		AgentAction action;

		
		if(pacManPos.getX() > agent.getPosition().getX()) { //pacMan is east of agent
			action = new AgentAction(2); //go east
			if(isLegalMove(agent, action, maze)) {return action;}
		}
		else if (pacManPos.getX() < agent.getPosition().getX()) { //pacMan is west of agent
			action = new AgentAction(3); //go west
			if(isLegalMove(agent, action, maze)) {return action;}
		}
		else { //pacMan is at same X coord as agent
			if(pacManPos.getY() > agent.getPosition().getY()) { //pacMan is south of agent
				action = new AgentAction(1); //go south
				if(isLegalMove(agent, action, maze)) {return action;}
			}
			else if (pacManPos.getY() < agent.getPosition().getY()) { //pacMan is north of agent
				action = new AgentAction(0); //go north
				if(isLegalMove(agent, action, maze)) {return action;}
			}
		}
		
		if(pacManPos.getY() > agent.getPosition().getY()) { //pacMan is south of agent
			action = new AgentAction(1); //go south
			if(isLegalMove(agent, action, maze)) {return action;}
		}
		else if (pacManPos.getY() < agent.getPosition().getY()) { //pacMan is north of agent
			action = new AgentAction(0); //go north
			if(isLegalMove(agent, action, maze)) {return action;}
		}
		else {
			if(pacManPos.getX() > agent.getPosition().getX()) { //pacMan is east of agent
				action = new AgentAction(2); //go east
				if(isLegalMove(agent, action, maze)) {return action;}
			}
			else if (pacManPos.getX() < agent.getPosition().getX()) { //pacMan is west of agent
				action = new AgentAction(3); //go west
				if(isLegalMove(agent, action, maze)) {return action;}
			}
		}
		return new AgentAction(4);
	}
	
	public AgentAction avoid(Agent agent, Maze maze) {
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
	
	public boolean isGhost(int x, int y) {
		for(Agent agent : this._game.getAgents()) {
			if(agent.getPosition().getX() == x && agent.getPosition().getY() == y && agent instanceof Ghost) return true;
		}
		return false;
	}
}
