import java.util.ArrayList;

public class PacmanGame extends Game {
	private Maze _maze;
	private ArrayList<Agent> _agents;

	public PacmanGame(int maxTurn, long time, Maze maze) {
		super(maxTurn, time);
		
		_maze = maze;
		_agents = new ArrayList<Agent>();
		
		super.init();
		
	}
	
	public Maze getMaze() {
		return _maze;
	}
	
	public ArrayList<Agent> getAgents(){
		return _agents;
	}

	@Override
	public void initializeGame() {
		System.out.println("Méthode initializeGame()");
		
		for(PositionAgent posFantome : _maze.getGhosts_start()) {
			AgentFantome newFantome = new AgentFantome(posFantome.getX(), posFantome.getY(), posFantome.getDir());
			_agents.add(newFantome);
		}
		for(PositionAgent posPacman : _maze.getPacman_start()) {
			AgentPacman newPacman = new AgentPacman(posPacman.getX(), posPacman.getY(), posPacman.getDir());
			_agents.add(newPacman);
		}
	}

	@Override
	public void takeTurn() {
		notifierObservers("taketurn");
		
	}

	@Override
	public void gameOver() {
	}

	@Override
	public boolean gameContinue() {
		
		//Check there is still food or capsules in the maze;
		int width = _maze.getSizeX();
		int height = _maze.getSizeY();
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(_maze.isFood(i, j) || _maze.isCapsule(i, j)) { //if any food is available, game continues
					return true;
				}
			}
		}
		
		
		return false;
	}
	
	public boolean isLegalMove(Agent agent, AgentAction action) {
		return false;
	}
	
	public void moveAgent(Agent agent, AgentAction action) {
		agent.setX(agent.getX() + action.get_vx());
		agent.setY(agent.getY() + action.get_vy());
	}
	
	
}
