import java.util.ArrayList;
import java.util.List;
import java.lang.Math.*;

public class Graph {
	
	public class Node {
		public int numNode;
		public ArrayList<Integer> adjacent;
	}
	public class Edge {
		public int From;
		public int To;
	}
	
	public class Pair{
		int x;
		int y;
	}
	
	private ArrayList<Node> nodes; //list d'adjacence
	
	//make graph from maze
	public Graph() {
		this.nodes = new ArrayList<Node>();
	}
	
public Graph(Game game, boolean avoidGhosts) {
		
		this.nodes = new ArrayList<Node>();
		
		ArrayList<Pair> ghostPositions = new ArrayList<Pair>();//positions of all ghosts
		
		if(avoidGhosts) {
			for(int a = 0; a < game.getAgents().size(); a++) {
				if(game.getAgents().get(a) instanceof Ghost) {
					Pair p = new Pair();
					p.x = game.getAgents().get(a).getPosition().getX();
					p.y = game.getAgents().get(a).getPosition().getY();
					ghostPositions.add(p);
				}
			}
		}
		
		
		
		
		
		
		Maze maze = game.getMaze();
		int height = maze.getSizeY();
		int width = maze.getSizeX();
		
		
		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {
				//System.out.println(i + ", " + j + " : " + (j*width + i) + " , isWall: " + maze.isWall(i, j));
				Node temp = new Node();
				temp.numNode = (j*width + i);
				temp.adjacent = new ArrayList<Integer>();
				
				Pair p = new Pair();
				p.x = i;
				p.y = j;
				
				if(!(maze.isWall(i, j) || (avoidGhosts && ghostPositions.contains(p))) ) {
					//check what directions are available
					if((i-1) > 0 && (i-1) < width && !maze.isWall((i-1), j)) { //east
						temp.adjacent.add(j*width + (i-1));
					}
					
					if((i+1) > 0 && (i+1) < width && !maze.isWall((i+1), j)) { //west
						temp.adjacent.add(j*width + (i+1));
					}
					
					if((j-1) > 0 && (j-1) < height && !maze.isWall(i, (j-1))) { //south
						temp.adjacent.add((j-1)*width + i);
					}
					
					if((j+1) > 0 && (j+1) < height && !maze.isWall(i, (j+1))) { //north
						temp.adjacent.add((j+1)*width + i);
					
					}
				}
				
				this.nodes.add(temp);
			}
		}
		//display();
	}
	
	public Graph clone() {
		
		Graph g = new Graph();
		for(int i = 0; i < nodes.size(); i++) {
			g.nodes.add(nodes.get(i));
		}
		
		return g;
	}
	
	
	//update the graph taking into account the position of the ghosts (treating them as walls)
	public void updateGhosts(Game game, Maze maze) {
		
		ArrayList<Agent> agents = game.getAgents();
		
		for(int i = 0; i < agents.size(); i++) {
			if(agents.get(i) instanceof Ghost) {//for all ghosts
				//get their cell number to be removed from nodes
				int cell = agents.get(i).getPosition().getY()*maze.getSizeX() + agents.get(i).getPosition().getX();
				//System.out.println("cell: " + cell);
				
				for(int c = 0; c < nodes.size(); c++) {
					if(nodes.get(c).adjacent.contains(cell)) {//remove as neighbor if possible
						int index = nodes.get(c).adjacent.indexOf(cell);//get index of neighbor to remove
						nodes.get(c).adjacent.remove(index);
						
					}
					if(nodes.get(c).numNode == cell) {//remove all neighbors
						nodes.get(c).adjacent.clear();
					}
				}
			}
		}
		
	}
	
	
	//distance euclidienne entre start et goal
	public double heuristic(int start, int goal, Maze maze) { 
		double startX = start % maze.getSizeX();
		double startY = start / maze.getSizeX();
		
		double goalX = goal % maze.getSizeX();
		double goalY = goal / maze.getSizeX();
		
		double distance = Math.sqrt(Math.pow(startX-goalX, 2d) + Math.pow(startY-goalY, 2d));
		return distance;
	}
	
	//returns index of min value in array
	public int minScore(ArrayList<Double> array, ArrayList<Integer> indexes) {
		int minIndex = indexes.get(0);
		for(int i = 1; i < indexes.size(); i++) {
			if(array.get(indexes.get(i)) < array.get(minIndex)) {
				minIndex = indexes.get(i);
			}
		}
		return minIndex;
	}
	
	
	public boolean isInKeys(ArrayList<Edge> cameFrom, int current) {
		for(Edge e : cameFrom){
			if(e.To == current) {
				return true;
			}
		}
		return false;
	}
	
	public int getForKey(ArrayList<Edge> cameFrom, int key) {
		for(Edge e : cameFrom){
			if(e.To == key) {
				return e.From;
			}
		}
		return 0;
	}
	
	
	public int reconstruct_path(ArrayList<Edge> cameFrom, int current) {
		ArrayList <Integer> path = new ArrayList<Integer>();
		path.add(current);
		
		while(isInKeys(cameFrom, current)) {
			current = getForKey(cameFrom, current);
			path.add(current);
		}
		//System.out.println("path: " + path);
		
		return path.get(path.size()-2); //get next move
		
	}
	
	public int A_Star(int start, int goal, Maze maze) {
		ArrayList<Integer> openSet = new ArrayList<Integer>();
		openSet.add(start);
		
		// For node n, cameFrom[n] is the node immediately preceding it on the cheapest path from start to n currently known.
		ArrayList<Edge> cameFrom = new ArrayList<Edge>();
		
		// For node n, gScore[n] is the cost of the cheapest path from start to n currently known.
		ArrayList<Double> gScore = new ArrayList<Double>();
		for(int i = 0; i < maze.getSizeX()*maze.getSizeY(); i++) {
			gScore.add(Double.POSITIVE_INFINITY);
		}
		gScore.set(start, 0d);
		
		
		// For node n, fScore[n] := gScore[n] + h(n). fScore[n] represents our current best guess as to how short a path from start to finish can be if it goes through n.
		ArrayList<Double> fScore = new ArrayList<Double>();
		for(int i = 0; i < maze.getSizeX()*maze.getSizeY(); i++) {
			fScore.add(Double.POSITIVE_INFINITY);
		}
		fScore.set(start, heuristic(start, goal, maze));
		
		while(! openSet.isEmpty()) {
			//System.out.println("Goal: " + goal);
			int current = minScore(fScore, openSet);
			if(current == goal) {
				return reconstruct_path(cameFrom, current);
			}
			
			
			//System.out.println("OpenSet: " + openSet);
			//System.out.println("current: " + current);
			//System.out.println("fscore: " + fScore);
			
			openSet.remove(openSet.indexOf(current));
			ArrayList<Integer> neighbors = nodes.get(current).adjacent;
			for(int a : neighbors) {
				//System.out.println("neighbor: "+ a);
				double tentative_gScore = gScore.get(current) + 1;//on suppose que les vertex ont un poids de 1
				if(tentative_gScore < gScore.get(a)) {// This path to neighbor is better than any previous one. Record it!
					Edge e = new Edge();
					e.From = current;
					e.To = a;
					cameFrom.add(e);
					gScore.set(a, tentative_gScore);
					fScore.set(a, tentative_gScore + heuristic(a, goal, maze));
					if(! openSet.contains(a)) {//add neighbor if not already in openSet
						openSet.add(a);
						//System.out.println("neighbor: "+ a + " added to openset");
					}
				}
			}
		}
		//no path 
		return -1;
	}
	
	public void display() {
		String temp;
		for(int i = 0; i < this.nodes.size(); i++) {
			temp = this.nodes.get(i).numNode + " -> ";
			for(int a = 0; a < this.nodes.get(i).adjacent.size(); a++) {
				temp += this.nodes.get(i).adjacent.get(a) + ", ";
				
			}
			System.out.println(temp);
			
		}
	}
	
	

}












