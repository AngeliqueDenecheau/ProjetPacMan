//Classe abstraite représentant une partie

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class Game implements Runnable, Observable {

	private int _turn;
	private int _maxturn;
	private boolean _isRunning;
	public boolean _over;
	private Thread _thread;
	private long _time;
	private ArrayList<Observer> _observateurs;
	private Strategie _strategy;
	private int _timerCapsule;

	public Game(int maxturn, long time, Strategie strategie) {
		_maxturn = maxturn;
		_time = time;
		_observateurs = new ArrayList<Observer>();
		_strategy = strategie;
	}
	
	//Setters et getters
	public int getTurn() {return _turn;}
	public int getMaxTurn() {return _maxturn;}
	public void setRunning(boolean running) {_isRunning = running;}
	public boolean isRunning() {return _isRunning;}
	public void setOver(boolean over) {_over = over;}
	public void setTime(long time) {_time = time;}
	public Strategie getStrategy() {return _strategy;}
	public int getTimerCapsule() {return _timerCapsule;}
	public void setTimerCapsule(int timer) {_timerCapsule = timer;}
	
	//Méthode abstraites
	public abstract ArrayList<Agent> getAgents();
	public abstract void initializeGame();
	public abstract void takeTurn();
	public abstract void gameOver();
	public abstract boolean gameContinue();
	
	//Initialise la partie
	public void init() {
		_turn = 0;
		_isRunning = false;
		_over = false;
		_timerCapsule = 0;
		_strategy.setTurn(Turn.PACMAN);
		initializeGame();
	}
	
	//Effectue un seul tour de jeu
	public void step() {
		if(_over) return;
		if(!(_strategy instanceof StrategieMultijoueurs) || _strategy.getTurn() == Turn.PACMAN) {
			System.out.println("Tour " + _turn);
			_turn++;
			setTimerCapsule(getTimerCapsule() - 1);
			if(getTimerCapsule() == 0) notifierObservers("colorghosts");
		}
		takeTurn();
		if(!gameContinue()) {
			_isRunning = false;
			if(_thread != null) _thread.interrupt();
			gameOver();
		}
	}
	
	//Effectue le déroulement de la partie
	public void run() {
		do {
			step();
			try {
				if(!Thread.interrupted()) Thread.sleep(_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (_isRunning);
	}
	
	//Met la partie en pause
	public void pause() {
		_isRunning = false;
	}
	
	//Lance la partie
	public void launch() {
		_isRunning = true;
		_thread = new Thread(this);
		_thread.start();
	}

	//Ajoute un observateur dans la liste des observateurs
	public void ajouterObserver(Observer observateur) {
		_observateurs.add(observateur);	
	}

	//Supprime un observateur de la liste des observateurs
	public void supprimerObserver(Observer observateur) {
		_observateurs.remove(observateur);
	}

	//Notifie tous les observateurs qu'il y a eu des modifications
	public void notifierObservers(String modification) {
		for (Observer observer : _observateurs) {
			observer.actualiser(this, modification);
		}
	}
	
	public void keyPressed(int code) {
		_isRunning = true;
		if((_strategy instanceof StrategieInteractive && (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT)) || (_strategy instanceof StrategieMultijoueurs && ((_strategy.getTurn() == Turn.PACMAN && (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT)) || (_strategy.getTurn() == Turn.GHOST && (code == KeyEvent.VK_Z || code == KeyEvent.VK_S || code == KeyEvent.VK_D || code == KeyEvent.VK_Q))))){
			_strategy.keyPressed(code);
			step();			
		}
	}
}
