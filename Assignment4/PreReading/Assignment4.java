/**
 * 
 * @author Phoenix Boisier
 * 
 * Change Log:
 * 	10/15/17
 * 	-made error methods private
 * 	-fixed input problems for multiple item commands
 * 	-all commands are working as expected, including cheat codes
 * 
 * 	10/9/17
 *	-commented out multiple part inputs, marked with TODO
 *
 */
/*
 * TODO fix MOVE command so that creatures do not move to unwanted room
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class Assignment4 {
	
	private static boolean criticalError() {
		System.out.println("Critical error; player not in room. Exiting now.");
		return false;
	}
	private static boolean unknownError() {
		System.out.println("Critical error; unknown error. Exiting game.");
		return false;
	}

	public static void main(String[] args) {
		
		ArrayList<Room> world = new ArrayList<>();
		boolean playing;
		int streetCred;
		String inputCommand;
		String inputString;
		ArrayList<String> inputs;
		Scanner scone = new Scanner(System.in);
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
				"CHEAT5: removes target creature from the house.\n"+
				"CHEAT6: sets street cred to 0.\n"+
				"CHEAT7: boosts street cred to 80.\n"+
				"CHEAT8: removes all creatures from room, sets it to dirty, and sets street cred to 1.\n"+
				"CHEAT9: lists all rooms.";
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			InputStream xmlInput  = new FileInputStream("input.xml");
			SAXParser saxParser = spf.newSAXParser();
			ProjectXMLParser sxp = new ProjectXMLParser();
			saxParser.parse(xmlInput, sxp);
			
			world = sxp.getRooms();
		}	
		catch(SAXException|ParserConfigurationException|IOException e){
			e.printStackTrace();
		}

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
		playing = true;
		inputs = new ArrayList<>();
		streetCred = 40;
		Room playerRoom = null;
		while(playing) {
			//this clears the inputs so that we don't get stuck with the first command after the game has gone one round
			inputCommand = "";
			inputString = "";
			inputs = new ArrayList<>(); 
			
			//this finds the player and finds the current street cred and room.
			for(int i = 0; i<world.size(); i++) {
				if(world.get(i).findPlayer()!=null) {
					streetCred = world.get(i).findPlayer().getStreetCred();
					playerRoom = world.get(i).findPlayer().getRoom();
				}
			}
			
			//once found, if the player's street cred has reached a certain marker {0/80, 80/80} the game ends
			if(streetCred <= 0) {
				System.out.println("Streed cred is mad whack! It's "+streetCred+". Nobody loves you.\nYou lose...");
				playing = false;
				break;
			}
			else if(streetCred >= 80) {
				System.out.println("Streed cred is delooxe! It's "+streetCred+". Everybody loves you.\nYou win!");
				System.out.println(playerRoom.findPlayer().happy());
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
									System.out.println(playerRoom.toString());	
									break;
								}
								catch (NullPointerException e) {
									playing = criticalError();
									break;
								}
							}
							case "CLEAN":{
								try {
									playerRoom.findPlayer().modifyRoom("CLEAN");	
									break;
								}
								catch (NullPointerException e) {
									playing = criticalError();
									break;
								}
							}
							case "DIRTY":{
								try {
									playerRoom.findPlayer().modifyRoom("DIRTY");
									break;
								}
								catch (NullPointerException e) {
									playing = criticalError();
									break;
								}
							}
							case "NORTH":{
								try {
									playerRoom.getRoomNorth().addCreature(playerRoom.findPlayer());
									break;
								}
								catch (NullPointerException e) {
									System.out.println("There is no room to the north.");
									break;
								}
							}
							case "SOUTH":{
								try {
									playerRoom.getRoomSouth().addCreature(playerRoom.findPlayer());
									break;
								}
								catch (NullPointerException e) {
									System.out.println("There is no room to the south.");
									break;
								}
							}
							case "EAST":{
								try {
									playerRoom.getRoomEast().addCreature(playerRoom.findPlayer());
									break;
								}
								catch (NullPointerException e) {
									System.out.println("There is no room to the east.");
									break;
								}
							}
							case "WEST":{
								try {
									playerRoom.getRoomWest().addCreature(playerRoom.findPlayer());
									break;
								}
								catch (NullPointerException e) {
									System.out.println("There is no room to the west.");
									break;
								}
							}
							case "QUIT":{
								System.out.println("You have quit the game, you quitter. Your total street cred was: "+playerRoom.findPlayer().getStreetCred());
								playing = false;
								break;
							}
							case "EXIT":{
								System.out.println("You have exited the game. Your total street cred was: "+playerRoom.findPlayer().getStreetCred());
								playing = false;
								break;
							}
//----------------------------------------------------------------------------------------------------------cheat codes here
							case "UPDOWNLEFTRIGHT":{
								System.out.println(cheatList);
								break;
							}
							case "CHEAT1":{
								for(int i = 0; i<10; i++) {
										playerRoom.findPlayer().boostCred();
								}
								break;
							}
							case "CHEAT6":{
								for(int i = playerRoom.findPlayer().getStreetCred(); i>=0; i--) {
									playerRoom.findPlayer().bustCred();
								}
								break;
							}
							case "CHEAT7":{
								for(int i = 0; i <= playerRoom.findPlayer().getStreetCred(); i++) {
									playerRoom.findPlayer().boostCred();
								}
								break;
							}
							case "CHEAT8":{
								for(int i = 0; i<10; i++) {
									if((playerRoom.getList()[i]!=null)&&(playerRoom.getList()[i]!=playerRoom.findPlayer())) {
										System.out.println(playerRoom.getList()[i].getName()+" was brutally murdered.");
										playerRoom.removeCreature(playerRoom.getList()[i]);
									}
								}
								playerRoom.setState(State.DIRTY);
								System.out.println("The room is now dirty.");
								playerRoom.findPlayer().lowestCred();
								System.out.println(playerRoom.findPlayer().unhappy());
								System.out.println("Your street cred is now "+playerRoom.findPlayer().getStreetCred()+".");
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
									if(playerRoom.findCreature(inputs.get(1)).type().equals("ANIMAL")) {
										playerRoom.findCreature(inputs.get(1)).modifyRoom();
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
									if(playerRoom.findCreature(inputs.get(1)).type().equals("NPC")) {
										playerRoom.findCreature(inputs.get(1)).modifyRoom();
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
										if(playerRoom.findCreature(inputs.get(1))!=null) {
												playerRoom.getNeighbors().get((int)(Math.random()*playerRoom.getNeighbors().size())).addCreature(
														playerRoom.findCreature(inputs.get(1)));
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
										if(playerRoom.findCreature(inputs.get(1))!=null) {
											//then we check to find the specified neighboring room
											for(int i = 0; i<playerRoom.getNeighbors().size(); i++) {
												//when we find it, the creature gets moved
												if(playerRoom.getNeighbors().get(i).getName().toUpperCase().equals(inputs.get(2).toUpperCase())) {
													//but only if it would like to be in that room
													if(playerRoom.findCreature(inputs.get(1)).isPleasedNearby(playerRoom.getNeighbors().get(i))){
														playerRoom.getNeighbors().get(i).addCreature(playerRoom.findCreature(inputs.get(1)));
														break;
													}
													//otherwise the player is notified that the creature did not want to go there
													else {
														System.out.println(inputs.get(1)+" did not want to go to "+inputs.get(2)+".");
														break;
													}
												}
												//otherwise the room is non-existent
												else if (i==playerRoom.getNeighbors().size()) {
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
											world.get(i).addCreature(playerRoom.findPlayer());
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
								if(playerRoom.isFull()==false) {
									System.out.println("Space found in current room.");
									for(int i = 0; i<world.size(); i++) {
										if(world.get(i).getName().equals(playerRoom.getName())) {
											world.get(i).addCreature(new Animal(inputs.get(1), inputs.get(2)));
										}
									}
								}
								break;
							}
							case "CHEAT4":{
								System.out.println("Spawning new NPC.");
								if(playerRoom.isFull()==false) {
									System.out.println("Space found in current room.");
									for(int i = 0; i<world.size(); i++) {
										if(world.get(i).getName().equals(playerRoom.getName())) {
											world.get(i).addCreature(new NPC(inputs.get(1), inputs.get(2)));
										}
									}
								}
								break;
							}
							case "CHEAT5":{
								System.out.println("Attempting to murder "+inputs.get(1)+" in "+playerRoom.getName()+".");
									try {
										playerRoom.removeCreature(playerRoom.findCreature(inputs.get(1)));
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
		}
		System.out.println("Thank you for playing.");
		scone.close();
	}
}

