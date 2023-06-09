
public class Animal {
	
	private String name;
	private Room r;
	
	/**
	 * Creates a new animal.
	 * @param N the name of the animal.
	 */
	public Animal (String N) {
		name = N;
	}

	/**
	 * 
	 * @return The animal's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return The room the animal is in.
	 */
	public Room getRoom() {
		return r;
	}
	
	/**
	 * Sets the animal's room to a new room.
	 * @param newR the room the animal is now in
	 */
	public void setRoom(Room newR) {
		r = newR;
	}
	
	//determines if the animal is pleased where 2 is clean, 1 is half dirty and 0 is dirty
	/**
	 * Determines if the animal is pleased with its room's state expressed as a numerical value.
	 * @return true if 1 or 2, else false
	 */
	public boolean isPleased() {
		if(this.r.getState()>=1) {
			return true;
		}
		else return false;
	}

}
