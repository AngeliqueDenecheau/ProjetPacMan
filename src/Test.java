import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {

	public static void main(String[] args) throws Exception {
		//StrategieAleatoire strategie = new StrategieAleatoire();
		//StrategieSimple strategie = new StrategieSimple();
		StrategieInteractive strategiePacMan = new StrategieInteractive();
		//StrategieAvoid strategieFantomes = new StrategieAvoid();
		StrategieAttack strategieFantomes = new StrategieAttack();
		PacmanGame pacmanGame = new PacmanGame(1000, 500, "mediumClassic.lay", strategiePacMan, strategieFantomes);
		
		ControleurPacmanGame controleurPacmanGame = new ControleurPacmanGame(pacmanGame);
	}
}
