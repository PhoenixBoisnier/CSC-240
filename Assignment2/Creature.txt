
public class Creature {

	private String name;
	private String desc;
	private Room r = new Room("HoldingCell");
	
	/**
	 * Creates a new animal.
	 * @param N the name of the animal.
	 */
	public Creature (String N) {
		name = N;
	}

	/**
	 * 
	 * @return The creature's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return The description of the creature.
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * 
	 * 
	 */
	public void setDesc(String description) {
		desc = description;
	}
	
	/**
	 * 
	 * @return The room the creature is in.
	 */
	public Room getRoom() {
		return r;
	}
	
	/**
	 * Sets the creature's room to a new room.
	 * @param newR the room the creature is now in
	 */
	public void setRoom(Room newR) {
		r = newR;
	}
	
	/**
	 * Animals like CLEAN and HALFDIRTY rooms and will return true for either.
	 * NPCs like DIRTY and HALFDIRTY rooms and will return true for either.
	 * The player does not care and therefore does not overwrite this method.
	 * @return true or false, parent class returns true
	 */
	public boolean isPleased() {
		return true;
	}
	
	/**
	 * 
	 * 
	 */
	public boolean isPleaasedNearby(Room r) {
		return false;
	}
	
	/**
	 * 
	 * 
	 */
	public void modifyRoom() {
		System.out.println("Nothing happens.");
	}
	
	/**
	 * 
	 * 
	 */
	public void modifyRoom(String s) {
		System.out.println("Nothing happens.");	
	}
	
	/**
	 * 
	 * 
	 */
	public boolean isPlayer() {
		return false;
	}
	
	/**
	 * 
	 * 
	 */
	public String unhappy() {
		return "No distinguishable sound is made, but it seems unhappy.";
	}
	
	/**
	 * 
	 * 
	 */
	public String happy() {
		return "No distinguishable sound is made, but it seems happy.";
	}
	
}
