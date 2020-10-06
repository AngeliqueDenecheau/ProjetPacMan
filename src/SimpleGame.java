
public class SimpleGame extends Game {
	
	public SimpleGame(int maxTurn, long time) {
		super(maxTurn, time);
	}

	@Override
	public void initializeGame() {
		notifierObservers("Méthode initializeGame");		
	}

	@Override
	public void takeTurn() {
		notifierObservers("Méthode takeTurn");		
	}

	@Override
	public void gameOver() {
		notifierObservers("Méthode gameOver");		
	}

	@Override
	public boolean gameContinue() {
		notifierObservers("Méthode gameContinue");
		return true;
	}
}
