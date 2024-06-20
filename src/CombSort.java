import SortLib.SortSolution;

public class CombSort{
	
	public CombSort(SortSolution soln){

		String title = "Comb Sort";
		soln.putTitle(title);
		soln.putDescription("Bubble with variable distance between compares");

		sort(soln);

		System.out.println(title);
		if (soln.verify()) {
			System.out.println("Success!!");
		} else {
			System.out.println("Failure!!");
		}
		System.out.println("compares: " + soln.getCompares());
		System.out.println("swaps: " + soln.getSwaps());
		System.out.println("total: " + (soln.getCompares() + soln.getSwaps())+"\n");

	}
	
	private void sort(SortSolution soln) {
		int len = soln.getLength();
		String note = "";
		boolean done;
		int distance = len;
		
		for (int i=0; i<len; i++){
			distance--;
			distance = Math.max(1, distance);
			note = ""+distance;
			done = distance==1?true:false;
			
			for (int j=0; j<len-distance; j++){
				if (!soln.compare_lte(j,j+distance,note)){
					soln.swap(j,j+distance,note);
					done = false;
				}
			}
			if (done)
				break;
		}
	}
}