import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game implements Runnable, Observable {

	//Attributs
	private int _turn;
	private int _maxturn;
	private boolean _isRunning;
	private boolean _over;
	private Thread _thread;
	private long _time;
	private ArrayList<Observer> _observateurs;
	private Strategie _strategyPacman;
	private Strategie _strategyGhost;
	private int _timerCapsule;
	private Maze _maze;
	private String _mazeFilename;
	private ArrayList<Agent> _agents;

	//Constructeur
	public Game(int maxturn, long time, String mazeFilename, Strategie strategiePacman, Strategie strategieGhost) {
		_maxturn = maxturn;
		_time = time;
		_mazeFilename = mazeFilename;
		_strategyPacman = strategiePacman;
		_strategyGhost = strategieGhost;
		_strategyPacman.setGame(this);
		_strategyGhost.setGame(this);
		_observateurs = new ArrayList<Observer>();
		init();
	}
	
	//Setters et getters
	public int getTurn() {return _turn;}
	public int getMaxTurn() {return _maxturn;}
	public void setRunning(boolean running) {_isRunning = running;}
	public boolean isRunning() {return _isRunning;}
	public void setOver(boolean over) {_over = over;}
	public void setTime(long time) {_time = time;}
	public Strategie getStrategyPacman() {return _strategyPacman;}
	public Strategie getStrategyGhost() {return _strategyGhost;}
	public int getTimerCapsule() {return _timerCapsule;}
	public void setTimerCapsule(int timer) {_timerCapsule = timer;}
	public Maze getMaze() {return _maze;}
	public ArrayList<Agent> getAgents(){return _agents;}
	
	//Méthodes
	public void init() {
		_turn = 0;
		_isRunning = false;
		_over = false;
		_timerCapsule = 0;
		_thread = null;
		try {
			_maze = new Maze("./layouts/" + _mazeFilename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		_agents = new ArrayList<Agent>();
		for(PositionAgent posFantome : _maze.getGhosts_start()) {
			Ghost newFantome = new Ghost(new PositionAgent(posFantome.getX(), posFantome.getY(), posFantome.getDir()));
			_agents.add(newFantome);
		}
		for(PositionAgent posPacman : _maze.getPacman_start()) {
			Pacman newPacman = new Pacman(new PositionAgent(posPacman.getX(), posPacman.getY(), posPacman.getDir()));
			_agents.add(newPacman);
		}
		if(_strategyPacman instanceof StrategieMultijoueurs) ((StrategieMultijoueurs) _strategyPacman).setPacmanTurn(true);
	}
	
	public void run() {
		if(_strategyGhost instanceof StrategieInteractive) {
			try {
				if(!Thread.interrupted()) Thread.sleep(_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		do {
			step();
			try {
				if(!Thread.interrupted()) Thread.sleep(_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (_isRunning);
	}
	
	public void step() {
		if(_over) return;
		if(!(_strategyPacman instanceof StrategieMultijoueurs) || ((StrategieMultijoueurs) _strategyPacman).getPacmanTurn()) {
			_turn++;
			_timerCapsule--;
			if(_timerCapsule == 0) notifierObservers("colorghosts");
		}
		takeTurn();
		if(!gameContinue()) {
			_isRunning = false;
			if(_thread != null) _thread.interrupt();
			gameOver();
		}
		if(!_over && _strategyGhost instanceof StrategieInteractive && !stillGhosts() && _thread == null) launch();
	}
	
	public void takeTurn() {
		for(Agent agent : _agents) {
			AgentAction action = (agent instanceof Pacman) ? _strategyPacman.getAction(agent, _maze) : _strategyGhost.getAction(agent, _maze);
			moveAgent(agent, action);
		}
		killAgents();
		if(_strategyPacman instanceof StrategieMultijoueurs && !stillGhosts()) ((StrategieMultijoueurs) _strategyPacman).setPacmanTurn(true);
		notifierObservers("taketurn");
	}
	
	public void moveAgent(Agent agent, AgentAction action) {
		agent.setPosition(new PositionAgent(agent.getPosition().getX() + action.get_vx(), agent.getPosition().getY() + action.get_vy(), action.get_direction()));
		if(agent instanceof Pacman) {
			if(_maze.isFood(agent.getPosition().getX(), agent.getPosition().getY())) _maze.setFood(agent.getPosition().getX(), agent.getPosition().getY(), false);
			if(_maze.isCapsule(agent.getPosition().getX(), agent.getPosition().getY())) {
				_maze.setCapsule(agent.getPosition().getX(), agent.getPosition().getY(), false);
				setTimerCapsule(21);
				notifierObservers("colorghosts");
			}
		}
	}
	
	public void killAgents() {
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
	
	public boolean stillGhosts() {
		for(Agent agent : _agents) {
			if(agent instanceof Ghost) {
				return true;
			}
		}
		return false;
	}
	
	public boolean gameContinue() {
		if(_turn >= _maxturn) return false;
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
		boolean pacmanRestant = false;
		for(Agent agent : _agents) {
			if(agent instanceof Pacman) {
				pacmanRestant = true;
				break;
			}
		}
		return foodRestante && pacmanRestant;
	}
	
	public void gameOver() {
		_over = true;
		if(_turn >= _maxturn) {
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
		notifierObservers((foodRestante) ? "ghostwin" : "pacmanwin");
	}
	
	public void pause() {
		_isRunning = false;
	}
	
	public void launch() {
		_isRunning = true;
		_thread = new Thread(this);
		_thread.start();
	}

	public void ajouterObserver(Observer observateur) {
		_observateurs.add(observateur);	
	}

	public void supprimerObserver(Observer observateur) {
		_observateurs.remove(observateur);
	}

	public void notifierObservers(String modification) {
		for (Observer observer : _observateurs) {
			observer.actualiser(this, modification);
		}
	}
	
	public void keyPressed(int code) {
		_isRunning = true;
		int actionPacman = 4;
		int actionGhost = 4;
		switch(code) {
			case KeyEvent.VK_UP:
				actionPacman = 0;
				break;
			case KeyEvent.VK_DOWN:
				actionPacman = 1;
				break;
			case KeyEvent.VK_RIGHT:
				actionPacman = 2;
				break;
			case KeyEvent.VK_LEFT:
				actionPacman = 3;
				break;
			case KeyEvent.VK_Z:
				actionGhost = 0;
				break;
			case KeyEvent.VK_S:
				actionGhost = 1;
				break;
			case KeyEvent.VK_D:
				actionGhost = 2;
				break;
			case KeyEvent.VK_Q:
				actionGhost = 3;
				break;
			default:
				break;
		}
		
		// La touche pressée n'est pas une touche de déplacement
		if(actionPacman == 4 && actionGhost == 4) return;
		
		// Stratégie interactive
		if(_strategyPacman instanceof StrategieInteractive) {
			// Le joueur a appuyé sur une mauvaise touche pour déplacer le pacman
			if(actionPacman == 4) return;
			((StrategieInteractive)_strategyPacman).setAction(actionPacman);
		}
		if(_strategyGhost instanceof StrategieInteractive) {
			// Le joueur a appuyé sur une mauvaise touche pour déplacer le fantôme
			if(actionGhost == 4) return;
			((StrategieInteractive)_strategyGhost).setAction(actionGhost);
		}
		
		// Stratégie multijoueurs
		if(_strategyPacman instanceof StrategieMultijoueurs && actionPacman != 4) {
			// Le joueur a appuyé sur une touche pour déplacer le pacman mais ce n'est pas son tour de jouer
			if(!((StrategieMultijoueurs)_strategyPacman).getPacmanTurn()) return;
			((StrategieMultijoueurs)_strategyPacman).setActionPacman(actionPacman);
		}
		if(_strategyGhost instanceof StrategieMultijoueurs && actionGhost != 4) {
			// Le joueur a appuyé sur une touche pour déplacer le fantôme mais ce n'est pas son tour de jouer
			if(((StrategieMultijoueurs)_strategyGhost).getPacmanTurn()) return;
			((StrategieMultijoueurs)_strategyGhost).setActionGhost(actionGhost);
		}
		
		step();
	}
}
