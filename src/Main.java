import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

import SimpleNetworking.ClientRep;
import SimpleNetworking.Server;
import SortLib.SortDisplayer;
import SortLib.SortTracker;

public class Main {

	public Main() {
	}

	static int elementCount = 100;
	static int seed = 0;
	static int style = 0;

	/** main program (entry point) */
	public static void main(String[] args) {
		// SortTracker st = new SortTracker(seed,elementCount,style);

		// new SelectionSort(st.addSolution());
		// new BubbleSort(st.addSolution());
//		new InsertionSort(st.addSolution());
		// new HeapSort(st.addSolution());
		// new QuickSort(st.addSolution());
//		new CombSort(st.addSolution());

		Server server = new Server(8080);
		ArrayList<ClientRep> clientReps = server.waitForNextClient();
		ClientRep clientRep = clientReps.get(0);

		StartupConfig  startupConfig = new StartupConfig(elementCount, seed, style);
		server.sendObject(clientRep, startupConfig);

		ArrayList<Object> objects = server.waitForNextObject(clientRep);
		SortTracker st = (SortTracker) objects.get(0);

		SortDisplayer sd = new SortDisplayer(st);
	}
}

class StartupConfig implements Serializable{
	final int code = 101;
	int elementCount = 100;
	int seed = 0;
	int style = 0;

	public StartupConfig(int elementCount, int seed, int style){
		this.elementCount = elementCount;
		this.seed = seed;
		this.style = style;
	}
}