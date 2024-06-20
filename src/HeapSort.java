import SortLib.SortSolution;

public class HeapSort{

	public HeapSort(SortSolution soln) {

		String title = "Heap Sort";
		soln.putTitle(title);
		soln.putDescription("build binary tree of elements in array");

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
		
        // Build heap (rearrange array)
        for (int i = len/2-1; i>= 0; i--)
            heapify(len, i, soln);
 
        // One by one extract an element from heap
        for (int i=len-1; i>=0; i--) {
            // Move current root to end
        	if (i>0) {
        		soln.swap(0, i);
        	}
        	
            // call max heapify on the reduced heap
            heapify(i, 0, soln);
        }
    }
		 
    // To heapify a subtree rooted with node i which is
    // an index in arr. n is size of heap
    void heapify(int n, int i, SortSolution soln)
    {
        int largest = i;  // Initialize largest as root
        int l = 2*i + 1;  // left = 2*i + 1
        int r = 2*i + 2;  // right = 2*i + 2
 
        // If left child is larger than root
        if (l < n && !soln.compare_lte(l,largest)) {
            largest = l;
        }

        // If right child is larger than largest so far
        if (r < n && !soln.compare_lte(r,largest)) {
            largest = r; 
        }

        // If largest is not root
        if (largest != i) {
        	soln.swap(i,largest);
            
            // Recursively heapify the affected sub-tree
            heapify(n, largest, soln);
        }
    }
}