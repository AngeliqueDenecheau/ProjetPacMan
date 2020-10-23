public abstract class Strategie {
	
	protected Game _game;
	
	public abstract AgentAction getAction(Agent agent, Maze maze);
	
	public static boolean isLegalMove(Agent agent, AgentAction action, Maze maze) {
		return !maze.isWall(agent.getPosition().getX() + action.get_vx(), agent.getPosition().getY() + action.get_vy());
	}
	
	public void setGame(Game game) {_game = game;}
}
