import SortLib.SortSolution;

public class BubbleSort{

	public BubbleSort(SortSolution soln){

		String title = "Bubble Sort";
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
		int increasePt = len;
		int newIncPt = increasePt;
		
		for (int i=0; i<len; i++){
			boolean stop = true;
			
			for (int j=0; j<len-i-1; j++){

				if (!soln.compare_lte(j,j+1,"iPt "+increasePt)){
					soln.swap(j,j+1,"iPt "+increasePt);
					stop = false;
					newIncPt = j;
				}
				else if (j>=increasePt) {
					break;
				}
			}
			increasePt = newIncPt;
			if (stop)
				break;
		}
	}
}