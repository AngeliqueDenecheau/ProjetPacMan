//Classe affichant le panneau de commandes de la partie

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ViewCommand implements Observer{
	private JFrame _jframe;
	private JButton _restartButton;
	private JButton _runButton;
	private JButton _stepButton;
	private JButton _pauseButton;
	private JLabel _sliderTitle;
	private JSlider _slider;
	private JLabel _nbrTours;
	private InterfaceControleur _controleurGame;
	
	public ViewCommand(Observable observable, InterfaceControleur controleur) {
		observable.ajouterObserver(this);
		_controleurGame = controleur;
		
		_jframe = new JFrame();
		_jframe.setTitle("Game");
		_jframe.setSize(new Dimension(1400, 700));
		Dimension windowSize = _jframe.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		_jframe.setLocation(0, 0);
		
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(2, 1));
		
		JPanel top_container = new JPanel();
		top_container.setLayout(new GridLayout(1, 4));
		Icon restartIcon = new ImageIcon("./icons/icon_restart.png");
		_restartButton = new JButton(restartIcon);
		_restartButton.setEnabled(false);
		top_container.add(_restartButton);
		Icon runIcon = new ImageIcon("./icons/icon_run.png");
		_runButton = new JButton(runIcon);
		top_container.add(_runButton);
		Icon stepIcon = new ImageIcon("./icons/icon_step.png");
		_stepButton = new JButton(stepIcon);
		_stepButton.setEnabled(false);
		top_container.add(_stepButton);
		Icon pauseIcon = new ImageIcon("./icons/icon_pause.png");
		_pauseButton = new JButton(pauseIcon);
		_pauseButton.setEnabled(false);
		top_container.add(_pauseButton);
		_restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_controleurGame.start();
			}
		});
		_runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_controleurGame.run();
			}
		});
		_stepButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_controleurGame.step();
			}
		});
		_pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_controleurGame.pause();
			}
		});
		
		JPanel bottom_container = new JPanel();
		bottom_container.setLayout(new GridLayout(1, 2));
		JPanel slider_container = new JPanel();
		slider_container.setLayout(new GridLayout(2, 1));
		_sliderTitle = new JLabel("Nombre de tours par seconde", SwingConstants.CENTER);
		slider_container.add(_sliderTitle);
		_slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		_slider.setMajorTickSpacing(1);
		_slider.setPaintTicks(true);
		_slider.setPaintLabels(true);
		slider_container.add(_slider);
		_slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				_controleurGame.setTime(_slider.getValue());
			}
		});
		bottom_container.add(slider_container);
		_nbrTours = new JLabel("Tour : 0", SwingConstants.CENTER);
		bottom_container.add(_nbrTours);
		
		container.add(top_container);
		container.add(bottom_container);
		
		_jframe.setContentPane(container);
		_jframe.setVisible(true);
		
		if(_controleurGame.isInteractive() || _controleurGame.isMultijoueurs()) {
			_runButton.setEnabled(false);
			_slider.setEnabled(false);
			_sliderTitle.setEnabled(false);
			_nbrTours.setText("Appuiez sur les touches flêchées pour bouger le Pacman.");
		}
		
		_jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actualiser(Game game, String modification) {
		switch(modification) {
		case "pause":
			_restartButton.setEnabled(true);
			_runButton.setEnabled(true);
			_stepButton.setEnabled(true);
			_pauseButton.setEnabled(false);
			break;
		case "step":
			_restartButton.setEnabled(true);
			_runButton.setEnabled(true);
			_stepButton.setEnabled(true);
			_pauseButton.setEnabled(false);
			break;
		case "start":
			_nbrTours.setText("Tour : " + game.getTurn());
			_restartButton.setEnabled(false);
			if(_controleurGame.isInteractive() || _controleurGame.isMultijoueurs()) break;
			_runButton.setEnabled(true);
			_stepButton.setEnabled(false);
			_pauseButton.setEnabled(false);
			break;
		case "run":
			_restartButton.setEnabled(true);
			_runButton.setEnabled(false);
			_stepButton.setEnabled(false);
			_pauseButton.setEnabled(true);
			break;
		case "taketurn":
			_nbrTours.setText("Tour : " + game.getTurn());
			break;
		case "ghostwin":
		case "pacmanwin":
		case "toursecoules":
			System.out.println("fin");
			_restartButton.setEnabled(true);
			_runButton.setEnabled(false);
			_stepButton.setEnabled(false);
			_pauseButton.setEnabled(false);
			break;
		case "keypressed":
			_restartButton.setEnabled(true);
			_runButton.setEnabled(false);
			_stepButton.setEnabled(false);
			_pauseButton.setEnabled(false);
			break;
		default:
			break;
		}
	}
}
