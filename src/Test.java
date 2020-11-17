import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Test {
	
	private static JFrame _jframe;
	private static JFrame _jframeMaze;
	
	private static Strategie _strategiePacman;
	private static Strategie _strategieGhosts;
	
	private static String _lookandfeel = "com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme";
	private static String _font = "Roboto";
	
	public static void main(String[] args) throws Exception {
		_jframe = new JFrame();
		_jframe.setTitle("Jeu de Pacman");
		_jframeMaze = new JFrame();
		_jframeMaze.setTitle("Erreur");
		try {
			UIManager.setLookAndFeel(_lookandfeel);
			//UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (Exception e) {
		}
		choosePlayer();
	}
	
	public static void choosePlayer() {
		_jframe.setSize(new Dimension(400, 300));
		Dimension windowSize = _jframe.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		_jframe.setLocation(dx,dy);
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		
		JLabel titre = new JLabel("Choisissez le nombre de joueurs :", SwingConstants.CENTER);
		//titre.setFont(new Font(_font, Font.BOLD, 20));
		titre.setPreferredSize(new Dimension(400, 50));
		container.add(titre, BorderLayout.PAGE_START);
		
		JPanel button_container = new JPanel();
		button_container.setLayout(new BoxLayout(button_container, BoxLayout.Y_AXIS));
		button_container.add(Box.createRigidArea(new Dimension(0, 15)));
		
		JButton zeroPlayer = new JButton("0 Joueur");
		//zeroPlayer.setFont(new Font(_font, Font.PLAIN, 16));
		zeroPlayer.setMargin(new Insets(10, 30, 10, 30));
		zeroPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		button_container.add(zeroPlayer);
		button_container.add(Box.createRigidArea(new Dimension(0, 15)));
		JButton onePlayer = new JButton("1 Joueur");
		//onePlayer.setFont(new Font(_font, Font.PLAIN, 16));
		onePlayer.setMargin(new Insets(10, 30, 10, 30));
		onePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		button_container.add(onePlayer);
		button_container.add(Box.createRigidArea(new Dimension(0, 15)));
		JButton twoPlayers = new JButton("2 Joueurs");
		//twoPlayers.setFont(new Font(_font, Font.PLAIN, 16));
		twoPlayers.setMargin(new Insets(10, 25, 10, 25));
		twoPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
		button_container.add(twoPlayers);
		
		container.add(button_container, BorderLayout.CENTER);
		
		zeroPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_jframe.dispose();
				chooseStrategies(0);
			}
		});		
		onePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_jframe.dispose();
				chooseStrategies(1);
			}
		});
		twoPlayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_jframe.dispose();
				chooseStrategies(2);
			}
		});
		
		_jframe.setContentPane(container);
		_jframe.setVisible(true);
		_jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void chooseStrategies(int nbrJoueurs) {
		_jframe.setSize(new Dimension(500, 350));
		Dimension windowSize = _jframe.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		_jframe.setLocation(dx,dy);
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		
		JLabel titre = new JLabel("Choisissez les stratégies pour les Pacmans et les Fantômes :", SwingConstants.CENTER);
		//titre.setFont(new Font(_font, Font.BOLD, 16));
		titre.setPreferredSize(new Dimension(400, 50));
		container.add(titre, BorderLayout.PAGE_START);
		
		JPanel strategies_container = new JPanel();
		
		JPanel strategiesPacmanContainer = new JPanel();
		strategiesPacmanContainer.setLayout(new GridBagLayout());
		JPanel strategiesGhostContainer = new JPanel();
		strategiesGhostContainer.setLayout(new GridBagLayout());
		
		JLabel titreStrategiePacman = new JLabel("Stratégie Pacman : ", SwingConstants.CENTER);
		//titreStrategiePacman.setFont(new Font(_font, Font.PLAIN, 16));
		strategiesPacmanContainer.add(titreStrategiePacman);
		String[] strategiesPacman = (nbrJoueurs == 2) ? new String[] {"Multijoueurs"} : new String[] {"Aléatoire", "Simple"};
		JComboBox<String> choixStrategiesPacmans = new JComboBox<String>(strategiesPacman);
		//choixStrategiesPacmans.setFont(new Font(_font, Font.PLAIN, 16));
		strategiesPacmanContainer.add(choixStrategiesPacmans);
		
		JLabel titreStrategieGhost = new JLabel("Stratégie Fantôme : ", SwingConstants.CENTER);
		//titreStrategieGhost.setFont(new Font(_font, Font.PLAIN, 16));
		strategiesGhostContainer.add(titreStrategieGhost);
		String[] strategiesGhost = (nbrJoueurs == 2) ? new String[] {"Multijoueurs"} : new String[] {"Aléatoire", "Simple", "A*"};
		JComboBox<String> choixStrategiesGhosts = new JComboBox<String>(strategiesGhost);
		//choixStrategiesGhosts.setFont(new Font(_font, Font.PLAIN, 16));
		strategiesGhostContainer.add(choixStrategiesGhosts);
		
		JPanel mazeContainer = new JPanel();
		mazeContainer.setLayout(new GridBagLayout());
		
		JLabel titreMazeFile = new JLabel("Nom du fichier labyrinthe : ", SwingConstants.CENTER);
		//titreMazeFile.setFont(new Font(_font, Font.PLAIN, 16));
		mazeContainer.add(titreMazeFile);
		
		List<String> mazeFilesName;
        File f = new File("./layouts");
        mazeFilesName = Arrays.asList(f.list());
        for(int i = 0; i < mazeFilesName.size(); i++) {
        	mazeFilesName.set(i, mazeFilesName.get(i).substring(0, mazeFilesName.get(i).length() - 4));
        }
        mazeFilesName.sort(Comparator.comparing(String::toString));
		JComboBox<String> mazeFileField = new JComboBox(mazeFilesName.toArray());
		mazeFileField.setSelectedIndex((mazeFilesName.indexOf("capsuleClassic") != -1) ? mazeFilesName.indexOf("capsuleClassic") : 0);
		//mazeFileField.setFont(new Font(_font, Font.PLAIN, 16));
		mazeFileField.setPreferredSize(new Dimension(250, 25));
		mazeContainer.add(mazeFileField);
		
		if(nbrJoueurs == 1) {
			DefaultComboBoxModel<String> interractive = new DefaultComboBoxModel<String>(new String[] {"Interactive"});
			choixStrategiesPacmans.setModel(interractive);
			choixStrategiesPacmans.setEnabled(false);
			strategies_container.setLayout(new GridLayout(4, 1));
			JPanel campContainer = new JPanel();
			campContainer.setLayout(new GridBagLayout());
			JLabel titreCamp = new JLabel("Jouer un : ", SwingConstants.CENTER);
			campContainer.add(titreCamp);
			ButtonGroup buttonGroup = new ButtonGroup();
		    JRadioButton pacman = new JRadioButton("Pacman");
		    //pacman.setFont(new Font(_font, Font.PLAIN, 16));
		    JRadioButton ghost = new JRadioButton("Fantôme");
		    //ghost.setFont(new Font(_font, Font.PLAIN, 16));
		    pacman.setSelected( true );
	        campContainer.add(pacman);
	        campContainer.add(ghost);
	        buttonGroup.add(pacman);
	        buttonGroup.add(ghost);
	        pacman.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == pacman) {
						choixStrategiesPacmans.setModel(interractive);
						choixStrategiesPacmans.setEnabled(false);
						choixStrategiesGhosts.setModel(new DefaultComboBoxModel<String>(new String[] {"Aléatoire", "Simple", "A*"}));
						choixStrategiesGhosts.setEnabled(true);
					}
				}
			});
	        ghost.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == ghost) {
						choixStrategiesGhosts.setModel(interractive);
						choixStrategiesGhosts.setEnabled(false);
						choixStrategiesPacmans.setModel(new DefaultComboBoxModel<String>(new String[] {"Aléatoire", "Simple"}));
						choixStrategiesPacmans.setEnabled(true);
					}
				}
			});
	        strategies_container.add(campContainer);
		}else {
			strategies_container.setLayout(new GridLayout(3, 1));
		}
		
		if(nbrJoueurs == 2) {
			choixStrategiesPacmans.setEnabled(false);
			choixStrategiesGhosts.setEnabled(false);
		}
		
		strategies_container.add(strategiesPacmanContainer);
		strategies_container.add(strategiesGhostContainer);
		strategies_container.add(mazeContainer);
		container.add(strategies_container, BorderLayout.CENTER);
		
		JPanel button_container = new JPanel();
		button_container.setLayout(new BoxLayout(button_container, BoxLayout.X_AXIS));
		
		button_container.add(Box.createHorizontalGlue());
		JButton retour = new JButton("Retour");
		//retour.setFont(new Font(_font, Font.PLAIN, 16));
		retour.setMargin(new Insets(10, 30, 10, 30));
		button_container.add(retour);
		button_container.add(Box.createRigidArea(new Dimension(25, 0)));
		JButton valider = new JButton("Valider");
		//valider.setFont(new Font(_font, Font.PLAIN, 16));
		valider.setMargin(new Insets(10, 30, 10, 30));
		button_container.add(valider);
		button_container.add(Box.createHorizontalGlue());
		container.add(button_container, BorderLayout.PAGE_END);
		
		retour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_jframe.dispose();
				choosePlayer();
			}
		});
		
		valider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nbrJoueurs == 2) {
					_strategiePacman = new StrategieMultijoueurs();
					_strategieGhosts = _strategiePacman;
				}else {
					switch((String) choixStrategiesPacmans.getSelectedItem()) {
						case "Aléatoire":
							_strategiePacman = new StrategieAleatoire();
							break;
						case "Simple":
							_strategiePacman = new StrategieSimple();
							break;
						case "Interactive":
							_strategiePacman = new StrategieInteractive();
							break;
					}
					switch((String) choixStrategiesGhosts.getSelectedItem()) {
						case "Aléatoire":
							_strategieGhosts = new StrategieAleatoire();
							break;
						case "Simple":
							_strategieGhosts = new StrategieSimple();
							break;
						case "Interactive":
							_strategieGhosts = new StrategieInteractive();
							break;
						case "A*":
							_strategieGhosts = new StrategieA_etoile();
							break;
					}
				}
				System.out.println(mazeFileField.getSelectedItem());
				System.out.println(mazeFileField.getSelectedItem().toString());
				String mazeName = mazeFileField.getSelectedItem().toString() + ".lay";
				
				File maze = new File("./layouts/" + mazeName);
				if(!maze.exists()) { 
					mazeIncorrect("Ce fichier n'existe pas.");
				}else if(_strategiePacman instanceof StrategieInteractive && getNbrAgent('P', mazeName) != 1) {
					System.out.println("1 " + getNbrAgent('P', mazeName));
					mazeIncorrect("Vous devez choisir un labyrinthe avec UN seul Pacman si vous avez sélectionné la stratégie Interactive.");
				}else if(_strategieGhosts instanceof StrategieInteractive && getNbrAgent('G', mazeName) != 1){
					System.out.println("2 " + getNbrAgent('G', mazeName));
					mazeIncorrect("Vous devez choisir un labyrinthe avec UN seul Fantôme si vous avez sélectionné la stratégie Interactive.");
				}else if(_strategiePacman instanceof StrategieMultijoueurs && (getNbrAgent('P', mazeName) != 1 || getNbrAgent('G', mazeName) != 1)) {
					System.out.println("3 " + getNbrAgent('P', mazeName) + " " + getNbrAgent('G', mazeName));
					mazeIncorrect("Vous devez choisir un labyrinthe avec UN seul Pacman et UN seul Fantôme si vous avez sélectionné la stratégie Multijoueurs.");
				}else {
					_jframe.dispose();
					startGame(mazeName);					
				}
			}
		});
		
		_jframe.setContentPane(container);
		_jframe.setVisible(true);
		_jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static int getNbrAgent(char agent, String mazeName) {
		int nbAgent = 0;
		try {
			InputStream ips;
			ips = new FileInputStream("./layouts/" + mazeName);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				for(int i = 0; i < ligne.length(); i++) {
					if(ligne.charAt(i) == agent) { 
						nbAgent++;
					}
				}
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return nbAgent;
	}
	
	public static void mazeIncorrect(String erreur) {
		_jframeMaze.setSize(new Dimension(900, 200));
		Dimension windowSize = _jframeMaze.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		_jframeMaze.setLocation(dx,dy);
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		container.add(Box.createVerticalGlue());
		JLabel titre = new JLabel(erreur, SwingConstants.CENTER);
		//titre.setFont(new Font(_font, Font.PLAIN, 16));
		titre.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(titre);
		container.add(Box.createRigidArea(new Dimension(0, 25)));
		JButton ok = new JButton("Ok");
		//ok.setFont(new Font(_font, Font.PLAIN, 16));
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		ok.setMargin(new Insets(10, 30, 10, 30));
		container.add(ok);
		container.add(Box.createVerticalGlue());
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_jframeMaze.dispose();
			}
		});
		
		_jframeMaze.setContentPane(container);
		_jframeMaze.setVisible(true);
	}
	
	public static void startGame(String mazeName) {
		Game game = new Game(1000, 500, mazeName, _strategiePacman, _strategieGhosts);
		ControleurGame controleurPacmanGame = new ControleurGame(game);
	}
}
