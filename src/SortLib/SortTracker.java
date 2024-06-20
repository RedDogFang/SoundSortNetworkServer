package SortLib;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class SortTracker implements Serializable{
	int[] e;
	int e2[];
	int e2Idx = 0;
	ArrayList<SortSolution> solutions = new ArrayList<SortSolution>();
	Random rnd;
	int seed;
	int style;

	int lineWidth = 10;
	int offset = 10;
	int windowHeight = 500;

	public SortTracker(int pSeed, int elements, int style) {
		this.style = style;
		e = new int[elements];
		seed = pSeed;

		if (seed == -1) {
			rnd = new Random();
			seed = rnd.nextInt();
			System.out.println("seed: "+seed);
		}
		
		reset();
	}

	private void reset() {
		rnd = new Random(seed);

		switch (style) {
		case 0: // random, no dupes
			// fill with increasing values
			for (int i = 0; i < e.length; i++) {
				e[i] = i+1;
			}
			// swap each one with a random location
			for (int i = 0; i < e.length; i++) {
				int tmp = e[i];
				int idx = rnd.nextInt(e.length);
				e[i] = e[idx];
				e[idx] = tmp;
			}
			break;
		case 1: // increasing
			for (int i = 0; i < e.length; i++) {
				e[i] = i+1;
			}
			break;
		case 2: // decreasing
			for (int i = 0; i < e.length; i++) {
				e[i] = e.length - i;
			}
			break;
		case 3: // random with dupes
			for (int i = 0; i < e.length; i++) {
				e[i] = 1+rnd.nextInt(e.length - 1);
			}
			break;
		case 4: // all the same
			for (int i = 0; i < e.length; i++) {
				e[i] = 1;
			}
			break;
		}
		
		e2Idx = 0;
		e2 = new int[e.length];
		System.arraycopy(e, 0, e2, 0, e.length);
	}

	public SortSolution addSolution() {
		SortSolution soln = new SortSolution(e); 
		solutions.add(soln);
		return soln;
	}
	
	private void setGraphics(int lineWidth, int offset, int windowHeight) {
		this.lineWidth = lineWidth;
		this.offset = offset;
		this.windowHeight = windowHeight;
	}
}