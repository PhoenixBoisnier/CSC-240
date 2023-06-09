import java.util.ArrayList;

public class Room {

	private String name;
	private Creature[] roomContents = new Creature[10];
	private String giveToString;
	
	private String desc;
	private Room rN;
	private Room rS;
	private Room rE;
	private Room rW;
	private ArrayList<Room> neighbors = new ArrayList<Room>();
	public enum State{CLEAN, DIRTY, HALFDIRTY}
	private State cleanliness;
	
	/**
	 * Creates a new room.
	 * @param N the name of the room.
	 */
	public Room(String N) {
		name = N;
		cleanliness = State.HALFDIRTY;
	}
	
	//this method works in tandem with removeCreature to first assign the animal to this room in room, then removes it from its first room
	//	and reassigns the room of the animal to this room
	/**
	 * Adds a creature to the room, and removes the creature from its previous room.
	 * @param a the creature to be added
	 */
	public void addCreature(Creature a) {
		for(int i = 0; i<10; i++) {
			if(roomContents[i] == null) {
				roomContents[i] = a;
				if(a.getRoom()!=null) {
					a.getRoom().removeCreature(a);
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

	//this finds the animal in the room and empties its position in the list; it works in tandem with addCreature
	/**
	 * A private method used to remove the animal.
	 * @param a the animal being removed, the same as the animal "a" in addAnimal(Animal a)
	 */
	private void removeCreature(Creature a) {
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
	
	/**
	 * Tries to find a creature in the room matching the name given.
	 * @param name The name of the creature in question.
	 * @return the creature if it is present in the room.
	 */
	public Creature findCreature(String name) {
		for(int i = 0; i<10; i++) {
			if(this.getList()[i]!=null&&this.getList()[i].getName().equals(name)) {
				return this.getList()[i];
			}
		}
		System.out.println("No creature found with name: "+name);
		return null;
	}
	
	/**
	 * 
	 * Alerts the room when the state is changed. 
	 * Gets called during setState().
	 * This method moves all non-player creatures to another room that the creature would prefer,
	 * or outside of the house if there is no valid target.
	 */
	private void notifyRoom() {
		for(int i = 0; i<10; i++) {
			if(this.getList()[i]!=null) {
				//in the case that there is an unhappy non-player creature.
				if(!this.getList()[i].isPlayer()&&!(this.getList()[i].isPleased())) {
					if(this.findPlayer()!=null) {
						System.out.println(this.getList()[i].getName()+" is unhappy with the change.");
						this.getList()[i].unhappy();
						this.findPlayer().bustCred();
						System.out.println(this.findPlayer().getName()+"'s street cred got jacked! It is now "+this.findPlayer().getStreetCred());
					}
					//finds a suitable nearby room to move to and compiles the index of the room in a temporary ArrayList
					ArrayList<Integer> indexes = new ArrayList<>();
					for(int q = 0; q<neighbors.size(); q++) {
						if(!this.getList()[i].isPleaasedNearby(neighbors.get(q))) {
							indexes.add(q);
						}
					}
					if(indexes.size()==0) {
						//if there are no suitable rooms nearby, the creature will leave the house.
						System.out.println(this.getList()[i].getName()+" is so unhappy, it left the house.");
						System.out.println(this.getList()[i].unhappy());
						this.findPlayer().bustCred();
						System.out.println(this.findPlayer().getName()+"'s street cred got jacked! It is now "+this.findPlayer().getStreetCred());
						this.removeCreature(this.getList()[i]);
					}
					else {
						//otherwise, a random index is picked from the available rooms and is selected (via the stored index value) and the creature is moved there.
						this.getNeighbors().get(indexes.get((int)Math.random()*indexes.size())).addCreature(this.getList()[i]);
					}
					
				}
				//in the case that there is a happy non-player creature.
				if(this.getList()[i]!=null&&!this.getList()[i].isPlayer()&&this.getList()[i].isPleased()) {
					//checking to see if the space is null is necessary again as an animal may have left the room in this spot by now.
					if(this.findPlayer()!=null) {
						System.out.println(this.getList()[i].getName()+" is happy with the change.");
						this.getList()[i].happy();
						this.findPlayer().boostCred();
						System.out.println(this.findPlayer().getName()+"'s street cred went up! It is now "+this.findPlayer().getStreetCred());
					}
				}
			}
		}
	}
	
	
	//this section contains basic getters	
	/**
	 * 
	 * @return The array containing the animals in this room.
	 */
	public Creature[] getList() {
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
	 * @param r will be the room to the north.
	 */
	public void setRoomNorth(Room r) {
		rN = r;
	}
	
	/**
	 * 
	 * @param r will be the room to the south.
	 */
	public void setRoomSouth(Room r) {
		rS = r;
	}
	
	/**
	 * 
	 * @param r will be the room to the east.
	 */
	public void setRoomEast(Room r) {
		rE = r;
	}
	
	/**
	 * 
	 * @param r will be the room to the west.
	 */
	public void setRoomWest(Room r) {
		rW = r;
	}
	
	/**
	 * 
	 * @return the room to the north.
	 */
	public Room getRoomNorth() {
		return rN;
	}

	/**
	 * 
	 * @return the room to the south.
	 */
	public Room getRoomSouth() {
		return rS;
	}

	/**
	 * 
	 * @return the room to the east.
	 */
	public Room getRoomEast() {
		return rE;
	}

	/**
	 * 
	 * @return the room to the west.
	 */
	public Room getRoomWest() {
		return rW;
	}
	
	/**
	 * 
	 * Sets state to given state.
	 */
	public void setState(State s) {
		cleanliness = s;
		this.notifyRoom();
	}
	
	/**
	 * 
	 * @return State in the form of CLEAN.
	 */
	public static State getClean() {
		return State.CLEAN;
	}
	
	/**
	 * 
	 * @return State in the form of DIRTY.
	 */
	public static State getDirty() {
		return State.DIRTY;
	}
	
	/**
	 * 
	 * @return State in the form of HALFDIRTY.
	 */
	public static State getHalfDirty() {
		return State.HALFDIRTY;
	}
	
	/**
	 * 
	 * @return Defined by enum in Room; either CLEAN, DIRTY, or HALFDIRTY.
	 */
	public State getState() {
		return cleanliness;
	}
	
	/**
	 * 
	 * @return an ArrayList<Room> of rooms directly next to this one.
	 */
	private ArrayList<Room> getNeighbors(){
		return neighbors;
	}

	
	/**
	 * This method is used to convert all details about the room
	 * into string format.
	 */
	public String toString() {
		giveToString = "This room is "+this.getName()+"."+"\n"+"The room looks "+this.getDesc()+"."+"\n"+
					"The room is "+this.getState()+"."+"\n";
		for(int i = 0; i<10; i++) {
			if(this.getList()[i]!=null) {
				giveToString = giveToString + this.getList()[i].getName()+" is in the room."+"\n";
				giveToString = giveToString + this.getList()[i].getDesc()+"\n";
			}
		}
		return giveToString;
	}
	
	/**
	 * Finds the player in the room, if the player is present.
	 * @return the player or null if the player is not present.
	 */
	public PC findPlayer() {
		for(int i = 0; i<10; i++) {
			if(this.getList()[i]!=null&&this.getList()[i].isPlayer()) {
				return (PC) this.getList()[i];
			}
		}
		return null;
	}
	
}
