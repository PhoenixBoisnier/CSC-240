
public class Assignment2 {

	public static void main(String[] args) {
		
		Room foyer = new Room("Foyer");
		Room basement = new Room("Basement");
		Room attic = new Room("Attic");
		
		foyer.setState(Room.getClean());
		basement.setState(Room.getDirty());
		attic.setState(Room.getHalfDirty());
		
		System.out.println(foyer.getState());
		System.out.println(basement.getState());
		System.out.println(attic.getState());
		
		for(int i = 0; i<5; i++) {
			foyer.addCreature(new Animal("FoyerAnimal"+(i+1)));
		}
		for(int i = 0; i<5; i++) {
			basement.addCreature(new NPC("BasementNPC"+(i+1)));
		}
		attic.addCreature(new PC("Phoenix"));

		System.out.println(foyer.getState());
		System.out.println(basement.getState());
		System.out.println(attic.getState());
		
		for(int i = 0; i<10; i++) {
			if(foyer.getList()[i]!=null) {
				System.out.println(foyer.getList()[i].getName()+" is pleased: "+
						foyer.getList()[i].isPleased());
			}
		}
		
		for(int i = 0; i<10; i++) {
			if(basement.getList()[i]!=null) {
				System.out.println(basement.getList()[i].getName()+" is pleased: "+
						basement.getList()[i].isPleased());
			}
		}
		
		System.out.println("Player in the attic "+attic.getList()[0].getName()+" is pleased: "+
				attic.getList()[0].isPleased());

		System.out.println(foyer.getState());
		System.out.println(basement.getState());
		System.out.println(attic.getState());
		
		for(int i = 0; i<5; i++) {
			foyer.addCreature(basement.getList()[i]);
		}

		System.out.println(foyer.getState());
		System.out.println(basement.getState());
		System.out.println(attic.getState());
		
		for(int i = 0; i<10; i++) {
			if(foyer.getList()[i]!=null) {
				System.out.println(foyer.getList()[i].getName()+" is pleased: "+
						foyer.getList()[i].isPleased());
			}
		}
		
		System.out.println("The attic is "+attic.getState());
		attic.getList()[0].modifyRoom("CLEAN");
		attic.getList()[0].modifyRoom("DIRTY");
		attic.getList()[0].modifyRoom("DIRTY");
		attic.getList()[0].modifyRoom("CLEAN");
		attic.getList()[0].modifyRoom("CLEAN");
		attic.getList()[0].modifyRoom("CLEAN");
		attic.getList()[0].modifyRoom("DIRTY");
		attic.getList()[0].modifyRoom("DIRTY");
		attic.getList()[0].modifyRoom("DIRTY");
		attic.getList()[0].modifyRoom("DIRTY");
		
		System.out.println(attic.findPlayer().getStreetCred());
		for(int i = 0; i<39; i++) {
			attic.findPlayer().bustCred();	
		}
		System.out.println(attic.findPlayer().getName()+" is mad whack...");
		System.out.println(attic.findPlayer().getStreetCred()+" street cred...");
		for(int i = 0; i<100; i++) {
			attic.findPlayer().boostCred();
		}
		System.out.println(attic.findPlayer().getName()+" is supah fly!");
		System.out.println(attic.findPlayer().getStreetCred()+" street cred!");
		
		foyer.addCreature(attic.findPlayer());
		
		basement.addCreature(foyer.getList()[0]);
		foyer.addCreature(attic.findPlayer());
		
		foyer.getList()[1].modifyRoom();
		foyer.getList()[6].modifyRoom();
		foyer.getList()[6].modifyRoom();
		
		try {
			foyer.findPlayer().moveOther(foyer.findCreature("BasementNPC3"), basement);
			foyer.findPlayer().moveOther(foyer.findCreature("FoyerAnimal1"), basement);
		}
		catch(NullPointerException e) {
			
		}
		
		Room hallway = new Room("Hallway");
		Room sunroom = new Room("Sunroom");
		foyer.setRoomEast(hallway);
		hallway.setRoomWest(foyer);
		hallway.setRoomEast(sunroom);
		sunroom.setRoomWest(hallway);
		sunroom.setRoomSouth(basement);
		basement.setRoomNorth(sunroom);
		
		for(int i = 0; i<10; i++) {
			foyer.addCreature(new Animal("FoyerAnimalNew"+(i+1)));
		}
		
		System.out.println("\n\nStreet cred test: "+"\n\n");
		hallway.setState(Room.getHalfDirty());
		System.out.println(foyer.toString());
		foyer.findPlayer().modifyRoom("CLEAN");
		System.out.println();
		foyer.findPlayer().modifyRoom("CLEAN");
		
		for(int i = 1; i<10; i++) {
			if(foyer.getList()[i]!=null) {
				foyer.findPlayer().moveOther(foyer.getList()[i], foyer.getRoomEast());	
			}
			else {
				foyer.getRoomEast().addCreature(new Animal(foyer.getRoomEast().getName()+"Animal"+i));
			}
		}
		foyer.getRoomEast().addCreature(foyer.findPlayer());
		hallway.findPlayer().modifyRoom("CLEAN");
		if(hallway.findPlayer().getStreetCred()>=80) {
			System.out.println("Aww Sheet! That's sheets delooxe! Toe up from the mutha' fukin' flo up!");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println("Someone call an exterminator, this house is infested with animals...");	
		}
		System.out.println("Someone call an exterminator, this house is infested with animals...");
	}

}
