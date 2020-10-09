//Classe abstraite représentant une partie

import java.util.ArrayList;

public abstract class Game implements Runnable, Observable {

	private int _turn;
	private int _maxturn;
	private boolean _isRunning;
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
	public boolean isRunning() {return _isRunning;}
	public void setTime(long time) {_time = time;}
	public Strategie getStrategy() {return _strategy;}
	public int getTimerCapsule() {return _timerCapsule;}
	public void setTimerCapsule() {_timerCapsule = 20;}
	
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
		_timerCapsule = 0;
		initializeGame();
	}
	
	//Effectue un seul tour de jeu
	public void step() {
		System.out.println("Tour " + _turn);
		_turn++;
		if(gameContinue()) {
			takeTurn();
		}else{
			_isRunning = false;
			_thread.interrupt();
			gameOver();
		}
	}
	
	//Effectue le déroulement de la partie
	public void run() {
		do {
			try {
				Thread.sleep(_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			step();
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
}
