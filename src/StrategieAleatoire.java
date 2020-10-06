import java.util.concurrent.ThreadLocalRandom;


public class StrategieAleatoire extends Strategie{
	
	public AgentAction chooseMove(Agent agent, Maze maze) {
		
		int randAction = ThreadLocalRandom.current().nextInt(0, 4 + 1);
		
		agent.setDirection(randAction);
		
		AgentAction action = new AgentAction(agent.getDirection());
		
		return action;
	}

}
