import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PacmanGame extends Game {
	private Maze _maze;
	private ArrayList<Agent> _agents;

	public PacmanGame(int maxTurn, long time, Maze maze) {
		super(maxTurn, time);
		_maze = maze;
		_agents = new ArrayList<Agent>();
		init();
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
			AgentFantome newFantome = new AgentFantome(new PositionAgent(posFantome.getX(), posFantome.getY(), posFantome.getDir()));
			_agents.add(newFantome);
		}
		for(PositionAgent posPacman : _maze.getPacman_start()) {
			AgentPacman newPacman = new AgentPacman(new PositionAgent(posPacman.getX(), posPacman.getY(), posPacman.getDir()));
			_agents.add(newPacman);
		}
	}

	@Override
	public void takeTurn() {
		System.out.println("Méthode taketurn()");
		for(Agent agent : _agents) {
			List<Integer> directions = new ArrayList<Integer>();
			directions.add(0);
			directions.add(1);
			directions.add(2);
			directions.add(3);
			Collections.shuffle(directions);
			while(!directions.isEmpty()) {
				AgentAction action = new AgentAction(directions.get(0));
				if(isLegalMove(agent, action)) {
					moveAgent(agent, action);
					break;
				}
				directions.remove(0);
			}
		}
		notifierObservers("taketurn");
	}

	@Override
	public void gameOver() {
	}

	@Override
	public boolean gameContinue() {
		return true;
	}
	
	public boolean isLegalMove(Agent agent, AgentAction action) {
		return !_maze.isWall(agent.getPosition().getX() + action.get_vx(), agent.getPosition().getY() + action.get_vy());
	}
	
	public void moveAgent(Agent agent, AgentAction action) {
		agent.setPosition(new PositionAgent(agent.getPosition().getX() + action.get_vx(), agent.getPosition().getY() + action.get_vy(), action.get_direction()));
		if(_maze.isFood(agent.getPosition().getX(), agent.getPosition().getY()) && (agent instanceof AgentPacman)) _maze.setFood(agent.getPosition().getX(), agent.getPosition().getY(), false);
	}
}
