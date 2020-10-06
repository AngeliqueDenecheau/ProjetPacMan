
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
	}

	@Override
	public void step() {
		_simplegame.step();
	}

	@Override
	public void run() {
		_simplegame.run();
	}

	@Override
	public void pause() {
		_simplegame.pause();
	}

	@Override
	public void setTime(double time) {
		_simplegame.setTime((long) (1000/time));
		System.out.println("New Time : " + 1000/time);
	}

}
