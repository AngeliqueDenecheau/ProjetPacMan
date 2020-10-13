import java.awt.event.KeyEvent;

public interface InterfaceControleur {

	public void start();	
	public void step();	
	public void run();	
	public void pause();
	public void setTime(double time);
	public void keyPressed(int code);
	public boolean isInteractive();
}
