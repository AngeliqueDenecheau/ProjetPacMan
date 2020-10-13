import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {

	public static void main(String[] args) throws Exception {
<<<<<<< HEAD
		Maze maze = new Maze("./layouts/testClassic.lay");
		
		PacmanGame pacmanGame = new PacmanGame(10, 2000, maze);
=======
		//StrategieAleatoire strategie = new StrategieAleatoire();
		StrategieSimple strategie = new StrategieSimple();
		//StrategieInteractive strategie = new StrategieInteractive();
		PacmanGame pacmanGame = new PacmanGame(1000, 500, "capsuleClassic.lay", strategie);
>>>>>>> b493e2c2bfe649c6e1e8676de5960286e2d2c630
		ControleurPacmanGame controleurPacmanGame = new ControleurPacmanGame(pacmanGame);
	}
}
