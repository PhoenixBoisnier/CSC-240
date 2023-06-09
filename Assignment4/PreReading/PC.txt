/**
 * 
 * @author Phoenix Boisier
 * 
 * Change-log:
 * 	10/15/17 
 * 	-changed println in modifyRoom() to be less ambiguous
 * 	-added lowestCred()
 * 
 * 	10/9/2017
 * 	-added type()
 * 
 * 	10/7/17
 * 	-fixed PC due to abstraction of creature
 * 	-updated unhappy() and happy() methods
 * 	-added look()
 * 	-updated doc comments
 * 	
 *
 */
public class PC extends Creature{

	private int StreetCred = 40;
	
	public PC(String N, String D) {
		super(N, D);
	}
	
	/**
	 * 
	 * @return The numerical value between 0 and 80 representing the player's respect.
	 */
	public int getStreetCred() {
		return StreetCred;
	}
	
	/**
	 * Decrements the respect value by 1.
	 */
	public void bustCred() {
		StreetCred--;
		if(StreetCred<0) {
			StreetCred = 0;
		}
		System.out.println(this.getName()+"'s street cred got jacked! It is now "+this.getStreetCred());
	}
	
	/**
	 * Increments the respect value by 1.
	 */
	public void boostCred() {
		StreetCred++;
		if(StreetCred>80) {
			StreetCred = 80;
		}
		System.out.println(this.getName()+"'s street cred went up! It is now "+this.getStreetCred());
	}
	
	/**
	 * 
	 * Alters the room's current state depending on the input from the player.
	 * Player's input is always converted to upper case.
	 */
	public void modifyRoom(String playerInput) {
		String upper = playerInput.toUpperCase();
		if(upper.equals("CLEAN")) {
			System.out.println(this.getName()+" tidied up the "+this.getRoom().getName());
			switch(this.getRoom().getState()) {
				case CLEAN:
					System.out.println("The room is already clean.");
					break;
				case DIRTY:
					this.getRoom().setState(Room.getHalfDirty());
					break;
				case HALFDIRTY:
					this.getRoom().setState(Room.getClean());
					break;
			}
			System.out.println(this.getRoom().getName()+" is now "+this.getRoom().getState());
		}
		if(upper.equals("DIRTY")) {
			System.out.println(this.getName()+" dirtied up the "+this.getRoom().getName());
			switch(this.getRoom().getState()) {
				case CLEAN:
					this.getRoom().setState(Room.getHalfDirty());
					break;
				case DIRTY:
					System.out.println("The room is already dirty.");
					break;
				case HALFDIRTY:
					this.getRoom().setState(Room.getDirty());
					break;
			}
			System.out.println("It is now "+this.getRoom().getState());
		}
	}
	
	/**
	 * Sets player street cred to 1
	 */
	public void lowestCred() {
		this.StreetCred = 1;
	}
	
	
	/**
	 * Moves a creature from one room to the next.
	 * @param c the creature being moved
	 * @param destination the room to be moved to.
	 */
	public void moveOther(Creature c, Room destination) {
		System.out.println("Player "+this.getName()+" moved "+c.getName()+" to the "+destination.getName());
		destination.addCreature(c);
	}
	
	/**
	 * Always returns true.
	 * 
	 */
	public boolean isPlayer() {
		return true;
	}

	/**
	 * Always returns true because the PC never cares about room state.
	 * Ultimately, this never needs to be called.
	 */
	public boolean isPleased() {
		return true;
	}

	/**
	 * Always returns true because the PC never cares about room state.
	 * Ultimately, this never needs to be called.
	 * Should this method be called, the PC will return true so that it never
	 * 	gets removed from the world.
	 */
	public boolean isPleasedNearby(Room r) {
		return true;
	}

	/**
	 * This might be useful. Maybe.
	 */
	public String unhappy() {
		return "After looking upon the state of the room, you realize with a deep sense\n"
				+ "of dread that everything you've done has brough nothing but pain.";
	}

	/**
	 * This might be useful too. Also maybe.
	 */
	public String happy() {
		return "After a lot of effort, blood, sweat, and tears, you can finally relax.\n"
				+ "Looking over the results of your efforts, you can finally know happiness.";
	}
	
	/**
	 * This is how the player gets the room info.
	 * Prints contents of the room using its current room's toString().
	 */
	public void look() {
		System.out.println("You look around the room. This is what you see: ");
		System.out.println(this.getRoom().toString());
	}
	
	/**
	 * @return 'PC'
	 */
	public String type() {
		return "PC";
	}
	
}
