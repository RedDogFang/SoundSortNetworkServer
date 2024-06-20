import SortLib.SortSolution;

public class SelectionSort{

	public SelectionSort(SortSolution soln){

		String title = "Selection Sort";
		soln.putTitle(title);
		soln.putDescription("Move left to right leaving lowest on left");

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
		
		for (int i=0; i<len-1; i++){
			
			for (int j=i+1; j<len; j++){

				if (!soln.compare_lte(i,j)){
					soln.swap(i,j);
				}
			}
		}
	}
}