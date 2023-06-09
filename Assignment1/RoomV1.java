
public class Room {

	private String name;
	private String desc;
	private int state = (int) (Math.random()*3);
	private Animal[] roomContents = new Animal[10];
	private String giveToString;
	
	/**
	 * Creates a new room.
	 * @param N the name of the room.
	 */
	public Room(String N) {
		name = N;
	}
	
	//this method works in tandem with removeAnimal to first assign the animal to this room in room, then removes it from its first room
	//	and reassigns the room of the animal to this room
	/**
	 * Adds an animal to the room, and removes the animal from its previous room.
	 * @param a the animal to be added
	 */
	public void addAnimal(Animal a) {
		for(int i = 0; i<10; i++) {
			if(roomContents[i] == null) {
				roomContents[i] = a;
				if(a.getRoom()!=null) {
					a.getRoom().removeAnimal(a);
				}
				a.setRoom(this);
				System.out.println(a.getName()+" moved to "+a.getRoom().getName()+".");
				break;
			}
			if(i==9&&this.getList()[i]!=null) {
				System.out.println("The "+this.getName()+" is full."+"\n"+a.getName()+" could not move to the "+this.getName()+".");
			}
		}
	}

	//this finds the animal in the room and empties its position in the list; it works in tandem with addAnimal
	/**
	 * A private method used to remove the animal.
	 * @param a the animal being removed, the same as the animal "a" in addAnimal(Animal a)
	 */
	private void removeAnimal(Animal a) {
		for(int i = 0; i<10; i++) {
			if(a.getRoom().getList()[i] !=null
					&&a.getRoom().getList()[i].getName() == a.getName()) {
				roomContents[i] = null;
				break;
			}
		}
	}
	
	/**
	 * Used to determine if the room is at maximum capacity.
	 * @return if full true, else false.
	 */
	public boolean isFull() {
		for(int i=0; i<10; i++) {
			if(this.getList()[i]==null) {
				return false;
			}
		}
		return true;
	}
	
	//this section contains basic getters	
	/**
	 * 
	 * @return The array containing the animals in this room.
	 */
	public Animal[] getList() {
		return roomContents;
	}
	
	/**
	 * 
	 * @return The name of the room.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return The description of the room.
	 */
	private String getDesc() {
		return desc;
	}
	
	/**
	 * 
	 * @return either 2, 1, or 0 where 2 is clean, 1 is half dirty and 0 is dirty
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * This method is used to find out if the room
	 * is clean, half-dirty, or clean.
	 */
	private String stateToString() {
		
		if(state == 2) {
			return "clean";
		}
		else if(state == 1){
			return "half-dirty";
		}
		else return "dirty";
	}
	
	/**
	 * This method is used to convert all details about the room
	 * into string format.
	 */
	public String toString() {
		giveToString = "This room is "+this.getName()+"."+"\n"+"The room looks "+this.getDesc()+"."+"\n"+
					"The room is "+this.stateToString()+"."+"\n";
		for(int i = 0; i<10; i++) {
			if(this.getList()[i]!=null) {
				giveToString = giveToString + this.getList()[i].getName()+" is in the room."+"\n";
			}
		}
		return giveToString;
	}
	
}
