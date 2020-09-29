
public abstract class Game implements Runnable {

	private int _turn;
	private int _maxturn;
	private boolean _isRunning;
	private Thread _thread;
	private long _time;

	public Game(int maxturn, long time) {
		_maxturn = maxturn;
		_time = time;
	}
	
	public void init() {
		_turn = 0;
		_isRunning = true;
		initializeGame();
	}
	
	abstract public void initializeGame();
	
	// Effectue un seul tour de jeu
	public void step() {
		System.out.println("Commande step effectuée.");
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
		System.out.println("Commande run effectuée.");
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
		_isRunning = false;
	}
	
	public void launch() {
		_isRunning = true;
		_thread = new Thread(this);
		_thread.start();
	}
}
