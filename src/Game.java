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
	}
	
	public int getTurn() {return _turn;}
	public int getMaxTurn() {return _maxturn;}
	public boolean isRunning() {return _isRunning;}
	public void setTime(long time) {_time = time;}
	
	public void init() {
		_turn = 0;
		_isRunning = true;
		initializeGame();
		notifierObservers("Méthode init");
	}
	
	abstract public void initializeGame();
	
	// Effectue un seul tour de jeu
	public void step() {
		_turn++;
		if(gameContinue()) {
			takeTurn();
		}else {
			_isRunning = false;
			gameOver();
		}
		System.out.println("Tour " + _turn);
		notifierObservers("Méthode step " + _turn);
	}
	
	abstract public void takeTurn();
	
	abstract public void gameOver();
	
	abstract public boolean gameContinue();
	
	public void run() {
		System.out.println("Commande run effectuée.");
		do {
			step();
			try {
				Thread.sleep(_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (_isRunning);
		notifierObservers("Méthode run");
	}
	
	public void pause() {
		_isRunning = false;
		notifierObservers("Méthode pause");
	}
	
	public void launch() {
		_isRunning = true;
		_thread = new Thread(this);
		_thread.start();
		notifierObservers("Méthode launch");
	}

	public void ajouterObserver(Observer observateur) {
		_observateurs.add(observateur);	
	}

	public void supprimerObserver(Observer observateur) {
		_observateurs.remove(observateur);
	}

	public void notifierObservers(String msg) {
		for (Observer observer : _observateurs) {
			observer.actualiser(msg);
		}
	}
}
