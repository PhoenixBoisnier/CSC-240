
public class PC extends Creature{

	private int StreetCred = 40;
	
	public PC(String N) {
		super(N);
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
	}
	
	/**
	 * Increments the respect value by 1.
	 */
	public void boostCred() {
		StreetCred++;
		if(StreetCred>80) {
			StreetCred = 80;
		}
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
			System.out.println("It is now "+this.getRoom().getState());
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
	 * Moves a creature from one room to the next.
	 * @param c the creature being moved
	 * @param destination the room to be moved to.
	 */
	public void moveOther(Creature c, Room destination) {
		System.out.println("Player "+this.getName()+" moved "+c.getName()+" to the "+destination.getName());
		destination.addCreature(c);
	}
	
	/**
	 * 
	 * 
	 */
	public boolean isPlayer() {
		return true;
	}
	
}
