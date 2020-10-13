
public interface Observable {
	
	public void ajouterObserver(Observer observateur);
	public void supprimerObserver(Observer observateur);
	public void notifierObservers(String modification);
}
