package SortLib;

import java.io.Serializable;
import java.util.ArrayList;

public class SortSolution implements Serializable{
	private int[] initial;
	private int[] e;
	private ArrayList<Action2> actions = new ArrayList<Action2>();
	private String title = "add title";
	private String desc = "add description";
	private int compares = 0;
	private int swaps = 0;
	private boolean verbose = false;
	private int currentIndex = -1;
	
	public SortSolution(int[] e) {
		this.e = new int[e.length];
		initial = new int[e.length];
		System.arraycopy(e, 0, this.e, 0, e.length);
		System.arraycopy(e, 0, initial, 0, e.length);
		actions.add(new Action2(-1, -1, 0, ""));				
//		for(int i=0;i<e.length;i++) {
//			System.out.print(e[i]+",");
//		}
//		System.out.println();
	}

	public void setVerboseMode(boolean verbose) {
		this.verbose = verbose;
	}
	
	private void vPrint(String act) {
		if (verbose) {
			if (e.length <= 10) {
				for(int i=0;i<e.length;i++) {
					System.out.print(e[i]+",");
				}
				System.out.println();
			}
			System.out.println(act);
		}
	}
	
	public boolean compare_lte(int idx0, int idx1, String note) {
		compares++;
		actions.add(new Action2(idx0, idx1, 1, note));
		vPrint("compare: ("+idx0+","+idx1+") = ("+e[idx0]+","+e[idx1]+") "+(e[idx0] <= e[idx1])+" "+note);
		return e[idx0] <= e[idx1];
	}

	public boolean compare_lte(int idx0, int idx1) {
		return compare_lte(idx0, idx1, "");
	}

	public void swap(int idx0, int idx1, String note) {
		swaps++;
		actions.add(new Action2(idx0, idx1, 2, note));
		vPrint("swap: ("+idx0+","+idx1+") = ("+e[idx0]+","+e[idx1]+") "+note);
		int tmp = e[idx0];
		e[idx0] = e[idx1];
		e[idx1] = tmp;
	}

	public void swap(int idx0, int idx1) {
		swap(idx0, idx1, "");
	}

	public int getLength() {
		return e.length;
	}
	
	public int getActionCount() {
		return actions.size();
	}
	
	public int getCompares() {
		return compares;
	}
	
	public int getSwaps() {
		return swaps;
	}
	
	public boolean verify() {

		for (int i = 0; i < e.length-1; i++) {
			if (e[i] > e[i+1]) {
				actions.add(new Action2(-1, -1, 4, ""));				
				return false;
			}
		}

		actions.add(new Action2(-1, -1, 3, ""));		
		return true;
	}
	
	public void putTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void putDescription(String desc) {
		this.desc = desc;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public EState getPlayback(int index) {
		System.arraycopy(initial, 0, e, 0, initial.length);
		
		Action2 a2 = null;
		for (int i=0; i<actions.size() && i<=index; i++) {
			a2 = actions.get(i);
			if (a2.type == 2) {
				int tmp = e[a2.a];
				e[a2.a] = e[a2.b];
				e[a2.b] = tmp;
			}
		}
//		System.out.println("index: "+index);
		return new EState(e,a2);
	}
}

class EState {
	int[] e;
	Action2 action;
	
	public EState (int[] pe, Action2 pAction) {
		this.e = new int[pe.length];
		
		System.arraycopy(pe, 0, this.e, 0, pe.length);
		this.action = pAction;
	}
}

class Action2 implements Serializable{
	int a, b;
	int type;//0-initial state, 1-compare, 2-swap, 3-success (final state), 4-failed (final state)
	String note = "";

	public Action2(int a, int b, int type, String note) {
		this.a = a;
		this.b = b;
		this.type = type;
		this.note = note;
	}
}