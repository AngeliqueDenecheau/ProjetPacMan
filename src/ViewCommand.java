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
		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2 - 350;
		_jframe.setLocation(dx, dy);
		
		/*Color red = new Color(255, 0, 0);
		Color green = new Color(0, 255, 0);
		Color blue = new Color(0, 0, 255);*/
		
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(2, 1));
		//container.setBackground(red);
		
		JPanel top_container = new JPanel();
		top_container.setLayout(new GridLayout(1, 4));
		//top_container.setBackground(green);
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
				_restartButton.setEnabled(false);
				_runButton.setEnabled(true);
				_stepButton.setEnabled(false);
				_pauseButton.setEnabled(false);
			}
		});
		_runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_sliderTitle.setText("test");
				_controleurGame.run();
				_restartButton.setEnabled(true);
				_runButton.setEnabled(false);
				_stepButton.setEnabled(false);
				_pauseButton.setEnabled(true);
			}
		});
		_stepButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_controleurGame.step();
				_restartButton.setEnabled(true);
				_runButton.setEnabled(true);
				_stepButton.setEnabled(true);
				_pauseButton.setEnabled(false);
			}
		});
		_pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_controleurGame.pause();
				_restartButton.setEnabled(true);
				_runButton.setEnabled(true);
				_stepButton.setEnabled(true);
				_pauseButton.setEnabled(false);
			}
		});
		
		JPanel bottom_container = new JPanel();
		bottom_container.setLayout(new GridLayout(1, 2));
		//bottom_container.setBackground(blue);
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
		_nbrTours = new JLabel("Tour : 5", SwingConstants.CENTER);
		bottom_container.add(_nbrTours);
		
		container.add(top_container);
		container.add(bottom_container);
		
		_jframe.setContentPane(container);
		_jframe.setVisible(true);
	}
	
	@Override
	public void actualiser(String msg) {
		_nbrTours.setText(msg);
	}
}
