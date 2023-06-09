import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * @author Phoenix Boisier
 * 
 * Change-log:
 * 	10/16/17
 * 	-pulled xml parsing out of player and into assignment 4
 * 	-deleted extraneous variables caused by move
 * 
 * 	10/15/17
 * 	-after realizing I needed to put a play() method in here, I did a big copy paste
 * 	-also allowed for different inputs to be made for xml file
 * 
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
	private ArrayList<Room> world;
	
	public PC(String N, String D) {
		super(N, D);
	}
	
	/**
	 * This method is used to feed the player the world info used for cheats
	 * @param world the world the player is in
	 */
	public void feedWorld(ArrayList<Room> world) {
		this.world = world;
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
	
	private static boolean criticalError() {
		System.out.println("Critical error; player not in room. Exiting now.");
		return false;
	}
	private static boolean unknownError() {
		System.out.println("Critical error; unknown error. Exiting game.");
		return false;
	}
	
	/**
	 * Plays the game.
	 * @param a scanner
	 */
	public void play(Scanner s) {
		//sets up all the info we'll need to play
		boolean playing = true;
		String inputCommand;
		String inputString;
		ArrayList<String> inputs = new ArrayList<>();
		Scanner scone = s;
		String commandList = 
				"HELP: Prints command list.\n"+
				"LOOK: Pritns the room's information.\n"+
				"CLEAN: Cleans the room.\n"+
				"DIRTY: Dirties the room.\n"+
				"NORTH: Moves player to the north room if it exists.\n"+
				"SOUTH: Moves player to the south room if it exists.\n"+
				"EAST: Moves player to the east room if it exists.\n"+
				"WEST: Moves player to the west room if it exists.\n"+
				"CLEAN 'NAME': 'NAME' is the name of an animal in the room.\n"
				+ "This command makes that animal clean the room.\n"+
				"DIRTY 'NAME': 'NAME' is the name of an NPC in the room.\n"
				+ "This command makes that NPC dirty the room.\n"+
				"MOVE 'NAME': Name is the name of a creature in the room.\n"
				+ "This command moves the creature to a nearby random room."+
				"MOVE 'NAME' 'ROOM': Name is the name of a creature in the room."
				+ "ROOM is the name of a nearby room.\n"
				+ "This command moves the specified creature to the specified room.\n"+
				"QUIT: Quits the game.\n"+
				"EXIT: Exits the game.";
		String cheatList = 
				"CHEAT1: adds 10 to street cred.\n"+
				"CHEAT2 'NAME': moves player to room name.\n"+
				"CHEAT3 'NAME' 'DESC': adds animal to room of name NAME and description DESC.\n"+
				"CHEAT4 'NAME' 'DESC': adds NPC to room of name NAME and description DESC.\n"+
				"CHEAT5 'NAME': removes target creature NAME from the house.\n"+
				"CHEAT6: sets street cred to 0.\n"+
				"CHEAT7: boosts street cred to 80.\n"+
				"CHEAT8: removes all creatures from room, sets it to dirty, and sets street cred to 1.\n"+
				"CHEAT9: lists all rooms.";
		
		System.out.println("Welcome to Dirty House. Make everyone love you or you lose.\nType HELP for list of commands.");
		/*
		 * -other; prints "Unknown command. Type HELP for command list."
		 * -HELP; prints command list
		 * -LOOK; prints room info
		 * -CLEAN; cleans room
		 * -DIRTY; dirties room
		 * -NORTH; moves player north
		 * -SOUTH; moves player south
		 * -EAST; moves player east
		 * -WEST; moves player west
		 * -QUIT; exits game
		 * -EXIT; exits game
		 * -CLEAN "NAME"; makes an animal clean the room 
		 * -DIRTY "NAME"; makes an NPC clean the room
		 * -MOVE "NAME"; moves specified creature to random nearby room
		 * -MOVE "NAME" "ROOM"; moves specified creature to specified room
		 * 
		 * Following commands are not listed in HELP
		 * -UPDOWNLEFTRIGHT; prints cheat-code list
		 * -CHEAT1; adds 10 to street cred
		 * -CHEAT2 "NAME"; moves player to room name
		 * -CHEAT3 "NAME" "DESC"; adds animal to room
		 * -CHEAT4 "NAME" "DESC"; adds NPC to room
		 * -CHEAT5 "NAME"; removes target creature from house
		 * -CHEAT6; sets street cred to 0
		 * -CHEAT7; sets street cred to 80
		 * -CHEAT8; removes all creatures in room, makes room dirty, sets cred to 1
		 * -CHEAT9; lists all rooms
		 * 
		 */
		while(playing) {
			//this clears the inputs so that we don't get stuck with the first command after the game has gone one round
			inputCommand = "";
			inputString = "";
			inputs = new ArrayList<>();
			
			//once found, if the player's street cred has reached a certain marker {0/80, 80/80} the game ends
			if(StreetCred <= 0) {
				System.out.println("Streed cred is mad whack! It's "+this.StreetCred+". Nobody loves you.\nYou lose...");
				playing = false;
				break;
			}
			else if(StreetCred >= 80) {
				System.out.println("Streed cred is delooxe! It's "+this.StreetCred+". Everybody loves you.\nYou win!");
				System.out.println(this.happy());
				playing = false;
				break;
			}
			
			//otherwise, the game waits for a command
			else {
				System.out.println("What would you like to do?");
				inputCommand = scone.nextLine();
			 	inputCommand = inputCommand.trim();
			 	inputCommand += ' ';
			  	for(int i = 0; i<inputCommand.toCharArray().length; i++) {
					if(inputCommand.toCharArray()[i]==' ') {
						inputs.add(inputString);
						inputString = "";
					}
					else {
						inputString += inputCommand.toCharArray()[i];
					}		
				}
				/* checks to see if the command was multiple inputs or just one */
			  	switch(inputs.size()) {
					/* in the case that the input is one command */
			  		case 1:{
						switch(inputs.get(0).toUpperCase()) {
							case "HELP":{
								System.out.println(commandList);
								break;
							}
							case "LOOK":{
								try {
									System.out.println(this.getRoom().toString());	
									break;
								}
								catch (NullPointerException e) {
									playing = criticalError();
									break;
								}
							}
							case "CLEAN":{
								try {
									this.modifyRoom("CLEAN");	
									break;
								}
								catch (NullPointerException e) {
									playing = criticalError();
									break;
								}
							}
							case "DIRTY":{
								try {
									this.modifyRoom("DIRTY");
									break;
								}
								catch (NullPointerException e) {
									playing = criticalError();
									break;
								}
							}
							case "NORTH":{
								try {
									this.getRoom().getRoomNorth().addCreature(this);
									break;
								}
								catch (NullPointerException e) {
									System.out.println("There is no room to the north.");
									break;
								}
							}
							case "SOUTH":{
								try {
									this.getRoom().getRoomSouth().addCreature(this);
									break;
								}
								catch (NullPointerException e) {
									System.out.println("There is no room to the south.");
									break;
								}
							}
							case "EAST":{
								try {
									this.getRoom().getRoomEast().addCreature(this);
									break;
								}
								catch (NullPointerException e) {
									System.out.println("There is no room to the east.");
									break;
								}
							}
							case "WEST":{
								try {
									this.getRoom().getRoomWest().addCreature(this);
									break;
								}
								catch (NullPointerException e) {
									System.out.println("There is no room to the west.");
									break;
								}
							}
							case "QUIT":{
								System.out.println("You have quit the game, you quitter. Your total street cred was: "+this.StreetCred);
								playing = false;
								break;
							}
							case "EXIT":{
								System.out.println("You have exited the game. Your total street cred was: "+this.StreetCred);
								playing = false;
								break;
							}
//----------------------------------------------------------------------------------------------------------cheat codes here
							case "UPDOWNLEFTRIGHT":{
								System.out.println(cheatList);
								break;
							}
							case "CHEAT1":{
								this.StreetCred += 10;
								System.out.println("Chill! Your street cred went up. It is now "+this.StreetCred+".");
								break;
							}
							case "CHEAT6":{
								this.StreetCred -= 10;
								System.out.println("Stank! Your street cred went down. It is now "+this.StreetCred+".");
								break;
							}
							case "CHEAT7":{
								this.StreetCred = 80;
								break;
							}
							case "CHEAT8":{
								for(int i = 0; i<10; i++) {
									if((this.getRoom().getList()[i]!=null)&&(this.getRoom().getList()[i]!=this)) {
										System.out.println(this.getRoom().getList()[i].getName()+" was brutally murdered.");
										this.getRoom().removeCreature(this.getRoom().getList()[i]);
									}
								}
								this.getRoom().setState(State.DIRTY);
								System.out.println("The room is now dirty.");
								this.StreetCred = 1;
								System.out.println(this.unhappy());
								System.out.println("Your street cred is now "+this.StreetCred+".");
								break;
							}
							case "CHEAT9":{
								System.out.println("All rooms:");
								for(int i = 0; i<world.size(); i++) {
									System.out.println(world.get(i).getName());
								}
								break;
							}
//-----------------------------------------------------------------------------------------------------------------regular commands con't
							default:{
								System.out.println("Unknown command. Type HELP for command list.");
							}
						}
						break;
					}
					/* in the case that there is no input */
			  		case 0:{
						System.out.println("Unknown command. Type HELP for command list.");
						break;
					} 
					/* in the case that inputs is greater than 1 */
			  		default: {	
						switch(inputs.get(0).toUpperCase()) {
							case "CLEAN":{
								try {
									if(this.getRoom().findCreature(inputs.get(1)).type().equals("ANIMAL")) {
										this.getRoom().findCreature(inputs.get(1)).modifyRoom();
									}
									else {
										System.out.println("Specified creature is not capable of cleaning.");
									}
								}
								catch (NullPointerException e) {
									System.out.println("That creature is not in the room.");
								}
								break;
							}
							case "DIRTY":{
								try {
									if(this.getRoom().findCreature(inputs.get(1)).type().equals("NPC")) {
										this.getRoom().findCreature(inputs.get(1)).modifyRoom();
									}
									else {
										System.out.println("Specified creature is not capable of dirtying.");
									}
								}
								catch (NullPointerException e) {
									System.out.println("That creature is not in the room.");
								}
								break;
							}
							case "MOVE":{
								//moving the creature to a random nearby room
								if(inputs.size()==2) {
									try {
										if(this.getRoom().findCreature(inputs.get(1))!=null) {
												this.getRoom().getNeighbors().get((int)(Math.random()*this.getRoom().getNeighbors().size())).addCreature(
														this.getRoom().findCreature(inputs.get(1)));
										}
										else {
											System.out.println("Creature not found. Please try again.");
										}
									}
									catch (NullPointerException e) {
										playing = unknownError();
										break;
									}
								}
								else if (inputs.size()==3) {
									//moving the creature to a specified nearby room
									try {
										//first we find the creature
										if(this.getRoom().findCreature(inputs.get(1))!=null) {
											//then we check to find the specified neighboring room
											for(int i = 0; i<this.getRoom().getNeighbors().size(); i++) {
												//when we find it, the creature gets moved
												if(this.getRoom().getNeighbors().get(i).getName().toUpperCase().equals(inputs.get(2).toUpperCase())) {
													//but only if it would like to be in that room
													if(this.getRoom().findCreature(inputs.get(1)).isPleasedNearby(this.getRoom().getNeighbors().get(i))){
														this.getRoom().getNeighbors().get(i).addCreature(this.getRoom().findCreature(inputs.get(1)));
														break;
													}
													//otherwise the player is notified that the creature did not want to go there
													else {
														System.out.println(inputs.get(1)+" did not want to go to "+inputs.get(2)+".");
														break;
													}
												}
												//otherwise the room is non-existent
												else if (i==this.getRoom().getNeighbors().size()) {
													System.out.println("Room not found. Please try again.");
												}
											}
										}
										//otherwise the creature is non-existent
										else {
											System.out.println("Creature not found. Please try again.");
										}
									}
									//either the player or the player's room was not found
									catch (NullPointerException e) {
										playing = unknownError();
										break;
									}
								}
								else {
									System.out.println("Unknown command. Type HELP for command list.");
								}
								break;
							}
//--------------------------------------------------------------------------------------------------------------begin cheats again
							case "CHEAT2":{
								for(int i = 0; i<world.size(); i++) {
									if(world.get(i).getName().toUpperCase().equals(inputs.get(1).toUpperCase())) {
										System.out.println("Room found.");
										try {
											System.out.println("Teleporting player to room "+inputs.get(1)+".");
											world.get(i).addCreature(this.getRoom().findPlayer());
										}
										catch (NullPointerException e){
											playing = criticalError();
											break;
										}
									}
								}
								break;
							}
							case "CHEAT3":{
								System.out.println("Spawning new animal.");
								if(this.getRoom().isFull()==false) {
									System.out.println("Space found in current room.");
									for(int i = 0; i<world.size(); i++) {
										if(world.get(i).getName().equals(this.getRoom().getName())) {
											world.get(i).addCreature(new Animal(inputs.get(1), inputs.get(2)));
										}
									}
								}
								break;
							}
							case "CHEAT4":{
								System.out.println("Spawning new NPC.");
								if(this.getRoom().isFull()==false) {
									System.out.println("Space found in current room.");
									for(int i = 0; i<world.size(); i++) {
										if(world.get(i).getName().equals(this.getRoom().getName())) {
											world.get(i).addCreature(new NPC(inputs.get(1), inputs.get(2)));
										}
									}
								}
								break;
							}
							case "CHEAT5":{
								System.out.println("Attempting to murder "+inputs.get(1)+" in "+this.getRoom().getName()+".");
									try {
										this.getRoom().removeCreature(this.getRoom().findCreature(inputs.get(1)));
										System.out.println(inputs.get(1)+" was found and brutally murdered.");
									}
									catch (NullPointerException e){
										
									}
								break;
							}
							default: {
								System.out.println("Unknown command. Type HELP for command list.");
							}
						}
					}
				}
			  	//end commands		
			}
			//TODO make animals move from room to room on their own
		}
		System.out.println("Thank you for playing.");
	}
}
