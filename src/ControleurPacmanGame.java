
public class ControleurPacmanGame implements InterfaceControleur {
	PacmanGame _pacmangame;
	ViewCommand _viewCommand;
	ViewPacmanGame _viewPacmanGame;
	
	public ControleurPacmanGame(PacmanGame game) {
		_pacmangame = game;
		_viewCommand = new ViewCommand(game, this);
		_viewPacmanGame = new ViewPacmanGame(game, this, _pacmangame.getMaze());
	}

	@Override
	public void start() {
		_pacmangame.init();
		_pacmangame.notifierObservers("start");
	}

	@Override
	public void step() {
		_pacmangame.step();
		_pacmangame.notifierObservers("step");
	}

	@Override
	public void run() {
		_pacmangame.launch();
		_pacmangame.notifierObservers("run");
	}

	@Override
	public void pause() {
		_pacmangame.pause();
		_pacmangame.notifierObservers("pause");
	}

	@Override
	public void setTime(double time) {
		_pacmangame.setTime((long) (1000/time));
	}

}
