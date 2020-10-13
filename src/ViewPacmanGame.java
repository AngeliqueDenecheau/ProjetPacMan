//Classe affichant la grille de jeu

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ViewPacmanGame implements Observer {
	private JFrame _jframe;
	private JLabel _tourCourant;
	private PanelPacmanGame _labyrinthe;
	private InterfaceControleur _controleurGame;
	
	public ViewPacmanGame(Observable observable, InterfaceControleur controleur, Maze maze) {
		observable.ajouterObserver(this);
		_controleurGame = controleur;
		
		_jframe = new JFrame();
		_jframe.setTitle("Game");
		_jframe.setSize(new Dimension(maze.getSizeX()*75, 50 + maze.getSizeY()*75));
		Dimension windowSize = _jframe.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		_jframe.setLocation(1400, 0);
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
		_tourCourant = new JLabel("Tour : 0", SwingConstants.CENTER);
		_tourCourant.setPreferredSize(new Dimension(maze.getSizeX()*75, 50));
		container.add(_tourCourant, BorderLayout.PAGE_START);
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
				_tourCourant.setText("Tour : " + game.getTurn());
				_labyrinthe.updatePositions(game.getAgents());
				_labyrinthe.repaint();
				break;
			case "pacmanwin":
				_tourCourant.setText("Victoire des Pacmans !");
				break;
			case "ghostwin":
				_tourCourant.setText("Victoire des Fantômes !");
				break;
			case "toursecoules":
				_tourCourant.setText("Nombre de tours écoulés !");
			default:
				break;
		}
	}
}
