

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ViewSimpleGame implements Observer {
	private JFrame _jframe;
	private JLabel _tourCourant;
	private JLabel _etat;
	private InterfaceControleur _controleurGame;
	
	public ViewSimpleGame(Observable observable, InterfaceControleur controleur) {
		observable.ajouterObserver(this);
		_controleurGame = controleur;
		
		_jframe = new JFrame();
		_jframe.setTitle("Game");
		_jframe.setSize(new Dimension(500, 500));
		Dimension windowSize = _jframe.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		//int dx = centerPoint.x - windowSize.width / 2;
		//int dy = centerPoint.y - windowSize.height / 2 - 350;
		_jframe.setLocation(1400, 0);
		
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(2, 1));
		_tourCourant = new JLabel("Tour courant : ", SwingConstants.CENTER);
		container.add(_tourCourant);
		_etat = new JLabel("", SwingConstants.CENTER);
		container.add(_etat);
		_jframe.add(container);
		_jframe.setVisible(true);
		_jframe.setResizable(false);
	}

	@Override
	public void actualiser(Game game, String modification) {
		_etat.setText(modification);
	}
}
