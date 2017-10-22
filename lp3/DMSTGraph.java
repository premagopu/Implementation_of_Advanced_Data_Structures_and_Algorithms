package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;

public class DMSTGraph extends Graph {
	DMSTVertex[] dmstVertexArray;

	public static class DMSTVertex extends Vertex {
		int minEdge;
		boolean disabled;
		List<DMSTEdge> DMSTadj;
		List<DMSTEdge> DMSTrevadj;

		public DMSTVertex(Vertex u) {
			super(u);
			minEdge = Integer.MAX_VALUE;
			disabled = false;
			DMSTadj = new LinkedList<>();
			DMSTrevadj = new LinkedList<>();
		}

		boolean isDisabled() {
			return disabled;
		}

		void disable() {
			disabled = true;
		}

		public Iterator<Edge> iterator() {
			return new DMSTVertexIterator(this);
		}

		public Iterator<Edge> revIterator() {
			return new DMSTVertexRevIterator(this);
		}

		class DMSTVertexIterator implements Iterator<Edge> {
			DMSTEdge cur;
			Iterator<DMSTEdge> it;
			boolean ready;

			DMSTVertexIterator(DMSTVertex u) {
				this.it = u.DMSTadj.iterator();
				ready = false;
			}

			public boolean hasNext() {
				if (ready) {
					return true;
				}
				if (!it.hasNext()) {
					return false;
				}
				cur = it.next();
				while (cur.isDisabled() && it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return !cur.isDisabled();
			}

			public Edge next() {
				if (!ready) {
					if (!hasNext()) {
						throw new java.util.NoSuchElementException();
					}
				}
				ready = false;
				return cur;
			}

			public void remove() {
				throw new java.lang.UnsupportedOperationException();
			}
		}

		class DMSTVertexRevIterator implements Iterator<Edge> {
			DMSTEdge cur;
			Iterator<DMSTEdge> it;
			boolean ready;

			DMSTVertexRevIterator(DMSTVertex u) {
				this.it = u.DMSTrevadj.iterator();
				ready = false;
			}

			public boolean hasNext() {
				if (ready) {
					return true;
				}
				if (!it.hasNext()) {
					return false;
				}
				cur = it.next();
				while (cur.isDisabled() && it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return !cur.isDisabled();
			}

			public Edge next() {
				if (!ready) {
					if (!hasNext()) {
						throw new java.util.NoSuchElementException();
					}
				}
				ready = false;
				return cur;
			}

			public void remove() {
				throw new java.lang.UnsupportedOperationException();
			}
		}

	}

	public static class DMSTEdge extends Edge {

		boolean disabled;

		void disabled() {
			disabled = true;
		}

		DMSTEdge(Vertex from, Vertex to, int weight, int name) {
			super(from, to, weight, name);
		}

		boolean isDisabled() {
			DMSTVertex xfrom = (DMSTVertex) from;
			DMSTVertex xto = (DMSTVertex) to;
			return disabled || xfrom.isDisabled() || xto.isDisabled();
		}

	}

	public DMSTGraph(Graph g) {
		super(g);
		dmstVertexArray = new DMSTVertex[2 * g.size()];

		for (Vertex u : g) {
			dmstVertexArray[u.getName()] = new DMSTVertex(u);
		}

		// Make copy of edges
		DMSTEdge dmstEdge;
		for (Vertex u : g) {
			for (Edge e : u.adj) {
				Vertex v = e.otherEnd(u);
				DMSTVertex x1 = getVertex(u);
				DMSTVertex x2 = getVertex(v);
				dmstEdge = new DMSTEdge(x1, x2, e.weight, e.getName());
				x1.DMSTadj.add(dmstEdge);
				x2.DMSTrevadj.add(dmstEdge);
				if (x2.minEdge > e.weight)
					x2.minEdge = e.weight;
			}
		}
	}

	DMSTVertex getVertex(Vertex u) {
		return Vertex.getVertex(dmstVertexArray, u);
	}

	@Override
	public Iterator<Vertex> iterator() {
		return new DMSTGraphIterator(this);
	}

	class DMSTGraphIterator implements Iterator<Vertex> {
		Iterator<DMSTVertex> it;
		DMSTVertex xcur;

		DMSTGraphIterator(DMSTGraph xg) {
			this.it = new ArrayIterator<DMSTVertex>(xg.dmstVertexArray, 0, xg.size() - 1); // Iterate
			// over
			// existing
			// elements
			// only
		}

		public boolean hasNext() {
			if (!it.hasNext()) {
				return false;
			}
			xcur = it.next();
			while (xcur.isDisabled() && it.hasNext()) {
				xcur = it.next();
			}
			return !xcur.isDisabled();
		}

		public Vertex next() {
			return xcur;
		}

		public void remove() {
		}

	}

	public DMSTVertex getDMSTVertex(int n) {
		return dmstVertexArray[n - 1];
	}

	public DMSTVertex[] getDMSTVertexArray() {
		return dmstVertexArray;
	}

}