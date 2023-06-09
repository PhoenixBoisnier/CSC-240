
public class QuickSort {
	
	private int partition(int arr[], int first, int last) {
		int pivot = arr[first];
		int up = first;
		int down = last;
		//while the pointers do not overlap
		while(up<down) {
			//while the value at arr[up] is less than or equal to the pivot
			while(arr[up]!=pivot && arr[up]<=pivot) {
				//move up the array to find something greater than the pivot
				up++;
			}
			//while the value at arr[down] is greater than the pivot
			while(arr[down]!=pivot && arr[down]>pivot) {
				//move down the array to find something less than the pivot
				down--;
			}
			//if the value at arr[up] is less than the value at arr[down] swap them
			if(arr[up]>arr[down]) {
				int temp = arr[up];
				arr[up] = arr[down];
				arr[down] = temp;
			}
			//if the value at arr[up] and arr[down] are equal and up!=down
			if(up!=down && arr[up]==arr[down]) {
				//shift down by one
				down--;
			}
		}
		return down;
	}
	
	private void quickSort(int arr[], int first, int last) {
		if(first<last) {
			int pivot = partition(arr, first, last);
			quickSort(arr, first, pivot-1);
			quickSort(arr, pivot+1, last);
			
		}
	}
	
	public void sort(int arr[]) {
		quickSort(arr, 0, arr.length-1);
	}
	
	/**
	 * Sort middle sort calls sort on an array smaller than the input. It does this by walking
	 * down the array and begins to sort where it finds something out of order. It then walks
	 * from the end of the array to see where to end the sort at. Only to be used when you know
	 * the first element is smallest and last element is largest.
	 * @param arr the array to be sorted.
	 */
	public void sortMiddleSort(int arr[]) {
		int begin = 1;
		int value = arr[0];
		while(begin<arr.length-1 && value<arr[begin]) {
			value = arr[begin];
			begin++;
		}
		int end = arr.length-1;
		value = arr[arr.length-1];
		while(end>0 && value>=arr[end]) {
			value = arr[end];
			end--;
		}
		if(begin==arr.length-1 || end==0) {
			//The list is in order in both of these cases 
		}
		else quickSort(arr, begin, end);
	}
	
}
