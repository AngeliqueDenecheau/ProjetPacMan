
public class Test {

	public static void main(String[] args) throws Exception {
		Maze maze = new Maze("./layouts/test.lay");
		//StrategieAleatoire strategie = new StrategieAleatoire();
		StrategieSimple strategie = new StrategieSimple();
		PacmanGame pacmanGame = new PacmanGame(15, 500, maze, strategie);
		ControleurPacmanGame controleurPacmanGame = new ControleurPacmanGame(pacmanGame);
	}
}
