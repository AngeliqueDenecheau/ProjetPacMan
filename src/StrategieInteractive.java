public class StrategieInteractive extends Strategie {
	private int _action = 4;

	@Override
	public AgentAction getAction(Agent agent, Maze maze) {
		return (isLegalMove(agent, new AgentAction(_action), maze)) ? new AgentAction(_action) : new AgentAction(4);
	}

	public void setAction(int code) {
		_action = code;
	}
}
