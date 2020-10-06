
public class ControleurSimpleGame implements InterfaceControleur {
	SimpleGame _simplegame;
	ViewCommand _viewCommand;
	ViewSimpleGame _viewSimpleGame;
	
	public ControleurSimpleGame(SimpleGame game) {
		_simplegame = game;
		_viewCommand = new ViewCommand(game, this);
		_viewSimpleGame = new ViewSimpleGame(game, this);
	}

	@Override
	public void start() {
		_simplegame.init();
		_simplegame.notifierObservers("start");
	}

	@Override
	public void step() {
		_simplegame.step();
		_simplegame.notifierObservers("step");
	}

	@Override
	public void run() {
		_simplegame.launch();
		_simplegame.notifierObservers("run");
	}

	@Override
	public void pause() {
		_simplegame.pause();
		_simplegame.notifierObservers("pause");
	}

	@Override
	public void setTime(double time) {
		_simplegame.setTime((long) (1000/time));
	}

}
