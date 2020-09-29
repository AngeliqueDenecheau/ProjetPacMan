
public class SimpleGame extends Game{
	
	public SimpleGame(int maxTurn, long time) {
		super(maxTurn, time);
	}

	@Override
	public void initializeGame() {
		System.out.println("Initialisation...");
		
	}

	@Override
	public void takeTurn() {
		System.out.println("Nouveau tour de jeu.");
		
	}

	@Override
	public void gameOver() {
		System.out.println("Game Over !");
		
	}

	@Override
	public boolean gameContinue() {
		System.out.println("La partie continue...");
		return true;
	}

}
