import SortLib.SortSolution;

public class QuickSort{

	SortSolution gSoln = null;
	public QuickSort(SortSolution soln) {

		String title = "Quick Sort";
		soln.putTitle(title);
		soln.putDescription("pick pivot, move > to right, move < to left, divide and repeat");

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
		gSoln = soln;
		qSortRecurse(0,soln.getLength());
	}
	int ccc=0;
	// start is inclusive, end is not inclusive
	void qSortRecurse(int start, int end){
		String note = "range("+start+","+end+")";
//		System.out.println("start "+start+","+end);
//		if (ccc>3)
//			return;
//		ccc++;
		// start is index of first element under consideration
		// start >= 0
		// start < e.length
		// start <= end

		// end is 1 greater than last element under consideration
		// end is >= 0
		// end is <= e.length
		// end >= start
				
		// if end-start is <= 1 then there is nothing to swap and we are done
		if (end-start <= 1) {
			return;
		}
		
		// if end-start == 2 then there are only two elements
		// check and swap if needed, then return
		if (end-start == 2) {

			if (!gSoln.compare_lte(start,end-1,note)) {
				gSoln.swap(end-1,start,note);
			}
			return;
		}

		if (end-start == 3) {

			int one = start;
			int two = one + 1;
			int three = two + 1;
			int pattern = 0;
			
			pattern |= (gSoln.compare_lte(one,two,note))?1:0;
			pattern |= (gSoln.compare_lte(two,three,note))?2:0;
			if (pattern==0x3) {
				pattern |= 4;
			}
			else if (pattern != 0) {
				pattern |= (gSoln.compare_lte(one,three,note))?4:0;
			}

			switch (pattern) {
			case 0x5: // acb
				gSoln.swap(two,three,note+" acb");
				break;
			case 0x2: // cab
				gSoln.swap(one,two,note+" cab");
				gSoln.swap(two,three,note+" cab");
				break;
			case 0x6: //bac
				gSoln.swap(one,two,note+" bac");
				break;
			case 0x1: // bca
				gSoln.swap(one, three,note+" bca");
				gSoln.swap(two, three,note+" bca");
				break;
			case 0x0: // cba
				gSoln.swap(one, three,note+"cba");
				break;
			case 0x7: // abc
				break;
			}
			return;
		}


		int pivot = start + (end - start)/2;
		int g = start;
		int l = end - 1;
		
//		System.out.println("0-pivot: "+pivot+" l: "+l+" g: "+g);

		while(g<l) {
			// slide l down until it points to an element <= pivot
			// may not find anything
			while (l>=start) {
				if ((l==pivot) || gSoln.compare_lte(l,pivot,note)) {
					break;
				}
				l--;
			}
	
			// all elements to right of l are >= pivot
			
			// slide g up until it points to an element > pivot
			// may not find anything
			while (g<end) {
				if ((g==pivot) || !gSoln.compare_lte(g,pivot,note)) {
					break;
				}
				g++;
			}
			
			if (g<l) {
				gSoln.swap(g,l,note);
				if (g==pivot) {
					pivot=l;
				}
				else if (l==pivot) {
					pivot=g;
				}
				if (g!=pivot) {
					g++;
				}
				if (l!=pivot) {
					l--;
				}
			}
		}

		if (g-start > 1) {
			qSortRecurse(start,g);
		}
		if (end-g > 1) {
			qSortRecurse(g,end);
		}
	}
}
