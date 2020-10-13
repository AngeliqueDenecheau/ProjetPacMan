import java.awt.event.KeyEvent;

//Classe récupérant les actions faîtes par l'utilisateur sur le panneau de commande
//et effectuant les actions correspondantes.

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
	//Redémarre la partie
	public void start() {
		_simplegame.init();
		_simplegame.notifierObservers("start");
	}

	@Override
	//Effectue un seul tour de jeu
	public void step() {
		_simplegame.step();
		_simplegame.notifierObservers("step");
	}

	@Override
	//Lance la partie
	public void run() {
		_simplegame.launch();
		_simplegame.notifierObservers("run");
	}

	@Override
	//Met le jeu en pause
	public void pause() {
		_simplegame.pause();
		_simplegame.notifierObservers("pause");
	}

	@Override
	//Modifie le temps entre chaque tour de jeu
	public void setTime(double time) {
		_simplegame.setTime((long) (1000/time));
	}

	@Override
	public void keyPressed(int code) {}

	@Override
	public boolean isInteractive() {return false;}

	@Override
	public boolean isMultijoueurs() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGhostsScarred() {
		// TODO Auto-generated method stub
		return false;
	}

}
