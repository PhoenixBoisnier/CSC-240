/**
 * 
 * @author Phoenix Boisier
 * 
 * Change Log:
 * 	10/16/17
 * 	-pulled world building from PC into this class
 * 	-fed the PC the world info
 * 
 * 	10/15/17
 * 	-read the assignment and realized the bulk of this needs to be in the PC class...
 * 
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

	public static void main(String[] args) {
		
		Scanner scone = new Scanner(System.in);
		ArrayList<Room> world = null;
		PC player = null;
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		String inputFileName;
		boolean scanning = true;
		boolean running = true;
		
		//gets the filename for the xml input to build the world
		while(running) {
			while(scanning) {
				System.out.println("What file would you like to use for the world? \n(type 'EXIT' to quit program)");
				System.out.println("ex: input.xml");
				inputFileName = scone.next();
				if(inputFileName.toUpperCase().equals("EXIT")) {
					running = false;
					scanning = false;
					break;
				}
				else {
					try {
						InputStream xmlInput  = new FileInputStream(inputFileName);
						SAXParser saxParser = spf.newSAXParser();
						ProjectXMLParser sxp = new ProjectXMLParser();
						saxParser.parse(xmlInput, sxp);
						world = sxp.getRooms();
						scanning = false;
						running = false;
					}
						
					catch(SAXException|ParserConfigurationException|IOException e){
						System.out.println("No such file name. Please try again.");
					}
				}	
			}
		}
		//in the case that the initial loop was exited
		if(world==null) {
			System.out.println("Program quit before building world.");
		}
		//otherwise we find the player
		else {
			for(int i = 0; i<world.size(); i++) {
				if(world.get(i).findPlayer()!=null) {
					//we store the player for later use
					player = world.get(i).findPlayer();
					//this method is used to give the player info about the world for cheats
					player.feedWorld(world);
					break;
				}
			}
			//and now we play
			player.play(scone);
		}
		scone.close();
		
	}
}

