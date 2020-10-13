import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {

	public static void main(String[] args) throws Exception {
		//StrategieAleatoire strategie = new StrategieAleatoire();
		StrategieSimple strategie = new StrategieSimple();
		//StrategieInteractive strategie = new StrategieInteractive();
		PacmanGame pacmanGame = new PacmanGame(1000, 500, "capsuleClassic.lay", strategie);
		ControleurPacmanGame controleurPacmanGame = new ControleurPacmanGame(pacmanGame);
	}
}
