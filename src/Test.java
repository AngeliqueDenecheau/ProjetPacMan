
public class Test {

	public static void main(String[] args) throws Exception {
		Maze maze = new Maze("./layouts/testClassic.lay");
		PacmanGame pacmanGame = new PacmanGame(10, 2000, maze);
		ControleurPacmanGame controleurPacmanGame = new ControleurPacmanGame(pacmanGame);
	}
}
