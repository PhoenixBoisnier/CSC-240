import java.util.Scanner;

/**
 * Tests the linked list.
 * @author Phoenix Boisnier
 *
 */
public class Assignment5 {

	public static void main(String[] args) {

		LinkedList<Integer> nums = new LinkedList<>();
		
		nums.append(1);
		nums.append(3);
		nums.append(6);
		nums.append(8);
		nums.append(12);
		System.out.println(nums.toString()+"\n");
		
		System.out.println("Nums find 4: "+nums.exists(4));
		System.out.println("Nums find 8: "+nums.exists(8));
		System.out.println("Nums find 12: "+nums.exists(12)+"\n");
		
		System.out.println("Nums get out of bounds: "+nums.get(12));
		System.out.println("Nums get index 0(1): "+nums.get(0));
		System.out.println("Nums get index 2(6): "+nums.get(2));
		System.out.println("Nums get index 4(12): "+nums.get(4)+"\n"); 
		

		System.out.println(nums.toString());
		System.out.println("Inserting 13 at 4.");
		nums.insert(13, 4);
		System.out.println(nums.toString());
		System.out.println("Inserting 15 at 4.");
		nums.insert(15, 4);
		System.out.println(nums.toString());
		System.out.println("Inserting 4 at 0.");
		nums.insert(4, 0);
		System.out.println(nums.toString());
		System.out.println("Inserting 25 at 25.");
		nums.insert(25, 25);
		System.out.println(nums.toString()+"\n");
		
		System.out.println("Removing 4.");
		System.out.println("Removed: "+nums.remove(4));
		System.out.println(nums.toString());
		System.out.println("Removing 15.");
		System.out.println("Removed: "+nums.remove(15));
		System.out.println(nums.toString());
		System.out.println("Removing 12.");
		System.out.println("Removed: "+nums.remove(12));
		System.out.println(nums.toString()+"\n");
		System.out.println("Removing 12. (already removed)");
		System.out.println("Removed: "+nums.remove(12));
		System.out.println(nums.toString()+"\n");
		
		nums = new LinkedList<>();
		System.out.println("Empty list removed: "+nums.remove(12));
		
		Room r = new Room("Tester", "A room to test linked lists");
		r.setState(State.HALFDIRTY);
		r.addCreature(new Animal("Cat", "It's a cat."));
		System.out.println(r.toString());
		r.addCreature(new Animal("Dog", "It's a dog."));
		System.out.println(r.toString());
		r.addCreature(new PC("P-Dizzle", "Chill"));
		System.out.println(r.toString());
		r.findPlayer().play(new Scanner(System.in));
	}

}
