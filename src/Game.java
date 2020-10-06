import java.util.ArrayList;

public abstract class Game implements Runnable, Observable {

	private int _turn;
	private int _maxturn;
	private boolean _isRunning;
	private Thread _thread;
	private long _time;
	private ArrayList<Observer> _observateurs;

	public Game(int maxturn, long time) {
		_maxturn = maxturn;
		_time = time;
		_observateurs = new ArrayList<Observer>();
		init();
	}
	
	public int getTurn() {return _turn;}
	public int getMaxTurn() {return _maxturn;}
	public boolean isRunning() {return _isRunning;}
	public void setTime(long time) {_time = time;}
	
	public void init() {
		System.out.println("Méthode init()");
		_turn = 0;
		_isRunning = false;
		initializeGame();
	}
	
	abstract public void initializeGame();
	
	// Effectue un seul tour de jeu
	public void step() {
		System.out.println("Méthode step()");
		_turn++;
		if(gameContinue()) {
			takeTurn();
		}else {
			_isRunning = false;
			gameOver();
		}
	}
	
	abstract public void takeTurn();
	
	abstract public void gameOver();
	
	abstract public boolean gameContinue();
	
	public void run() {
		System.out.println("Méthode run()");
		do {
			step();
			try {
				Thread.sleep(_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (_isRunning);
	}
	
	public void pause() {
		System.out.println("Méthode pause()");
		_isRunning = false;
	}
	
	public void launch() {
		System.out.println("Méthode launch()");
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
}
