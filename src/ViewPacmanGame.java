import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewPacmanGame implements Observer {
	private JFrame _jframe;
	private PanelPacmanGame _labyrinthe;
	private InterfaceControleur _controleurGame;
	
	public ViewPacmanGame(Observable observable, InterfaceControleur controleur, Maze maze) {
		observable.ajouterObserver(this);
		_controleurGame = controleur;
		
		_jframe = new JFrame();
		_jframe.setTitle("Jeu");
		_jframe.setSize(new Dimension(maze.getSizeX()*75, maze.getSizeY()*75));
		_jframe.setLocation(0, 0);
		if(_controleurGame.isInteractive() || _controleurGame.isMultijoueurs()) {
			_jframe.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {}
				@Override
				public void keyReleased(KeyEvent e) {}
				@Override
				public void keyPressed(KeyEvent e) {
					_controleurGame.keyPressed(e.getKeyCode());
				}
			});
		}
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		_labyrinthe = new PanelPacmanGame(maze);
		container.add(_labyrinthe, BorderLayout.CENTER);
		_jframe.add(container);
		_jframe.setVisible(true);
		_jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actualiser(Game game, String modification) {
		switch(modification) {
			case "start":
				_jframe.dispose();
				break;
			case "colorghosts":
				_labyrinthe.setGhostsScarred(_controleurGame.isGhostsScarred());
				break;
			case "taketurn":
				_labyrinthe.updatePositions(game.getAgents());
				_labyrinthe.repaint();
				break;
			default:
				break;
		}
	}
}
