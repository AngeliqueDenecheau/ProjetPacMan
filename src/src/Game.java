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
	private Strategie _strategiePacMan;
	private Strategie _strategieFantomes;
	private int _timerCapsule;

	public Game(int maxturn, long time, Strategie strategiePacMan, Strategie strategieFantomes) {
		_maxturn = maxturn;
		_time = time;
		_observateurs = new ArrayList<Observer>();
		_strategiePacMan = strategiePacMan;
		_strategieFantomes = strategieFantomes;
	}
	
	//Setters et getters
	public int getTurn() {return _turn;}
	public int getMaxTurn() {return _maxturn;}
	public void setRunning(boolean running) {_isRunning = running;}
	public boolean isRunning() {return _isRunning;}
	public void setOver(boolean over) {_over = over;}
	public void setTime(long time) {_time = time;}
	public Strategie getStrategyPacMan() {return _strategiePacMan;}
	public Strategie getStrategyFantomes() {return _strategieFantomes;}
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
		initializeGame();
	}
	
	//Effectue un seul tour de jeu
	public void step() {
		if(_over) return;
		System.out.println("Tour " + _turn);
		_turn++;
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
		_strategiePacMan.keyPressed(code);
		_strategieFantomes.keyPressed(code);
		step();
	}
}
