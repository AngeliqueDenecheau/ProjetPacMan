import java.util.ArrayList;

public class SimpleGame extends Game {
	
	public SimpleGame(int maxTurn, long time, Strategie strategie) {
		super(maxTurn, time, strategie);
		init();
	}

	@Override
	public void initializeGame() {
		System.out.println("Méthode initializeGame()");
	}

	@Override
	public void takeTurn() {
		notifierObservers("taketurn");
	}

	@Override
	public void gameOver() {
	}

	@Override
	public boolean gameContinue() {
		return true;
	}

	@Override
	public ArrayList<Agent> getAgents() {
		return null;
	}
}
