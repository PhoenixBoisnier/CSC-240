public class Animal extends Creature{
	
	
	/**
	 * Creates a new animal.
	 * @param N the name of the animal.
	 */
	public Animal (String N) {
		super(N);
	}
	
	/**
	 * Determines if the animal is pleased with its room's state.
	 * @return 
	 */
	public boolean isPleased() {
		switch(this.getRoom().getState()) {
			case CLEAN:
				return true;
			case DIRTY:
				return false;
			case HALFDIRTY:
				return true;
		}
		return false;
	}
	
	/**
	 * Used to check if the animal would be pleased in another room.
	 * @param room the nearby room
	 * @return true if CLEAN or HALFDIRTY
	 */
	public boolean isPleasedNearby(Room room) {
		switch(room.getState()) {
			case CLEAN:
				return true;
			case DIRTY:
				return false;
			case HALFDIRTY:
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * Bumps up the current state of the room one step cleaner.
	 */
	public void modifyRoom() {
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
		System.out.println("It is now "+this.getRoom().getState());
	}
	
	/**
	 * 
	 * 
	 */
	public String unhappy() {
		return this.getName()+" makes an an angry sounding animal noise.";
	}
	
	/**
	 * 
	 * 
	 */
	public String happy() {
		return this.getName()+" makes some sort of happy animal noise.";
	}

}
