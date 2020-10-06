import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ViewPacmanGame implements Observer {
	private JFrame _jframe;
	private JLabel _tourCourant;
	private JPanel _labyrinthe;
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
		//int dx = centerPoint.x - windowSize.width / 2;
		//int dy = centerPoint.y - windowSize.height / 2 - 350;
		_jframe.setLocation(1400, 0);
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		_tourCourant = new JLabel("Tour courant : 0", SwingConstants.CENTER);
		_tourCourant.setPreferredSize(new Dimension(maze.getSizeX()*75, 50));
		container.add(_tourCourant, BorderLayout.PAGE_START);
		_labyrinthe = new PanelPacmanGame(maze);
		container.add(_labyrinthe, BorderLayout.CENTER);
		_jframe.add(container);
		_jframe.setVisible(true);
	}

	@Override
	public void actualiser(Game game, String modification) {
		switch(modification) {
			case "taketurn":
				System.out.println("Test");
				_labyrinthe.repaint();
		}
	}
}
