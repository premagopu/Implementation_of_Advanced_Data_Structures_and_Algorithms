package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

import java.util.LinkedList;

/** Breadth-first search
 *  @author rbk
 *  Version 1.0: 2017/09/08
 */

import java.util.Queue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class BFS extends GraphAlgorithm<BFS.BFSVertex> {
	public static final int INFINITY = Integer.MAX_VALUE;

	// Class to store information about a vertex in this algorithm
	public static class BFSVertex {
		boolean seen;
		public Graph.Vertex parent;
		private int distance; // distance of vertex from source

		BFSVertex(Graph.Vertex u) {
			seen = false;
			parent = null;
			setDistance(INFINITY);
		}

		public int getDistance() {
			return distance;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}

		void set(boolean seen, Vertex parent, int distance) {
			this.seen = seen;
			this.parent = parent;
			this.distance = distance;
		}
	}

	Graph.Vertex src;

	public BFS(Graph g, Graph.Vertex src) {
		super(g);
		this.src = src;
		node = new BFSVertex[g.size()];
		// Create array for storing vertex properties
		for (Graph.Vertex u : g) {
			node[u.getName()] = new BFSVertex(u);
		}
		// Set source to be at distance 0
		getVertex(src).setDistance(0);
	}

	// reinitialize allows running BFS many times, with different sources
	public void reinitialize(Graph.Vertex newSource) {
		src = newSource;
		for (Graph.Vertex u : g) {
			BFSVertex bu = getVertex(u);
			bu.seen = false;
			bu.parent = null;
			bu.setDistance(INFINITY);
		}
		getVertex(src).setDistance(0);
	}

	public void bfs() {
		Queue<Graph.Vertex> q = new LinkedList<>();
		// Set source to be at distance 0
		getVertex(src).set(true, null, 0);
		q.add(src);
		while (!q.isEmpty()) {
			Graph.Vertex u = q.remove();
			for (Graph.Edge e : u) {
				Graph.Vertex v = e.otherEnd(u);
				if (!seen(v)) {
					visit(u, v);
					q.add(v);
				}
			}
		}
	}

	boolean seen(Graph.Vertex u) {
		return getVertex(u).seen;
	}

	public Graph.Vertex getParent(Graph.Vertex u) {
		return getVertex(u).parent;
	}

	int distance(Graph.Vertex u) {
		return getVertex(u).getDistance();
	}

	// Visit a node v from u
	void visit(Graph.Vertex u, Graph.Vertex v) {
		BFSVertex bv = getVertex(v);
		bv.set(true, u, distance(u) + 1);
	}
}