//Classe représenatant une partie de Pacman

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.print.DocFlavor.INPUT_STREAM;

public class PacmanGame extends Game {
	private Maze _maze;
	private String _mazeFilename;
	private ArrayList<Agent> _agents;

<<<<<<< HEAD
	public PacmanGame(int maxTurn, long time, Maze maze) {
		super(maxTurn, time);
		
		_maze = maze;
		_agents = new ArrayList<Agent>();
		
		super.init();
		
=======
	public PacmanGame(int maxTurn, long time, String mazeFilename, Strategie strategie) {
		super(maxTurn, time, strategie);
		_mazeFilename = mazeFilename;
		_agents = new ArrayList<Agent>();
		init();
>>>>>>> b493e2c2bfe649c6e1e8676de5960286e2d2c630
	}
	
	public Maze getMaze() {
		return _maze;
	}
	
	public ArrayList<Agent> getAgents(){
		return _agents;
	}

	@Override
	//Initialise la partie
	public void initializeGame() {
<<<<<<< HEAD
		System.out.println("Méthode initializeGame()");
		
=======
		try {
			_maze = new Maze("./layouts/" + _mazeFilename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		_agents = new ArrayList<Agent>();
>>>>>>> b493e2c2bfe649c6e1e8676de5960286e2d2c630
		for(PositionAgent posFantome : _maze.getGhosts_start()) {
			Ghost newFantome = new Ghost(new PositionAgent(posFantome.getX(), posFantome.getY(), posFantome.getDir()));
			_agents.add(newFantome);
		}
		for(PositionAgent posPacman : _maze.getPacman_start()) {
			Pacman newPacman = new Pacman(new PositionAgent(posPacman.getX(), posPacman.getY(), posPacman.getDir()));
			_agents.add(newPacman);
		}
	}

	@Override
	//Effectue un tour de jeu
	public void takeTurn() {
		for(Agent agent : _agents) {
			AgentAction action = getStrategy().getAction(agent, _maze);
			moveAgent(agent, action);
		}
		killAgents();
		setTimerCapsule(getTimerCapsule() - 1);
		notifierObservers("taketurn");
		
	}

	@Override
	//Affiche le message de fin de partie
	public void gameOver() {
		setOver(true);
		if(getTurn() >= getMaxTurn()) {
			notifierObservers("toursecoules");
			return;
		}
		boolean foodRestante = false;
		for(int i = 0; i < _maze.getSizeX(); i++) {
			for(int j = 0; j < _maze.getSizeY(); j++) {
				if(_maze.isFood(i, j)) {
					foodRestante = true;
					break;
				}
			}
			if(foodRestante) break;
		}
		if(!foodRestante) {
			notifierObservers("pacmanwin");
			return;
		}
		notifierObservers("ghostwin");
	}

	@Override
	//Renvoie true si la partie doit continuer, false sinon
	public boolean gameContinue() {
<<<<<<< HEAD
		
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
=======
		if(getTurn() >= getMaxTurn()) return false;
		boolean foodRestante = false;
		for(int i = 0; i < _maze.getSizeX(); i++) {
			for(int j = 0; j < _maze.getSizeY(); j++) {
				//if any food is available, game continues
				if(_maze.isFood(i, j)) {
					foodRestante = true;
					break;
				}
			}
			if(foodRestante) break;
		}
		boolean pacmanRestant = false;
		for(Agent agent : _agents) {
			if(agent instanceof Pacman) {
				pacmanRestant = true;
				break;
			}
		}
		return foodRestante && pacmanRestant;
>>>>>>> b493e2c2bfe649c6e1e8676de5960286e2d2c630
	}
	
	//Effectue le déplacement d'un agent en fonctin d'une action donnée
	public void moveAgent(Agent agent, AgentAction action) {
		agent.setPosition(new PositionAgent(agent.getPosition().getX() + action.get_vx(), agent.getPosition().getY() + action.get_vy(), action.get_direction()));
		if(agent instanceof Pacman) {
			if(_maze.isFood(agent.getPosition().getX(), agent.getPosition().getY())) _maze.setFood(agent.getPosition().getX(), agent.getPosition().getY(), false);
			if(_maze.isCapsule(agent.getPosition().getX(), agent.getPosition().getY())) {
				_maze.setCapsule(agent.getPosition().getX(), agent.getPosition().getY(), false);
				setTimerCapsule(20);
			}
		}
	}
	
	//Teste si un pacman (ou un fantôme si une capsule a été récupérée) a été mangé et le supprime de la liste des agents dans ce cas
	public void killAgents() {
		System.out.println(getTimerCapsule());
		for(int i = 0; i < _agents.size() - 1; i++) {
			for(int j = i+1; j < _agents.size(); j++) {
				if(_agents.get(i).getPosition().getX() == _agents.get(j).getPosition().getX() && _agents.get(i).getPosition().getY() == _agents.get(j).getPosition().getY()) {
					if(_agents.get(i) instanceof Pacman && _agents.get(j) instanceof Ghost) {
						_agents.remove((getTimerCapsule() > 0) ? j : i);
						i--;
						break;
					}
					if(_agents.get(i) instanceof Ghost && _agents.get(j) instanceof Pacman) {
						_agents.remove((getTimerCapsule() > 0) ? i : j);
						i--;
						break;
					}
				}
			}
		}
	}
	
	
}
