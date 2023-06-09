import java.util.Scanner;

public class Assignment1 {

	public static void main(String[] args) throws InterruptedException {

		Animal a = new Animal("Abe");
		Animal b = new Animal("Bob");
		Animal c = new Animal("Cat");
		Room room = new Room("Foyer");
		Room basement = new Room("Basement");
		room.addAnimal(a);
		room.addAnimal(b);
		room.addAnimal(c);
		
		
		System.out.println(a.getName());
		System.out.println(room.getName());
		System.out.println(a.getName()+" is in the "+a.getRoom().getName());

		System.out.println();
		System.out.println(room.toString());
		
		basement.addAnimal(a);
		
		System.out.println(room.toString());
		System.out.println(basement.toString());
		
		
		for(int i=0; i<11; i++) {
			basement.addAnimal(new Animal(""+(i+2)));
		}
		
		
		System.out.println("\n");
		System.out.println(basement.toString());
		
		System.out.println(basement.isFull());
		System.out.println(room.isFull());
		
		
		Room attic = new Room("Attic");
		for(int i = 0; i<10; i++) {
			attic.addAnimal(new Animal("Attic animal "+i));
		}
		System.out.println(attic.isFull()+"\n");

		Scanner scone = new Scanner(System.in);

		
		for(int i = -10; i<0; i++) {
			System.out.println("Which animal would you like to know the name of in the basement?\nYou have "
					+(-1*i)+" choice(s) left.\n");
			int s = 0;
			if(scone.hasNextInt()) {
				s = scone.nextInt()-1;
			}		
			else System.out.println("Not a valid choice. You lose. Good day sir.\n");
			try {
				System.out.println(basement.getList()[s].getName()+" is in that position.\n\n");
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println("Not a valid choice. You lose. Good day sir.\n");
			}
		}
		
		
		System.out.println("These are the contents of Attic before the move; \n");
		System.out.println(attic.toString());
		
		System.out.println("These are the contents of Foyer before the move; \n");
		System.out.println(room.toString());
		
		for(int i = 0; i<10; i++) {
			
			System.out.println("Index: "+i);
			System.out.println(room.toString());
			System.out.println("Is room "+room.getName()+" full? "+room.isFull()+"\n");
			
			if(!room.isFull()) {
				System.out.println("Animal moving to foyer.");
				System.out.println(attic.getList()[i].getName());
				room.addAnimal(attic.getList()[i]);
				System.out.println("Is room full after move? "+room.isFull());
				System.out.println(room.toString());
			}
			
			
			System.out.println(room.toString());
			System.out.println(attic.toString());
		}
		
	}

}
