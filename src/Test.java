import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Test {
	
	private static JFrame _choosePlayer;
	private static JLabel _titreChoosePlayer;
	private static JButton _onePlayer;
	private static JButton _twoPlayers;
	
	private static JButton _retour;
	
	private static JFrame _choosePacmanStrategy;
	private static JLabel _titreChoosePacmanStrategy;
	
	private static JFrame _chooseGhostsStrategy;
	private static JLabel _titreChooseGhostsStrategy;
	
	private static JLabel _strategieAleatoire;
	private static JLabel _strategieSimple;
	private static JLabel _strategieInteractive;	
	
	private static Strategie _strategiePacman;
	private static Strategie _strategieGhosts;
	
	
	public static void choosePlayer() {
		_choosePlayer = new JFrame();
		_choosePlayer.setTitle("Jeu de Pacman");
		_choosePlayer.setSize(new Dimension(500, 500));
		Dimension windowSize = _choosePlayer.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2 - 350;
		_choosePlayer.setLocation(dx,dy);
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		
		_titreChoosePlayer = new JLabel("Choisissez le nombre de joueurs :", SwingConstants.CENTER);
		_titreChoosePlayer.setPreferredSize(new Dimension(500, 50));
		container.add(_titreChoosePlayer, BorderLayout.PAGE_START);
		JPanel button_container = new JPanel();
		button_container.setLayout(new GridLayout(2, 1));
		_onePlayer = new JButton();
		_onePlayer.setText("1 Joueur");
		button_container.add(_onePlayer);
		_twoPlayers = new JButton();
		_twoPlayers.setText("2 Joueurs");
		button_container.add(_twoPlayers);
		container.add(button_container, BorderLayout.CENTER);
		
		_onePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_choosePlayer.dispose();
				choosePacmanStrategy(1);
			}
		});
		_twoPlayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_choosePlayer.dispose();
				_strategiePacman = new StrategieMultijoueurs();
				_strategieGhosts = new StrategieMultijoueurs();
				chooseMaze();
			}
		});
		
		_choosePlayer.setContentPane(container);
		_choosePlayer.setVisible(true);
		_choosePlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void choosePacmanStrategy(int nbrJoueurs) {
		
	}
	
	public static void chooseGhostsStrategy(int nbrJoueurs) {
		
		
		
		
		
		
	}
	
	public static void chooseMaze() {
		
	}
	
	public static void startGame(Strategie strategiePacman, Strategie strategieGhosts, String mazeName) {
		//PacmanGame pacmanGame = new PacmanGame(1000, 500, "contestClassic.lay", strategie);
		//ControleurPacmanGame controleurPacmanGame = new ControleurPacmanGame(pacmanGame);
	}

	public static void main(String[] args) throws Exception {
		choosePlayer();
		//StrategieAleatoire strategie = new StrategieAleatoire();
		//StrategieSimple strategie = new StrategieSimple();
		//StrategieInteractive strategie = new StrategieInteractive();
		/*StrategieMultijoueurs strategie = new StrategieMultijoueurs();
		PacmanGame pacmanGame = new PacmanGame(1000, 500, "contestClassic.lay", strategie);
		ControleurPacmanGame controleurPacmanGame = new ControleurPacmanGame(pacmanGame);*/
	}
}
