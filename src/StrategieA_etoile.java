import java.util.ArrayList;

//bigSearch_onePacman_oneGhost.lay
//originalClassic.lay

public class StrategieA_etoile extends Strategie{
	
	public Graph graphe;
	public Game _game;
	
	public class Pair{
		int x;
		int y;
	}
	
	@Override
	public AgentAction getAction(Agent agent, Maze maze) {
		if(agent instanceof Ghost) {
			if(_game.getTimerCapsule() > 0) {//avoid pacman if ghosts can be eaten
				return avoid(agent, maze);
			}
			else {
				return pathToPacman(agent, maze);
			}
		}
		else {
			System.out.println("vers food");
			return pathToFood(agent, maze);
		}
	}
	
	@Override
	public void setGame(Game game) {
		this._game = game;
		graphe = new Graph(this._game, false);
	}
	
	//get position of closest food pellet
	public Pair getFoodPos(Agent agent, Maze maze) {
		
		PositionAgent agentPos = agent.getPosition();
		
		Pair bestPos = new Pair();//closest food so far
		bestPos.x = -1;
		bestPos.y = -1;
		//double distance = Math.sqrt(Math.pow((bestPos.x-agentPos.getX()), 2) + Math.pow((bestPos.y-agentPos.getY()), 2));
		double distance = -1;
		//distance between bestPos and agentPos
		
		
		//get position of closest food
		for(int x = 0; x < maze.getSizeX(); x++) {
			for(int y = 0; y < maze.getSizeY(); y++) {
				if(maze.isFood(x, y)) {
					if(bestPos.x == -1 || Math.sqrt(Math.pow((x-agentPos.getX()), 2) + Math.pow((y-agentPos.getY()), 2)) < distance) {
						bestPos.x = x;
						bestPos.y = y;
						distance = Math.sqrt(Math.pow((x-agentPos.getX()), 2) + Math.pow((y-agentPos.getY()), 2));
						//update bestPos
					}
				}
			}
		}
		
		//System.out.println("closest food: " + bestPos.x + ", " + bestPos.y + ", distance = " + distance);
		
		System.out.println(bestPos.x + " " + bestPos.y);
		return bestPos;
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
	
	public AgentAction pathToFood(Agent agent, Maze maze) {
		Pair foodPos = getFoodPos(agent, maze);
		int goal = foodPos.y*maze.getSizeX() + foodPos.x;
		int start = agent.getPosition().getY()*maze.getSizeX() + agent.getPosition().getX();

		
		Graph newGraphe = new Graph(this._game, true);//recreate graph with ghost positions;
		
		int cell = newGraphe.A_Star(start, goal, maze);
		
		System.out.println(getAction(agent, maze, cell).get_vx() + " " + getAction(agent, maze, cell).get_vy() + " " + getAction(agent, maze, cell).get_direction());
		return getAction(agent, maze, cell);
		
	}
	
	//return next position to get closer to pacman
	public AgentAction pathToPacman(Agent agent, Maze maze) {
		PositionAgent pacManPos = getPacManPos();
		int goal = pacManPos.getY()*_game.getMaze().getSizeX() + pacManPos.getX(); //node number of pacman pos
		int start = agent.getPosition().getY()*_game.getMaze().getSizeX() + agent.getPosition().getX();
		
		int cell = graphe.A_Star(start, goal, maze); //returns the cell number to move to
		//System.out.println("next cell: " + cell); 
		

		return getAction(agent, maze, cell);
	}
	
	
	public AgentAction getAction(Agent agent, Maze maze, int cell) {
		
		if(cell == -1) {
			//System.out.println("no path, error");
			return new AgentAction(4);
		}
		
		
		//translate to coords
		int cellX = cell % maze.getSizeX();
		int cellY = cell / maze.getSizeX();
		
		//System.out.println("current position: " + agent.getPosition().getX() + ",  " +  agent.getPosition().getY());
		//System.out.println("next position: " + cellX + ",  " +  cellY);
		
		
		if(cellX < agent.getPosition().getX()) {//go west
			//System.out.println("west");
			return new AgentAction(3);
		}
		
		else if(cellX > agent.getPosition().getX()) {//go east
			//System.out.println("east");
			return new AgentAction(2);
		}
		
		else if(cellY > agent.getPosition().getY()) {//go south
			//System.out.println("south");
			return new AgentAction(1);
		}
		
		else if(cellY < agent.getPosition().getY()) {//go north
			//System.out.println("north");
			return new AgentAction(0);
		}
		
		else {//something has gone wrong
			//System.out.println("error");
			return new AgentAction(4);
		}
	}
	
	
	//avoid pacman
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
	

}
