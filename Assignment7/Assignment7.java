
public class Assignment7 {

	public static void main(String[] args) {
		
		QuickSort sort = new QuickSort();
		int[] nums = new int[10];
		for(int a = 0; a<10; a++) {
			for(int i = 0; i<10; i++) {
				nums[i] = (int)(Math.random()*nums.length*10+1);
			}
			System.out.println("First listing");
			for(int i: nums) {
				System.out.print(i+" ");
			}
			System.out.println();
			System.out.println("Sorting now");
			sort.sort(nums);
			System.out.println("Second listing");
			for(int i: nums) {
				System.out.print(i+" ");
			}
			System.out.println();
			System.out.println();
		}
		
		
	}
	
}
