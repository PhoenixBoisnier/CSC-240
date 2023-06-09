/**
 * 
 * @author Phoenix Boisier
 * 
 * Change-log:
 * 	10/15/17
 * 	-changed findCreature() to use toUpperCase()
 * 	-fixed notifyRoom() so that happy things happen when a creature is made happy
 * 	-fixed notifyRoom() so that unhappy things happen when a creature is made unhappy
 * 	-filled neighbors ArrayList so that notifyRoom() will work as intended
 * 
 * 	10/9/17
 * 	-getNeighbors() is now public
 * 	-removeCreature() is now public
 * 
 * 	10/7/17
 * 	-updated addCreature() from Creature's addition of throws NullPointerExceptio to getRoom() method
 * 	-updated notifyRoom() due to modification of unhappy() and happy() methods in animals, NPCs and PC as well as boostCred() and bustCred() in PC
 * 	-added throws NullPointerException to getRoomNorth(), getRoomSouth(), getRoomEast(), getRoomWest()
 * 	-updated doc comments
 * 	
 *
 */
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
	private State cleanliness;
	
	/**
	 * Creates a new room.
	 * @param N the name of the room.
	 */
	public Room(String N, String D) {
		name = N;
		desc = D;
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
				try {
					a.getRoom().removeCreature(a);
				}
				catch(NullPointerException e) {
					System.out.println("No previous room found for "+a.getName());
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
	 * A method used to remove the creature.
	 * @param a the creature being removed, the same as the creature "a" in addCreature(Creature a)
	 */
	public void removeCreature(Creature a) {
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
			if(this.getList()[i]!=null&&this.getList()[i].getName().toUpperCase().equals(name.toUpperCase())) {
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
						System.out.println(this.getList()[i].unhappy());
						this.findPlayer().bustCred();
					}
					//finds a suitable nearby room to move to and compiles the index of the room in a temporary ArrayList
					ArrayList<Integer> indexes = new ArrayList<>();
					for(int q = 0; q<neighbors.size(); q++) {
						if(this.getList()[i].isPleasedNearby(neighbors.get(q))) {
							indexes.add(q);
						}
					}
					if(indexes.size()==0) {
						//if there are no suitable rooms nearby, the creature will leave the house.
						System.out.println(this.getList()[i].unhappy());
						System.out.println(this.getList()[i].getName()+" is so unhappy, it left the house.");
						this.findPlayer().bustCred();
						this.removeCreature(this.getList()[i]);
					}
					else {
						//otherwise, a random index is picked from the available rooms and is selected (via the stored index value) and the creature is moved there.
						System.out.println(this.getList()[i].getName()+" found a better room to be in.");
						this.getNeighbors().get(indexes.get((int)Math.random()*indexes.size())).addCreature(this.getList()[i]);
					}
					
				}
				//in the case that there is a happy non-player creature.
				if(this.getList()[i]!=null&&!this.getList()[i].isPlayer()&&this.getList()[i].isPleased()) {
					//checking to see if the space is null is necessary again as an animal may have left the room in this spot by now.
					if(this.findPlayer()!=null) {
						System.out.println(this.getList()[i].happy());
						this.findPlayer().boostCred();
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
		neighbors.add(rN);
	}
	
	/**
	 * 
	 * @param r will be the room to the south.
	 */
	public void setRoomSouth(Room r) {
		rS = r;
		neighbors.add(rS);
	}
	
	/**
	 * 
	 * @param r will be the room to the east.
	 */
	public void setRoomEast(Room r) {
		rE = r;
		neighbors.add(rE);
	}
	
	/**
	 * 
	 * @param r will be the room to the west.
	 */
	public void setRoomWest(Room r) {
		rW = r;
		neighbors.add(rW);
	}
	
	/**
	 * 
	 * @return the room to the north.
	 */
	public Room getRoomNorth() throws NullPointerException{
		return rN;
	}

	/**
	 * 
	 * @return the room to the south.
	 */
	public Room getRoomSouth() throws NullPointerException{
		return rS;
	}

	/**
	 * 
	 * @return the room to the east.
	 */
	public Room getRoomEast() throws NullPointerException{
		return rE;
	}

	/**
	 * 
	 * @return the room to the west.
	 */
	public Room getRoomWest() throws NullPointerException{
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
	public ArrayList<Room> getNeighbors(){
		return neighbors;
	}

	
	/**
	 * This method is used to convert all details about the room
	 * into string format.
	 */
	public String toString() {
		giveToString = "This room is "+this.getName()+"."+"\n"+this.getDesc()+"."+"\n"+"The room is "+this.getState()+"."+"\n";
		giveToString = giveToString + "The neighboring rooms are: \n";
		if(rN!= null) {
			giveToString = giveToString + this.getRoomNorth().getName() + " to the north.\n";
		}
		if(rS!= null) {
			giveToString = giveToString + this.getRoomSouth().getName() + " to the south.\n";
		}
		if(rE!= null) {
			giveToString = giveToString + this.getRoomEast().getName() + " to the east.\n";
		}
		if(rW!= null) {
			giveToString = giveToString + this.getRoomWest().getName() + " to the west.\n";
		}
		giveToString = giveToString + "In the room are: \n";
		for(int i = 0; i<10; i++) {
			if(this.getList()[i]!=null) {
				giveToString = giveToString + this.getList()[i].toString() + "\n";
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
