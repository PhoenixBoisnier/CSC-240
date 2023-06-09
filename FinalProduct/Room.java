/**
 * 
 * @author Phoenix Boisnier
 * 
 * Change-log:
 * 	11/25/2017
 * 	-updated LinkedList to BST
 * 	-fixed toString() for BST
 * 
 * 	11/5/17
 * 	-updated notifyRoom() so that creatures only leave when all neighboring rooms are full
 * 	-updated notifyRoom() so that everyone hates you when a creature leaves the house
 * 	-updated notifyRoom() so that when a creature leaves its room, it modifies it
 * 
 * 	10/25/16
 * 	-fixed addCreature()
 * 	-added setContents()
 * 
 * 	10/23/17 
 * 	-changed roomContents to linked list and all appropriate methods
 * 
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
//	private Creature[] roomContents = new Creature[10];
	private BST<Creature> roomContents = new BST<>();
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
	 * @param c the creature to be added
	 */
	public void addCreature(Creature c) throws NullPointerException{
		if(roomContents.getSize()<=10) {
			this.roomContents.insert(c);
			try {
				c.getRoom().getList().remove(c);	
			}
			catch (NullPointerException e){
				System.out.println("No previous room found for "+c.getName()+".");
			}
			c.setRoom(this);
			System.out.println(c.getName()+" moved to "+c.getRoom().getName()+".");
		}
		else {
			System.out.println("The "+this.getName()+" is full."+"\n"+c.getName()+" could not move to "+this.getName()+".");
		}
	/*	for(int i = 0; i<10; i++) {
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
		}*/
	}

	//this finds the animal in the room and empties its position in the list; it works in tandem with addCreature
	/**
	 * A method used to remove the creature.
	 * @param c the creature being removed
	 */
	public void removeCreature(Creature c) throws NullPointerException{
		roomContents.remove(c);
		/*
		for(int i = 0; i<10; i++) {
			if(a.getRoom().getList()[i] !=null
					&&a.getRoom().getList()[i].getName() == a.getName()) {
				roomContents[i] = null;
				break;
			}
		}*/
	}
	
	/**
	 * Used when building rooms to feed it the list of creatures inside.
	 * @param input the list of creatures
	 */
	public void setContents(BST<Creature> input) {
		roomContents = input;
	}
	
	/**
	 * Used to determine if the room is at maximum capacity.
	 * @return if full true, else false.
	 */
	public boolean isFull() {
		if(roomContents.maxSize()) {
			return true;
		}
		else return false;
	}
	
	/**
	 * Tries to find a creature in the room matching the name given.
	 * @param name The name of the creature in question.
	 * @return the creature if it is present in the room.
	 */
	public Creature findCreature(String name) {
		for(Creature c: roomContents) {
			if(c.getName().toUpperCase().equals(name.toUpperCase())) {
				return c;
			}
		}
		return null;
		
	/*	for(int i = 0; i<10; i++) {
			if(this.getList()[i]!=null&&this.getList()[i].getName().toUpperCase().equals(name.toUpperCase())) {
				return this.getList()[i];
			}
		}
		System.out.println("No creature found with name: "+name);
		return null;*/
	}
	
	/**
	 * 
	 * Alerts the room when the state is changed. 
	 * Gets called during setState().
	 * This method moves all non-player creatures to another room that the creature would prefer,
	 * or outside of the house if there is no valid target.
	 */
	private void notifyRoom() {
		if(!roomContents.isEmpty()) {
			for(Creature c: roomContents) {
				//in the case that c is not the player and is not pleased
				if(!c.isPlayer()&&!c.isPleased()) {
					System.out.println(c.unhappy());
					this.findPlayer().bustCred();
					//this finds a nearby room for the creature to move to
					ArrayList<Integer> indexes = new ArrayList<>();
					for(int q = 0; q<neighbors.size(); q++) {
						if(!neighbors.get(q).isFull()) {
							indexes.add(q);
						}
					}
					if(indexes.size()==0) {
						//if there are no suitable rooms nearby, the creature will leave the house.
						System.out.println(c.unhappy());
						System.out.println(c.getName()+" is so unhappy, it left the house.");
						for(Creature cre: this.roomContents) {
							//then everyone expresses their disdain for the player
							if(!cre.isPlayer()) {
								System.out.println(cre.unhappy());
								this.findPlayer().bustCred();
							}
						}
					}
					else {
						//otherwise, a random index is picked from the available rooms and is selected (via the stored index value) and the creature is moved there.
						System.out.println(c.getName()+" found a better room to be in.");
						this.getNeighbors().get(indexes.get((int)Math.random()*indexes.size())).addCreature(c);
						this.roomContents.remove(c);
						//the room is then  modified according to the creature's modification procedure if it is not happy with the room's current status
						if(!c.isPleased()) {
							c.modifyRoom();	
						}
					}
				}
				else if(!c.isPlayer()&&c.isPleased()){
					System.out.println(c.happy());
					this.findPlayer().boostCred();
				}	
			}
		}
	}
	
	
	//this section contains basic getters	
	/**
	 * 
	 * @return The array containing the animals in this room.
	 */
	public BST<Creature> getList() {
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
		for(Creature c: roomContents) {
			giveToString = giveToString + c.toString()+"\n";
		}
		return giveToString;
	}
	
	/**
	 * Finds the player in the room, if the player is present.
	 * @return the player or null if the player is not present.
	 */
	public PC findPlayer() {
		for(Creature c: roomContents) {
			if(c.isPlayer()) {
				return (PC) c;
			}
		}
		return null;
	}
	
}
