
public class NPC extends Creature{

	
	/**
	 * Creates a new NPC.
	 * @param N is the name.
	 */
	public NPC(String N) {
		super(N);
	}
	
	/**
	 * Determines if the NPC is pleased with its room's state.
	 * @return 
	 */
	public boolean isPleased() {
		switch(this.getRoom().getState()) {
			case CLEAN:
				return false;
			case DIRTY:
				return true;
			case HALFDIRTY:
				return true;
		}
		return false;
	}
	
	/**
	 * Used to check if the NPC would be pleased in another room.
	 * @param room the nearby room
	 * @return true if DIRTY or HALFDIRTY
	 */
	public boolean isPleasedNearby(Room room) {
		switch(room.getState()) {
			case CLEAN:
				return false;
			case DIRTY:
				return true;
			case HALFDIRTY:
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * Bumps down the current state of the room one step dirtier.
	 */
	public void modifyRoom() {
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
	
	/**
	 * 
	 * 
	 */
	public String unhappy() {
		return this.getName()+" makes an angry people noise.";
	}
	
	/**
	 * 
	 * 
	 */
	public String happy() {
		return this.getName()+" makes some sort of happy people noise.";
	}
}
