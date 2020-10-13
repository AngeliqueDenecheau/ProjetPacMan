import java.awt.event.KeyEvent;

//Classe récupérant les actions faîtes par l'utilisateur sur le panneau de commande
//et effectuant les actions correspondantes.

public class ControleurPacmanGame implements InterfaceControleur {
	PacmanGame _pacmangame;
	ViewCommand _viewCommand;
	ViewPacmanGame _viewPacmanGame;
	
	public ControleurPacmanGame(PacmanGame game) {
		_pacmangame = game;
		_viewCommand = new ViewCommand(game, this);
		_viewPacmanGame = new ViewPacmanGame(game, this, _pacmangame.getMaze());
	}
	
	public boolean isInteractive() {
		return _pacmangame.getStrategy() instanceof StrategieInteractive;
	}

	@Override
	//Redémarre la partie
	public void start() {
		_pacmangame.init();
		_pacmangame.notifierObservers("start");
		_viewPacmanGame = new ViewPacmanGame(_pacmangame, this, _pacmangame.getMaze());
	}

	@Override
	//Effectue un seul tour de jeu
	public void step() {
		_pacmangame.notifierObservers("step");
		_pacmangame.step();
	}

	@Override
	//Lance la partie
	public void run() {
		_pacmangame.launch();
		_pacmangame.notifierObservers("run");
	}

	@Override
	//Met le jeu en pause
	public void pause() {
		_pacmangame.pause();
		_pacmangame.notifierObservers("pause");
	}

	@Override
	//Modifie le temps entre chaque tour de jeu
	public void setTime(double time) {
		_pacmangame.setTime((long) (1000/time));
	}

	@Override
	public void keyPressed(int code) {
		_pacmangame.keyPressed(code);
		_pacmangame.notifierObservers("keypressed");
		
	}

}
