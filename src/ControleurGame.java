public class ControleurGame implements InterfaceControleur {
	Game _game;
	ViewCommand _viewCommand;
	ViewPacmanGame _viewPacmanGame;
	
	public ControleurGame(Game game) {
		_game = game;
		_viewPacmanGame = new ViewPacmanGame(game, this, _game.getMaze());
		_viewCommand = new ViewCommand(game, this);
	}
	
	public boolean isInteractive() {
		return _game.getStrategyPacman() instanceof StrategieInteractive || _game.getStrategyGhost() instanceof StrategieInteractive;
	}
	
	public boolean isMultijoueurs() {
		return _game.getStrategyPacman() instanceof StrategieMultijoueurs || _game.getStrategyGhost() instanceof StrategieMultijoueurs;
	}

	@Override
	//Redémarre la partie
	public void start() {
		_game.init();
		_game.notifierObservers("start");
		_viewPacmanGame = new ViewPacmanGame(_game, this, _game.getMaze());
	}

	@Override
	//Effectue un seul tour de jeu
	public void step() {
		_game.notifierObservers("step");
		_game.step();
	}

	@Override
	//Lance la partie
	public void run() {
		_game.launch();
		_game.notifierObservers("run");
	}

	@Override
	//Met le jeu en pause
	public void pause() {
		_game.pause();
		_game.notifierObservers("pause");
	}

	@Override
	//Modifie le temps entre chaque tour de jeu
	public void setTime(double time) {
		_game.setTime((long) (1000/time));
	}

	@Override
	public void keyPressed(int code) {
		_game.keyPressed(code);
		_game.notifierObservers("keypressed");
	}
	
	public boolean isGhostsScarred() {
		return _game.getTimerCapsule() > 0;
	}

}
