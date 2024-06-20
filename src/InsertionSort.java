import SortLib.SortSolution;

public class InsertionSort {

	public InsertionSort(SortSolution soln) {

		String title = "Insertion Sort";
		soln.putTitle(title);
		soln.putDescription("Move left to right inserting each new element in place");

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
		
		for (int i=1; i<len; i++){
			int tmp = i;
			for (int j=tmp-1; j>=0; j--){
				if (!soln.compare_lte(j,tmp)) {
					soln.swap(j,tmp);
					tmp = j;
				}
				else {
					break;
				}
			}
		}
	}
}